package Randi;

import java.util.concurrent.*;

class Passenger extends Thread{
	private String name;
	private Station station;
	private Station drop_station;
	private Train train;
	View view;
	int curr;
	int drop;
	
	Passenger(String name, Station station, Station drop_station, View frame, int num, int num2){
		this.name = name;
		this.station = station;
		this.view = frame;
		this.drop_station = drop_station;
		this.curr = num;
		this.drop = num2;
	}

	public void run(){
		try {
			train = station.station_wait_for_train();
			drop_station.incrementDropOff(train.name);
			station.station_on_board();	
			view.pass_station[curr]--;
			
			drop_station.station_get_off(train.name);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Passenger" + name + "dropped off at " + drop_station.Name);
		view.drop_train[drop]++;
	}
}