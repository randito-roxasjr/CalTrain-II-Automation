import java.util.Scanner;

public class Caltrain{
		
	static Station[] stations = new Station[8];
	static Train[] trains = new Train[16];
	
	//////////////////Distribute Passengers //////////////////
	static void distribute_pass(int N){
		int passengers =  N/8;
		int remaining = N%8;
		int i=0;
		
		stations[0].waiting = passengers;
		stations[1].waiting = passengers; 
		stations[2].waiting = passengers;
		stations[3].waiting = passengers;
		stations[4].waiting = passengers;
		stations[5].waiting = passengers;
		stations[6].waiting = passengers;
		stations[7].waiting = passengers;
		
		while(remaining>0){
			stations[i].waiting++;
			remaining--;
			i++;	
		}
		
		for (i=0; i<8; i++){
			System.out.println(stations[i].waiting+" passengers arrived at station "+stations[i].Name);
		}
	}
	
	
	
	////////////////// Create All 8 Stations //////////////////
	static void station_init(){
		stations[0] = new Station("Roosevelt");
		stations[1] = new Station("Monumento");
		stations[2] = new Station("R. Papa");
		stations[3] = new Station("Blumentrit");
		stations[4] = new Station("Bambang");
		stations[5] = new Station("Carriedo");
		stations[6] = new Station("EDSA");
		stations[7] = new Station("Baclaran");
	}

	////////////////// Create All Trains //////////////////
	static void train_init(){
		Scanner reader =  new Scanner(System.in);
		for(int i=0; i<16; i++){
			// Get input
			System.out.println("Train "+(i+1));
			System.out.print("Number of Seats: ");
			int x = reader.nextInt();
			trains[i] = new Train("Train "+(i+1),x,stations,true);
			
		}
	}
	
	static void dispatchTrain(int i) {
		trains[i].start();
	}
	
	public static void main(String[]args) {
		Scanner reader =  new Scanner(System.in);
		Passenger passengers[];
		station_init();
		train_init();

		System.out.println("Total Passengers: ");
		
		int x = reader.nextInt();
		int total_passenger = x;
		int undispatchedTrain = 15;
		int counter=1;
		passengers = new Passenger[x];
		distribute_pass(x);

		for(int i = 0; i<x ; i++) {
			passengers[i] = new Passenger("Pass# "+(i+1), stations[i%8]);
			passengers[i].start();
		}

		dispatchTrain(0);

		while(undispatchedTrain!=0) {
			while(stations[0].hasTrain);
			System.out.println("Train dispatched " + counter);
			dispatchTrain(counter);
			counter++;
			undispatchedTrain--;
		}
		while(total_passenger>0) {
		}
		
		
	}
	




}