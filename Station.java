import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Station{
	//private Thread t;
	String Name;
	private Semaphore train = new Semaphore(1); //acts like semaphore for train
    Semaphore waiting_train = new Semaphore(1); //acts like semaphore for waiting train
	private Semaphore seats; //acts like semaphore for free_seats
	
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
		while(hasWaitingTrain) {
			try {
				System.out.println("Now, " + name + " is waiting for "+ this.Name);
				waiting_train.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		hasTrain = false;
		//train.signal();
	}
	
	public void waitEmpty(String name) {
		hasWaitingTrain = true;
		while(hasTrain){
			try {
				//train.acquire();
				System.out.println(name + " is currently waiting for train in "+ this.Name);
				train.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		train.release();
		hasTrain = true;
		hasWaitingTrain = false;
		waiting_train.release();

		System.out.println(name + " can now enter station " + this.Name);
	}
	
	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count, Train tren){
		this.seats = new Semaphore(count);
		free_seats = count;
		hasTrain = true;

		try{
			seats.acquire();
			System.out.println("Passenger boarding in " + tren.name + " at station "+ this.Name);
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		
		free_seats=0;
		hasTrain=false;
		tren.free_seats -= boarding;
		train.release();
	}

	////////////////// station waits for the train //////////////////
	public void station_wait_for_train(){
		// If train has arrived and there are enough seats		
		// else, continue to wait
		
		try{
			Thread.sleep(10);
			System.out.println(this.Name+" is waiting for a train");
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		
		free_seats--;
		waiting--;
		boarding ++;
		
		System.out.println("Train arrives in " + Name + " station");
		hasTrain = true;
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
		seats.release();
		free_seats++;	
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		boarding--;
	}
	
}
