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
			trains[i] = new Train("Train "+(i+1), x);
		}
	}

	public static int check_all_stations(){
		int N=0;	

		for (int i=0; i<8; i++){
			N += stations[i].waiting;
		}
		return N;
	}
	
	public static void main(String[]args) {
		station_init();
		train_init();

		Scanner reader =  new Scanner(System.in);
		
		System.out.println("Total Passengers: ");
		int x = reader.nextInt();

		Passenger passengers[] = new Passenger[x];
		distribute_pass(x);
		for(int i = 0; i<x ; i++) {
			passengers[i] = new Passenger("Pass# "+(i+1), stations[i%8]);
		}
		
		while(check_all_stations() > 0){
			stations[0].station_wait_for_train(trains);
			stations[1].station_wait_for_train(trains);
			stations[2].station_wait_for_train(trains);
			stations[3].station_wait_for_train(trains);
			stations[4].station_wait_for_train(trains);
			stations[5].station_wait_for_train(trains);
			stations[6].station_wait_for_train(trains);
			stations[7].station_wait_for_train(trains);

			trains[0].run(stations);
		/*	trains[1].run(stations);
			trains[2].run(stations);
			trains[3].run(stations);
			trains[4].run(stations);
			trains[5].run(stations);
			trains[6].run(stations);
			trains[7].run(stations);
			trains[8].run(stations);
			trains[9].run(stations);
			trains[10].run(stations);
			trains[11].run(stations);
			trains[12].run(stations);
			trains[13].run(stations);
			trains[14].run(stations);
			trains[15].run(stations);*/

		}
	}
	




}