import java.util.Scanner;
import java.util.concurrent.*;


public class Caltrain{
	Scanner reader;	
	Station[] stations = new Station[8];
	Train[] trains = new Train[16];

	public Caltrain(){
		reader = new Scanner(System.in);

		//Create Stations
		station_init();
		//Create All Trains
		train_init();
	}

	public void run(){
		//Get inputs for passenger and their dropoff
		System.out.print("Total number of Passengers: ");
		int N = reader.nextInt(); //All passengers
		
		System.out.print("Stations to be boarded: ");
		int D = reader.nextInt(); //How many Stations before dropoff
		this.distribute_pass(N, D);	

		///////////////////////////////////////////////////

	}

	////////////////// Distribute Passengers //////////////////
	public void distribute_pass(int N, int D){
		int passengers =  N/8;
		int remaining = N%8;
		int i=0;

		this.stations[0].waiting = passengers;
		this.stations[1].waiting = passengers; 
		this.stations[2].waiting = passengers;
		this.stations[3].waiting = passengers;
		this.stations[4].waiting = passengers;
		this.stations[5].waiting = passengers;
		this.stations[6].waiting = passengers;
		this.stations[7].waiting = passengers;
	
		while(remaining>0){
			this.stations[i].waiting++;
			remaining--;
			i++;	
		}

		for (i=0; i<8; i++){
			System.out.println(this.stations[i].waiting+" passengers waiting at station "+this.stations[i].name);
		}
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
		Semaphore sem;
		int x;
		for(int i=0; i<16; i++){
			// Get input
			System.out.println("Train "+(i+1));
			System.out.print("Number of Seats: ");
			x = reader.nextInt();

			sem = new Semaphore(x);

			System.out.println("Created Train "+(i+1)+": "+x+" seats");
			this.trains[i] = new Train("Train"+i, sem, x, stations);
		}
	}



}