package Randi;

import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station=null;
	Station next_station;
	Station prev_station;
	int free_seats;
	String name;
	boolean isFirstTrain;
	boolean hasLastTrainCome = false;
	boolean isTrainOne=false;

	////////////////// Constructor station_init //////////////////
	public Train(String name, int N, Station[] stations, boolean isFirst){
		this.free_seats = N;
		this.stations = stations;
		this.name = name;
		this.isFirstTrain = isFirst;
		System.out.println("Created Train: "+this.free_seats+" seats");
	}

	public void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int getSeats(){
		return this.free_seats;
	}

	public void run() {
		int i=0; //train has not been dispatched.
		
		do {
			sleep(1000);
			curr_station = stations[i];
			curr_station.waitEmpty(name, this);
			
			System.out.println("begin loading " + name);
			curr_station.station_load_train(free_seats, this);
			
			System.out.println("load train ok " + name);
			
			i = (i+1) % 8; // mod 8 since only 8 stations available
			next_station = stations[i];
			System.out.println(name + " is check waiting at " + next_station.Name);
			System.out.println("currently at " + curr_station.Name);
			
			if(curr_station.Name.equalsIgnoreCase("Baclaran") && !hasLastTrainCome && isTrainOne)
				next_station.waitForLastTrain();
			next_station.checkWaiting(name, curr_station, isFirstTrain);
			curr_station.getNextTrain(isFirstTrain);
			isFirstTrain = false;
			
		}
		while(curr_station.waiting!=0 || !curr_station.Name.equalsIgnoreCase("Baclaran"));
		//while(!curr_station.Name.equalsIgnoreCase("Baclaran"));
		System.out.println("I exited" + name + " " + curr_station.Name);
		//if wala ng passengers sa mga station and nasa dulong station na ko, i stop
	}
}