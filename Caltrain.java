import java.util.Scanner;

public class Caltrain{
	Scanner reader;	
	Station[] stations = new Station[8];
	Train[] trains = new Train[16];

	public Caltrain(){
		reader = new Scanner(System.in);

		//Create Stationst
		station_init();
		//Create All Trains
		train_init();

		//Get inputs for passenger and their dropoff
		System.print("Total number of Passengers: ");
		int N = reader.nextInt(); //All passengers
		
		System.print("Stations to be boarded: ");
		int D = reader.nextInt(); //How many Stations before dropoff
	

	}

	public void run(){
		open_stations();
	}

	////////////////// Distribute Passengers //////////////////
	public void distribute_pass(int N, int D){
		int passengers =  N/8;
		int remaining = N%8;

		this.stations[0] 
		this.stations[1] 
		this.stations[2] 
		this.stations[3] 
		this.stations[4] 
		this.stations[5] 
		this.stations[6] 
		this.stations[7]
	}

	////////////////// Create All 8 Stations //////////////////
	public void station_init(){
		this.stations[0] = new Station("Roosevelt");
		this.stations[1] = new Station("Monumento");
		this.stations[2] = new Station("R. Papa");
		this.stations[3] = new Station("Blumentrit");
		this.stations[4] = new Station("Bambang");
		this.stations[5] = new Station("Carriedo");
		this.stations[6] = new Station("EDSA");
		this.stations[7] = new Station("Baclaran");
	}

	////////////////// Create All Trains //////////////////
	public void train_init(){
		for(int i=0; i<16; i++){
			// Get input
			System.out.println("Train "+(i+1));
			System.out.print("Number of Seats: ");
			int x = reader.nextInt();
			this.trains[i] = new Train("Train"+i,x,stations);
		}
	}
	////////////////// Open All Stations //////////////////
	public void open_stations(){
		
		for(int i=0; i<8; i++){
			stations[i].open();
		}

	}




}