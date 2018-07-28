import java.util.concurrent.*;

class Train extends Thread{
	Station[] stations;
	Station curr_station;
<<<<<<< HEAD
<<<<<<< HEAD
	Station next_station;
=======
	int max_seats;
>>>>>>> c2bf62702e58ceed998e74c3f4bf764349d99bac
=======
	int max_seats;
>>>>>>> c2bf62702e58ceed998e74c3f4bf764349d99bac
	int free_seats;
	String name;

	////////////////// Constructor station_init //////////////////
	public Train(String name, int N, Station[] stations){
		this.max_seats = N;
		this.free_seats = N;
		this.stations = stations;
		this.name = name;
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
			next_station.checkWaiting();
			curr_station = stations[i];
			curr_station.waitEmpty();
			
			curr_station.station_load_train(free_seats, this);
			
		}
		while(curr_station.waiting!=0 && curr_station.Name.equalsIgnoreCase("Roosevelt")); 
		//if wala ng passengers sa mga station and nasa dulong station na ko, i stop
	}
}