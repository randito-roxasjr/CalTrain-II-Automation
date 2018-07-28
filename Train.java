import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station;
	Semaphore seats;
	int free_seats;
	String name;

	////////////////// Constructor station_init //////////////////
	public Train(String name, Semaphore sem, int seats, Station[] stations){	 
		this.stations = stations;
		this.seats = sem;
		this.name = name;
		this.free_seats = seats;
	}

	public void run() {
		int i=0;
		do {
			curr_station = stations[i];
			curr_station.waitEmpty();
			curr_station.station_load_train(free_seats);
		}
		while(curr_station.waiting!=0 && curr_station.Name.equalsIgnoreCase("Roosevelt")); 
		//if wala ng passengers sa mga station and nasa dulong station na ko, i stop
	}
}