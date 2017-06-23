public class testSynchronized2 {
	//set to be volatile will not help
	private int count = 0;
	
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String[] args) {
		testSynchronized2 test = new testSynchronized2();
		test.doWork();
	}
	
	public void doWork() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1000; i++) {
					increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1000; i++) {
					increment();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		System.out.println("Count: " + count);
	}

}
