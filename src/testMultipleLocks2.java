import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//more than twice the time, since synchronized get the intrinsic lock of the class. 
//Eg: It hold the class when stageOne() is processing and block the processing of stageTwo()
public class testMultipleLocks2 {
	public static void main(String[] args) {
		testMultipleLocks2 test = new testMultipleLocks2();
		test.work();
	}
	private Random random = new Random();

	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	public synchronized void stageOne() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list1.add(random.nextInt(100));
	}

	public synchronized void stageTwo() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list2.add(random.nextInt(100));
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
