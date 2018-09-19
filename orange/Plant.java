package orange;

public class Plant extends Orange implements Runnable {

	public static final int NUM_THREADS = 5;
	public static final long PROCESSING_TIME = 5 * 1000;
	public static final int ORANGES_PER_BOTTLE = 4;
	public static final int WORKERS = 5;
	public static boolean orangeGoneBad = false;
	public static int totalCounter = 0;
	public static int totalBadOranges = 0;
	public static long startTime = System.currentTimeMillis();
	
	private Thread thread;
	
	public static void main(String[] args) {
		
		Plant[] plant = new Plant[NUM_THREADS];
		String nameOfOrange = "Plant ";
		for (int i = 0; i < NUM_THREADS; i++){
			plant[i]= new Plant(nameOfOrange + i); 
		}
		for (int j = 0; j < NUM_THREADS; j++) {
			try {
				System.out.println("Plant " + j + " is going to close");
				plant[j].getThread().join();
				System.out.println(j + " closed");
			} catch (InterruptedException ignored) {}
		}
		
		if (!orangeGoneBad) {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", totalCounter,
					totalCounter / ORANGES_PER_BOTTLE, totalCounter % ORANGES_PER_BOTTLE);
		} else {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", totalCounter,
					totalCounter / ORANGES_PER_BOTTLE, totalCounter % ORANGES_PER_BOTTLE + totalBadOranges);
		}

	}

	public Plant(String name) {
		System.out.println("Creating Orange Plantation " + name);
		thread = new Thread();
		run();
	}
	
	public Thread getThread() {
		return thread;
	}
	
	@Override
	public void run() {
		Worker[] workers = new Worker[WORKERS];
		for (int i = 0; i < WORKERS; i++){
			workers[i]= new Worker(); 
		}
		for (int j = 0; j < NUM_THREADS; j++) {
			try {
				System.out.println("Worker " + j + " is going home");
				totalCounter += workers[j].getCounter();
				totalBadOranges += workers[j].getBadOranges();
				workers[j].getThread().join();
				System.out.println(j + " left");
			} catch (InterruptedException ignored) {}
		}
	}
}
