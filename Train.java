import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station;
	Station next_station;
	int free_seats;
	int i=-1; //train has not been dispatched.
	String name;


	////////////////// Constructor station_init //////////////////
	public Train(String name, int N){
		this.free_seats = N;
		this.name = name;
		System.out.println("Created Train: "+this.free_seats+" seats");
	}

	public int getSeats(){
		return this.free_seats;
	}

	public void run(Station[] s) {
		
		this.stations = s;

		i = (i+1) % 7; // mod 8 since only 8 stations available

		next_station = stations[i+1];
		curr_station = stations[i];
		curr_station.station_load_train(this);
		
		//if wala ng passengers sa mga station and nasa dulong station na ko, i stop
	}
}