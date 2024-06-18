package test.bundle;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.ibm.wsspi.threading.ExecutorServiceTaskInterceptor;

@Component
public class TestService implements ExecutorServiceTaskInterceptor {
    private static final AtomicInteger activatedCount = new AtomicInteger();
    private final int instance = activatedCount.incrementAndGet();

    public static int getActivatedCount() {
        return activatedCount.get();
    }

    @Activate
    public void activate() {
        System.out.println("### Activate TestService#" + instance);
    }

    @Override
    public Runnable wrap(Runnable r) {
        new Throwable("wrapping runnable " + r).printStackTrace(System.out);
        return () -> {
            new Throwable("wrapped runnable invoked").printStackTrace(System.out);
           r.run();
        };
    }

    @Override
    public <T> Callable<T> wrap(Callable<T> c) {
        new Throwable("wrapping callable " + c).printStackTrace(System.out);
        return () -> {
            new Throwable("wrapped callable invoked").printStackTrace(System.out);
            T t = c.call();
            System.out.println("task returned " + t);
            return t;
        };
    }
}
