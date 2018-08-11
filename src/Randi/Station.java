package Randi;

import java.util.concurrent.*;

class Station{
	//private Thread t;
	String Name;
	Train tren;
	Semaphore mutex = new Semaphore(1, true);
	Semaphore train = new Semaphore(1, true); //acts like semaphore for train
    Semaphore waiting_train = new Semaphore(1, true); //acts like semaphore for waiting train
    Semaphore waiting_waiting_train = new Semaphore(1, true);
    Semaphore last_Train = new Semaphore(0, true);
    Semaphore dispatch = new Semaphore(1, true);
	Semaphore seats = new Semaphore(0, true); //acts like semaphore for free_seats
	Semaphore drop = new Semaphore(0, true);
	Semaphore board = new Semaphore(0, true);
	Semaphore task = new Semaphore(1, true);
	Semaphore noSpace = new Semaphore(0);
	Semaphore noDrop = new Semaphore(0);
	Semaphore noBoard = new Semaphore(0);
	
	String train_drop[] = new String[16];
	int num_drop[] = new int[16];
	
	boolean hasTrain = false;
	boolean hasWaitingTrain = false;
	boolean hasWaitingWaitingTrain = false;
	boolean lastTrainNotDispatched = true;
	
	int free_seats;
	int waiting;
	int boarding;
	View view;
	
	////////////////// Constructor station_init //////////////////
	Station(String N, View frame){
		this.Name = N;
		this.view = frame;
		System.out.println("Created Station " + this.Name);
		free_seats = 0;
		boarding = 0;
	}
	
	public void signal_first_train() throws InterruptedException
	{
		lastTrainNotDispatched = false;
		last_Train.release();
	}

	public void waitForLastTrain() throws InterruptedException {		
		//----------- TRAIN WAITS -----------
		last_Train.acquire();
		//----------- TRAIN MOVES FROM WAIT -----------
		System.out.println("Train 1 is free!");
	}
	
	public void checkWaitingWaiting(String name, boolean isFirstTrain) throws InterruptedException {
		//----------- TRAIN WAITS -----------
		waiting_waiting_train.acquire();
		//----------- TRAIN MOVES FROM WAIT -----------
	}
	
	public void getNextTrain(boolean isFirstTrain) throws InterruptedException {
		if(isFirstTrain) {
			System.out.println("dipatch the next train!");
			dispatch.release();
			train.release();
		}
		else {
			System.out.println("get next train to enter " + Name);
			train.release();
		}
		Integer num = Integer.parseInt(tren.name.substring(6))-1;
		this.num_drop[num] = 0;
	}
	
	public void checkWaiting(String name, Train tren) throws InterruptedException {
		System.out.println("Now,  " + name + " is waiting for waiting "+ this.Name);
		//----------- TRAIN WAITS -----------
		tren.isWaiting = 1;
		waiting_train.acquire();
		tren.isWaiting = 0;
		//----------- TRAIN MOVES FROM WAIT -----------
		waiting_waiting_train.release();
	}
	
