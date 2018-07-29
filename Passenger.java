package Randi;
import java.util.concurrent.*;

class Passenger extends Thread{
	private String name;
	private Station station;
	private int count;

	Passenger(String name, Station station){
		this.name = name;
		this.station = station;
		this.count = 5;
	}

	public void run(){
		station.station_wait_for_train();
		station.station_on_board();
		while(count!=0) {
			if(station.station_check_station())
				count --;
		}
		station.station_get_off();
		System.out.println("Passenger" + name + "dropped off.");
		
	}
}