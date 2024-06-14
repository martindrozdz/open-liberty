package test.bundle;

import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component
public class TestService {
	private static final AtomicInteger activatedCount = new AtomicInteger();
	private final int instance = activatedCount.incrementAndGet();
	
	public static int getActivatedCount() { return activatedCount.get(); }

	@Activate
	public void activate() { System.out.println("### Activate TestService#" + instance); }
}
