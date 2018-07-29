import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.*;

class Station{
	private int state;
	String Name;
	private Semaphore waiting_train = new Semaphore(1); //Waiting train
	private Semaphore train = new Semaphore(1); //semaphore for train
	private Semaphore seats; //semaphore for free_seats
	

	boolean hasTrain = false;
	boolean hasWaitingTrain = false;
	
	int curr_train=-1;
	int free_seats;
	int waiting;
	int boarding;

	////////////////// Contructor station_init //////////////////
	Station(String N){
		this.Name = N;
		this.state = 1;
		System.out.println("Created Station " + this.Name);
		free_seats = 0;
		boarding = 0;
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
	public void station_load_train(Train t){
		if (state != 0){
			try{
				train.acquire();	//Get train
				this.hasTrain = true;
				this.hasWaitingTrain = false;

				System.out.println(t.name+" has arrived in "+this.Name);

				//	LOAD TRAIN
				free_seats = t.free_seats;
				seats = new Semaphore(free_seats);
				
				seats.acquire();

			} catch(InterruptedException e){
				System.out.println(e);
			}

			this.station_on_board();
		}
	
	}

	////////////////// station waits for the train //////////////////
	public void station_wait_for_train(Train[] t){
		if (state != 0){
			this.curr_train++;
			this.curr_train=curr_train%15;
			try{
				waiting_train.acquire();
				this.hasWaitingTrain = true;
				System.out.println(this.Name+" is waiting for "+t[curr_train].name);
				
			} catch (InterruptedException e){
				System.out.println(e);
			}

			waiting_train.release();
		}
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
		//lock.lock();
		free_seats++;
		//lock.unlock();		
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		this.free_seats--;
		this.waiting--;
		System.out.println("Passenger has boarded in "+this.Name);
		
		if (this.free_seats == 0){
			hasTrain=false;
			state = 0;
		}

		train.release();
	}
	
}
