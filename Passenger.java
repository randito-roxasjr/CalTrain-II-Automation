package Randi;
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
		train = station.station_wait_for_train();
		station.station_on_board();
		
		while(drop_station.station_check_station(train.curr_station)); 
		drop_station.station_get_off();
		System.out.println("Passenger" + name + "dropped off.");
		
	}
}