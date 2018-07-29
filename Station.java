package Randi;
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
		free_seats = 0;
		boarding = 0;
		
	}
	
	
	public void checkWaiting(String name) {
		lock.lock();
		while(hasWaitingTrain) {
			try {
				System.out.println("Now,  " + name + " is waiting for witing "+ this.Name);
				waiting_train.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		hasTrain = false;
		train.signal();
		lock.unlock();
	}
	
	public void waitEmpty(String name) {
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
		hasWaitingTrain = false;
		waiting_train.signal();
		lock.unlock();
		System.out.println("nakapasok na " + name + "sa station " + this.Name);
	}
	
	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count, Train tren){
		lock.lock();
		System.out.println("acquired lock!");
		free_seats = count;
		hasTrain = true;
		while(waiting!=0 && free_seats!=0) {
			
			seats.signal();
			lock.unlock();
			lock.lock();
		}
		System.out.println("remain waiting at " + this.Name + " is " + waiting); 
		System.out.println("passenger boarding at train" + tren.name + " station "+ this.Name);
		while(boarding!=0) {
			lock.unlock();
			lock.lock();
		}
		System.out.println("train leavin at train" + tren.name + " station " + this.Name);
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
		lock.unlock();
		System.out.println("from " + Name + " nakakpasok ko train!" + free_seats);
		lock.lock();
		hasTrain = true;
		
		
		lock.unlock();
		
	}
	
	////////////////// passenger checks if train successfully arrives on a station //////////////////
	public boolean station_check_station() {
		if(hasTrain || free_seats<=0)
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

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		lock.lock();
		boarding--;
		lock.unlock();
	}
	
}
