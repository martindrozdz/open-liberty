package test.bundle;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.ibm.wsspi.threading.ExecutorServiceTaskInterceptor;

@Component
public class TestService implements ExecutorServiceTaskInterceptor {
    private static final AtomicInteger activatedCount = new AtomicInteger();
    private final int instance = activatedCount.incrementAndGet();
    private static final AtomicReference<TestService> lastInstance = new AtomicReference<>();

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
        new Throwable("wrapping runnable " + r).printStackTrace(System.out);
        return () -> {
            new Throwable("wrapped runnable invoked")
                    .printStackTrace(System.out);
            r.run();
        };
    }

    @Override
    public <T> Callable<T> wrap(Callable<T> c) {
        new Throwable("wrapping callable " + c).printStackTrace(System.out);
        return () -> {
            new Throwable("wrapped callable invoked")
                    .printStackTrace(System.out);
            final T t = c.call();
            System.out.println("task returned " + t);
            return t;
        };
    }
}
