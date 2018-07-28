import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Station extends Thread{
	//private Thread t;
	String name;
	private final Semaphore train = new Semaphore(1); //acts like semaphore for train
	private Semaphore seats; //acts like semaphore for free_seats
	
	private boolean hasTrain = false;
	private Train curr_train;
	
	int free_seats=0;
	int waiting;
	int boarding;

	////////////////// Contructor station_init //////////////////
	Station(String N){
		this.name = N;
		System.out.println("Created Station " + this.name);
	}

	////////////////// RUN STATIONS //////////////////
	public void run(){
		this.station_wait_for_train();
	}
	
	public void waitEmpty() {
		while(hasTrain)
			try {
				train.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count){
		this.seats = new Semaphore(count)
		hasTrain = true;
		boarding = 0 ;
		//Passengers acquire seats
		this.seats.acquire();
		free_seats = count;
		
		while(waiting!=0 && free_seats!=0) {
			boarding++;
			waiting--;
		}


		seats.release();
	}

	////////////////// station waits for the train //////////////////
	public void station_wait_for_train(){
		train.acquire(); //acquire a train

		while(free_seats<=0) { //free_seats = 0 means 1) there is no train and 2) there are no free_seats
			try {
				seats.wait();
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hasTrain = true;

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
