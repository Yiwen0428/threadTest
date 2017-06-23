// with problem of nonSynchronized count
public class testSynchronized1 {
	private int count = 0;
	
	public static void main(String[] args) {
		testSynchronized1 test = new testSynchronized1();
		test.doWork();
	}
	
	public void doWork() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1000; i++) {
					count++;
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1000; i++) {
					count++;
				}
			}
		});
		
		t1.start();
		t2.start();
		
		//waits for thread to die
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		System.out.println("Count: " + count);
	}

}
