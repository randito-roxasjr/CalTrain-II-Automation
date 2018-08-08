package semaphore;

import java.util.concurrent.*;

class Passenger extends Thread{
	private String name;
	private Station station;
	private Station drop_station;
	private Train train;

	Passenger(String name, Station station, Station drop_station){
		this.name = name;
		this.station = station;
		this.drop_station = drop_station;
	}

	public void run(){
		try {
			train = station.station_wait_for_train();
			drop_station.incrementDropOff(train.name);
			station.station_on_board();
			drop_station.station_get_off(train.name);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Passenger" + name + "dropped off at " + drop_station.Name);
		
	}
}