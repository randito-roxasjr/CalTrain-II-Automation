import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station;
	Station next_station;
	int free_seats;
	String name;
	boolean isFirstTrain;

	////////////////// Constructor station_init //////////////////
	public Train(String name, int N, Station[] stations, boolean isFirst){
		this.free_seats = N;
		this.stations = stations;
		this.name = name;
		this.isFirstTrain = isFirst;
		System.out.println("Created Train: "+this.free_seats+" seats");
	}

	public int getSeats(){
		return this.free_seats;
	}

	public void run() {
		int i=-1; //train has not been dispatched.
		
		do {
			i = (i+1) % 8; // mod 8 since only 8 stations available
			next_station = stations[i];
			if(!isFirstTrain) {
				System.out.println("Waiting " + name);
				next_station.checkWaiting(name);
			}
			else 
				isFirstTrain = false;
				
			curr_station = stations[i];
			curr_station.waitEmpty(name);
			System.out.println("nakalabas WAITEMPTY " + name);

			curr_station.station_load_train(free_seats, this);
			System.out.println("Passengers loaded in " + name);
			
		}
		while(curr_station.waiting!=0 || !curr_station.Name.equalsIgnoreCase("Baclaran"));
		//while(!curr_station.Name.equalsIgnoreCase("Baclaran"));
		System.out.println(name + " exited from " + curr_station.Name);
		//if wala ng passengers sa mga station and nasa dulong station na ko, i stop
	}
}