	public void waitEmpty(String name, Train tren) throws InterruptedException {
		System.out.println("Currently  " + name + " is waiting for "+ this.Name);
		//----------- TRAIN WAITS -----------
		tren.isWaiting = 1;
		train.acquire();
		tren.isWaiting = 0;
		//----------- TRAIN MOVES FROM WAIT -----------
		waiting_train.release();
		this.tren = tren;
		System.out.println("nakapasok na " + name + " sa station " + this.Name);
	}
	
	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count, Train tren) throws InterruptedException{
		free_seats = count;
		//prepare dropping off
		int num = Integer.parseInt(tren.name.substring(6))-1;
		System.out.println(this.tren.name + " number of passengers to drop " + this.num_drop[num]);
		
		if(this.num_drop[num]!=0) {
			//update free seats ni train kung ilan bumaba
			tren.free_seats += this.num_drop[num];
			drop.release(this.num_drop[num]);
			//----------- TRAIN WAITS for passengers to leave-----------
			noDrop.acquire();
		}
		System.out.println("Update free seats " + this.Name + " with free seats:" + free_seats + " by " + tren.name);
		
		if(free_seats!=0 && waiting!=0) {
			seats.release(free_seats);
			//----------- TRAIN WAITS for passengers to prepare for boarding-----------
			noSpace.acquire();
		}
		
		//----------- TRAIN tries to clear the seat permits if there are spaces left-----------
		if(seats.tryAcquire()) {
			System.out.println("may seats pa blanko");
			seats.drainPermits();
		}
		System.out.println("remaining waiting at " + this.Name + " is " + waiting); 
		System.out.println(boarding + " passengers boarding on" + tren.name + " station "+ this.Name);
		//the train.free_seats is deducted by the number of people actually boarding
		
		tren.free_seats -= boarding;
		if(boarding!=0) {
			board.release(boarding);
			//----------- TRAIN WAITS for passengers to actually board-----------
			noBoard.acquire();
		}

		System.out.println(this.tren.name + " done boarding at " + this.Name );
		boarding = 0;
		free_seats=0;
		hasTrain=false;
	}

	////////////////// station waits for the train //////////////////
	public Train station_wait_for_train() throws InterruptedException{
		// If train has arrived and there are enough seats		
		// else, continue to wait
		System.out.println(this.Name+" is waiting for a train");
		while(true) {
			//----------- PASSENGER WAITS FOR TRAIN and FREE SEATS -----------
			seats.acquire();
			System.out.println("permits available: " + mutex.availablePermits());
			mutex.acquire();
			//----------- PASSENGER PREPARES TO BOARD -----------
			if(free_seats!=0) {
				waiting--;
				free_seats--;
				boarding ++;
				hasTrain = true;
				System.out.println("from " + Name + " nakakpasok ko " + tren.name + " with " + free_seats + " " + boarding);
				Train ret = tren;
				if(free_seats==0 || waiting==0)
					noSpace.release();
				mutex.release();
				return ret;
			}
			mutex.release();
		}
		
	}
	
	public void incrementDropOff(String name) throws InterruptedException {
		mutex.acquire();
		Integer num = Integer.parseInt(name.substring(6))-1;
		this.train_drop[num] = name;
		this.num_drop[num]++;
		mutex.release();
	}
	
	////////////////// passenger gets off train and free seat increments //////////////////
	public void station_get_off(String pass_train) throws InterruptedException {
		while(true) {
			//passenger waits for train to ask if they are going to get off or not
			drop.acquire();
			if(this.tren.name.equalsIgnoreCase(pass_train)) {
				mutex.acquire();
				break;
			}
			drop.release();
		}
		//PASSENGER LEAVES and updates station.free_seats
		free_seats++;
		Integer num = Integer.parseInt(tren.name.substring(6))-1;
		this.num_drop[num]--;
		System.out.println("FS: " + free_seats + " ND: " + num_drop[num]);
		if(this.num_drop[num]==0)
			noDrop.release();
		mutex.release();
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board() throws InterruptedException{
		// Let train know passengers are on board
		//----------- PASSENGER WAITS for train to signal passengers to actually board -----------
		board.acquire();
		mutex.acquire();
		System.out.println("nakasakay na ko train " + tren.name + " with " + mutex.availablePermits() + " " + boarding);
		boarding--;
		// update the number of boarding passengers left
		// when the boarding passenger is 0, the train prepares to leave.
		if(boarding==0)
			noBoard.release();
		mutex.release();
	}
	
	public void exitCircuit() throws InterruptedException {
		mutex.acquire();
		//------------------- TRAIN EXITS! -----------------------
		hasWaitingTrain = false;
		waiting_train.release();
		System.out.println("Exit successful!");
		mutex.release();
	}
	
}