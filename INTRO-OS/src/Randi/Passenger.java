package Randi;
import java.util.concurrent.*;

class Passenger extends Thread{
	private String name;
	private Station station;
	private Station drop_station;
	private Train train;
	View view;
	
	Passenger(String name, Station station, Station drop_station, View panel){
		this.view = panel;
		this.name = name;
		this.station = station;
		this.drop_station = drop_station;
	}

	public void run(){
		train = station.station_wait_for_train();
		drop_station.incrementDropOff(train.name);
		station.station_on_board();
		
	//	while(drop_station.station_check_station(train.curr_station)); 
		drop_station.station_get_off(train.name);
		System.out.println("Passenger " + name + " dropped off.");
		
	}
}