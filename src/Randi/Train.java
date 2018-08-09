package Randi;

import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station=null;
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
	View view;
	int i=0; //train has not been dispatched.
	private boolean end = false;
	
	////////////////// Constructor station_init //////////////////
	public Train(String name, int N, Station[] stations, boolean isFirst, View frame){
		this.view = frame;
		this.free_seats = this.max_seats = N;
		this.stations = stations;
		this.name = name;
		this.isFirstTrain = isFirst;
		this.counter = 0;
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
	
	public void nextStation() {
		Integer num = Integer.parseInt(name.substring(6))-1;
		if (end) {
			for (int z=view.trains.get(num).y; z > 150; z--) {
				view.trains.get(num).y = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.end = false;
		}
		
		switch (this.i) {
		case 1:
			for (int z=view.trains.get(num).x; z <= view.s2X; z++) {
				view.trains.get(num).x = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 2:
			for (int z=view.trains.get(num).x; z <= (int) view.width-view.offsetR+50; z++) {
				view.trains.get(num).x = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 3:
			for (int z=view.trains.get(num).y; z < view.s4Y; z++) {
				view.trains.get(num).y = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 4:
			for (int z=view.trains.get(num).y; z < (int) view.height-view.offsetB-105; z++) {
				view.trains.get(num).y = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 5:
			for (int z=view.trains.get(num).x; z >= view.s6X; z--) {
				view.trains.get(num).x = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 6:
			for (int z=view.trains.get(num).x; z >= 250; z--) {
				view.trains.get(num).x = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 7:
			for (int z=view.trains.get(num).y; z > view.s8Y; z--) {
				view.trains.get(num).y = z;
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.end = true;
		}
	}

	public void run() {
		
		do {
			sleep(1000);
			curr_station = stations[i];
			if(curr_station.Name.equalsIgnoreCase("Roosevelt"))
				counter = 0;
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
			
			this.nextStation();
			
			if(curr_station.Name.equalsIgnoreCase("Baclaran") && !hasLastTrainCome && isTrainOne && next_station.lastTrainNotDispatched){
				next_station.waitForLastTrain();
				System.out.println("nakalaya na train1");
				isFirstTrain = false;
				hasLastTrainCome = true;
			}
				
			next_station.checkWaitingWaiting(name, curr_station, isFirstTrain);
			curr_station.getNextTrain(isFirstTrain);
			if(isLastTrain) {
				System.out.println("Continue train1 at " + curr_station.Name);
				curr_station.lock.lock();
				if(curr_station.lastTrainNotDispatched) {
					curr_station.lastTrainNotDispatched = false;
					curr_station.last_Train.signal();
					System.out.println("wala namang train1 e!");
				}
				curr_station.lock.unlock();
				System.out.println("ok!");
				isLastTrain = false;
			}
			curr_station = stations[i];
			
			next_station.checkWaiting(name, curr_station, isFirstTrain);
			
			isFirstTrain = false;
		}
		while(counter!=8);
		next_station.exitCircuit();
		System.out.println("I exited" + name + " " + curr_station.Name);
		
		Integer num = Integer.parseInt(name.substring(6))-1;
		view.exitTrain(num);
	}
}