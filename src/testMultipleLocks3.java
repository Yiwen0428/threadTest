import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*If you declare the method as synchonized 
(as you're doing by typing public synchronized void stageOne()) 
		you synchronize on the whole object, so two thread accessing a different variable 
		from this same object would block each other anyway.

If you want to synchronize only on one variable at a time, 
so two threads won't block each other while accessing different variables, 
you have synchronize on them separately in synchronized () blocks.*/

public class testMultipleLocks3 {
	public static void main(String[] args) {
		testMultipleLocks3 test = new testMultipleLocks3();
		test.work();
	}
	private Random random = new Random();
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	public void stageOne() {
		synchronized(lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}

	public void stageTwo() {
		synchronized(lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}

	public void process() {
		for(int i=0; i<1000; i++) {
			stageOne();
			stageTwo();
		}
	}

	public void work() {
		System.out.println("Starting ...");

		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		t1.start();
		t2.start();


		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time take: " + (end-start));
		System.out.println("List1: " + list1.size() + "; List2: " + list2.size());
	}
}
