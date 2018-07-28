import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Station{
	//private Thread t;
	String Name;
	private final Lock lock = new ReentrantLock(); //mutex
	private final Condition train = lock.newCondition(); //acts like semaphore for train
	private final Condition waiting_train = lock.newCondition(); //acts like semaphore for waiting train
	private final Condition seats = lock.newCondition(); //acts like semaphore for free_seats
	
	boolean hasTrain = false;
	boolean hasWaitingTrain = false;
	
	int free_seats;
	int waiting;
	int boarding;

	////////////////// Contructor station_init //////////////////
	Station(String N){
		this.Name = N;
		System.out.println("Created Station " + this.Name);
	}
	
	
	public void checkWaiting() {
		while(hasWaitingTrain) {
			try {
				waiting_train.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		train.signal();
	}
	
	public void waitEmpty() {
		while(hasTrain)
			try {
				train.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count, Train tren){
		lock.lock();
		free_seats = count;
		hasTrain = true;
		while(waiting!=0 && free_seats!=0) {
			seats.signal();
			lock.unlock();
			lock.lock();
		}
		while(boarding!=0) {
			lock.unlock();
			lock.lock();
		}
		free_seats=0;
		hasTrain=false;
		tren.free_seats -= boarding;
		//train.signal();
		lock.unlock();
	}

	////////////////// station waits for the train //////////////////
	public void station_wait_for_train(){
		// If train has arrived and there are enough seats		
		// else, continue to wait
		lock.lock();
		waiting ++;
		while(free_seats<=0) { //free_seats = 0 means 1) there is no train and 2) there are no free_seats
			try {
				seats.await();
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hasTrain = true;
		boarding ++;
		lock.unlock();
	}
	
	////////////////// passenger checks if train successfully arrives on a station //////////////////
	public boolean station_check_station() {
		if(hasTrain || free_seats<=0)
			return true;
		else
			return false;
	}
	
	////////////////// passenger gets off train and free seat increments //////////////////
	public void station_get_off() {
		lock.lock();
		free_seats++;
		lock.unlock();		
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		;
	}
	
}
