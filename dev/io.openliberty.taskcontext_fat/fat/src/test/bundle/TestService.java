package test.bundle;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.ibm.wsspi.threading.ExecutorServiceTaskInterceptor;
import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContextService;

@Component
public class TestService implements ExecutorServiceTaskInterceptor {
    private static final AtomicInteger activatedCount = new AtomicInteger();
    private final int instance = activatedCount.incrementAndGet();
    private static final AtomicReference<TestService> lastInstance = new AtomicReference<>();
    public static final ThreadLocal<TaskContext> lastContext = new ThreadLocal<>();

    /** This should always contain all the available TaskContextServices */
    @Reference
    public volatile Collection<TaskContextService> contextProviders;

    public static TestService getInstance() {
        return lastInstance.get();
    }

    @Activate
    public TestService() {
        System.out.println("### Activate TestService#" + instance);
        lastInstance.set(this);
    }

    @Deactivate
    protected void deactivate() {
        lastInstance.compareAndSet(this, null);
    }

    @Override
    public Runnable wrap(Runnable r) {
        final Callable<Void> callable = convertRunnableToCallable(r);
        final Callable<Void> wrappedCallable = wrap(callable);
        return convertCallableToRunnable(wrappedCallable);
    }

    private static Runnable convertCallableToRunnable(Callable<?> c) {
        return () -> {
            try {
                c.call();
            } catch (final Exception e) {
                throw new Error(e);
            }
        };
    }

    private static Callable<Void> convertRunnableToCallable(Runnable r) {
        return () -> {
            r.run();
            return null;
        };
    }

    @Override
    public <T> Callable<T> wrap(Callable<T> c) {
        // THREAD 1 - pre-dispatch
        // retrieve the task context now (the only time it should be non-null)
        final Optional<TaskContext> tc = contextProviders.stream().findFirst()
                .map(TaskContextService::getTaskContext);
        new Throwable("wrapping callable " + c).printStackTrace(System.out);
        return () -> {
            // THREAD 2 - during dispatch
            new Throwable("wrapped callable invoked")
                    .printStackTrace(System.out);
            // stash the task context retrieved earlier
            // on a thread local so the test can access it
            tc.ifPresent(lastContext::set);
            try {
                final T t = c.call();
                System.out.println("task returned " + t);
                return t;
            } finally {
                lastContext.remove();
            }
        };
    }
}
