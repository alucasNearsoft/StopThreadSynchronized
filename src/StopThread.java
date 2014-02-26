import java.util.concurrent.TimeUnit;

// Properly synchronized cooperative thread termination
// the synchronization on these methods
// is used solely for its communication effects, not for mutual exclusion.
public class StopThread {
	private static boolean stopRequested;
	private static synchronized void requestStop() {
		stopRequested = true;
	}
	private static synchronized boolean stopRequested() {
		return stopRequested;
	}
	public static void main(String[] args)
			throws InterruptedException {
		Thread backgroundThread = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (!stopRequested())
					i++;
			}
		});
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		requestStop();
		System.out.println("Done.");
	}
}