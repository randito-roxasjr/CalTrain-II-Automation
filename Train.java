package semaphore;

import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station;
	Station next_station;
	Station last_station;
	int free_seats;
	int max_seats;
	int counter;
	String name;
	boolean isFirstTrain;
	boolean isLastTrain=false;
	boolean hasLastTrainCome = false;
	boolean isTrainOne=false;
	boolean isWaiting = false;
	boolean isWaitingWaiting = false;

	////////////////// Constructor station_init //////////////////
	public Train(String name, int N, Station[] stations, boolean isFirst){
		this.free_seats = this.max_seats = N;
		this.stations = stations;
		this.name = name;
		this.isFirstTrain = isFirst;
		this.counter = 0;
		System.out.println("Created Train: "+this.free_seats+" seats");
	}

	public int getSeats(){
		return this.free_seats;
	}
	
	public void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		int i=0; //train has not been dispatched.
		
		do {
			sleep(1000);
			curr_station = stations[i];
			if(curr_station.Name.equalsIgnoreCase("Roosevelt"))
				counter = 0;
			try {
				curr_station.waitEmpty(name, this);
								
				System.out.println("begin loading " + name);
				
				curr_station.station_load_train(free_seats, this);
				
				if(free_seats == max_seats)
					counter++;
				System.out.println("load train ok " + name);
		
				i = (i+1) % 8; // mod 8 since only 8 stations available
				next_station = stations[i];
				System.out.println(name + " is check waiting at " + next_station.Name);
				System.out.println("currently at " + curr_station.Name);
				
				if(curr_station.Name.equalsIgnoreCase("Baclaran") && !hasLastTrainCome && isTrainOne && next_station.lastTrainNotDispatched){
					try {
						next_station.waitForLastTrain();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("nakalaya na train1");
					hasLastTrainCome = true;
				}
				
				next_station.checkWaitingWaiting(name, isFirstTrain);
				curr_station.getNextTrain(isFirstTrain);
				if(isLastTrain) {
					System.out.println("Continue train1 at " + curr_station.Name);
					System.out.println("ok!");
					curr_station.signal_first_train();
					isLastTrain = false;
				}
				curr_station = stations[i];
				
				next_station.checkWaiting(name, this);
				
				isFirstTrain = false;
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		while(counter!=8);
		try {
			next_station.exitCircuit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("I exited" + name + " " + curr_station.Name);
		//if wala ng passengers sa mga station and nasa dulong station na ko, i stop
	}
}