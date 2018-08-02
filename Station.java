package Randi;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Station{
	//private Thread t;
	String Name;
	Train tren;
	final Lock lock = new ReentrantLock(); //mutex
	final Condition train = lock.newCondition(); //acts like semaphore for train
	final Condition waiting_train = lock.newCondition(); //acts like semaphore for waiting train
	private final Condition seats = lock.newCondition(); //acts like semaphore for free_seats
	final Condition board = lock.newCondition();
	final Condition next_station = lock.newCondition();
	final Condition last_Train = lock.newCondition();
	final Condition dispatch = lock.newCondition();
	
	boolean hasTrain = false;
	boolean hasWaitingTrain = false;
	boolean isReadyToBoard = false;
	boolean hasDrop = false;
	boolean dispatchRdy = true;
	boolean lastTrainNotDispatched = true;
	
	int free_seats;
	int waiting;
	int boarding;

	////////////////// Contructor station_init //////////////////
	Station(String N){
		this.Name = N;
		System.out.println("Created Station " + this.Name);
		free_seats = 0;
		boarding = 0;
		
	}
	
	
	public void waitForLastTrain() {
		lock.lock();
		while(lastTrainNotDispatched) {
			try {
				System.out.println("Train1 is waiting for the last train!");
				last_Train.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock.unlock();
	}
	
	public void checkWaiting(String name, Station curr_station, boolean isFirstTrain) {
		lock.lock();
		while(hasWaitingTrain) {
			try {
				System.out.println("Now,  " + name + " is waiting for waiting "+ this.Name);
				waiting_train.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock.unlock();
	}
	
	public void getNextTrain(boolean isFirstTrain) {
		lock.lock();
		hasTrain = false;
		if(isFirstTrain) {
			System.out.println(Name + " ayaw na ");
			dispatchRdy = false;
			dispatch.signal();
			System.out.println("dipatch the next train!");
		}
			
		else if(hasTrain) {
			train.signal();
		}
		lock.unlock();
	}
	
	public void waitEmpty(String name, Train tren) {
		
		hasWaitingTrain = true;
		lock.lock();
		while(hasTrain)
			try {
				System.out.println("Currently  " + name + " is waiting for "+ this.Name);
				train.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		hasTrain = true;
		this.tren = tren;
		hasWaitingTrain = false;
		waiting_train.signal();
		
		lock.unlock();
		System.out.println("nakapasok na " + name + "sa station " + this.Name);
	}
	
	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count, Train tren){
		lock.lock();
		System.out.println("acquired lock at station " + this.Name + " with free seats:" + count);
		free_seats = count;
		hasTrain = true;
		while(waiting!=0 && free_seats!=0 || hasDrop) {
			
			seats.signal();
			lock.unlock();
			lock.lock();
		}
		System.out.println("remaining waiting at " + this.Name + " is " + waiting); 
		System.out.println(boarding + " passengers boarding on" + tren.name + " station "+ this.Name);
		tren.free_seats -= boarding;
		
		isReadyToBoard = true;
		while(boarding!=0) {
			board.signal();
			lock.unlock();
			lock.lock();
		}
		System.out.println("about to leave:" + tren.name + " from station " + this.Name);
		isReadyToBoard = false;
		free_seats=0;
		
		lock.unlock();
	}

	////////////////// station waits for the train //////////////////
	public Train station_wait_for_train(){
		// If train has arrived and there are enough seats		
		// else, continue to wait
		lock.lock();
		System.out.println("nakakuha ako lock");
		while(free_seats<=0) { //free_seats = 0 means 1) there is no train and 2) there are no free_seats
			try {
				seats.await();
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		free_seats--;
		waiting--;
		boarding ++;
		System.out.println("from " + Name + " nakakpasok ko " + tren.name + " with " + free_seats + " " + boarding);
		hasTrain = true;
		
		Train ret = tren;
		lock.unlock();
		return ret;
		
	}
	
	////////////////// passenger checks if train successfully arrives on a station //////////////////
	public boolean station_check_station(Station curr_station) {
		if(curr_station.Name.equalsIgnoreCase(Name))
			return false;
		else
			return true;
	}
	
	////////////////// passenger gets off train and free seat increments //////////////////
	public void station_get_off() {
		lock.lock();
		free_seats++;
		lock.unlock();		
	}
	
	public Train getTrain() {
		return tren;
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		lock.lock();
		while(!isReadyToBoard) {
			try {
				board.await();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		boarding--;
		lock.unlock();
	}
	
}
