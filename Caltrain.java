import java.util.Scanner;

public class Caltrain{
 Scanner reader = new Scanner(System.in);
	
	public void run(){
		//Create Stations
		Station[] stations = station_init();
		
		//Create All Trains
		Train[] trains = train_init();
	}

	////////////////// Create All 8 Stations //////////////////
	public Station[] station_init(){
		Station[] t = new Station[8];

		t[0] = new Station("Roosevelt");
		t[1] = new Station("Monumento");
		t[2] = new Station("R. Papa");
		t[3] = new Station("Blumentrit");
		t[4] = new Station("Bambang");
		t[5] = new Station("Carriedo");
		t[6] = new Station("EDSA");
		t[7] = new Station("Baclaran");

		return t;
	}

	////////////////// Create All Trains //////////////////
	public Train[] train_init(){
		Train[] t = new Train[16];
		Integer N = 16;

		for(int i=0; i<16; i++){
			// Get input
			System.out.println("Train "+(i+1));
			System.out.print("Number of Seats: ");
			int x = reader.nextInt();
			t[i] = new Train(x);
		}
		return t;
	}

	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count){
		;
	}

	////////////////// station waits for the train //////////////////
	public void station_wait_for_train(){
		// If train has arrived and there are enough seats		
		// else, continue to wait
		;
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		;
	}

}