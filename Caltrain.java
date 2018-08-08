package semaphore;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Caltrain{
		
	static Station[] stations = new Station[8];
	static Train[] trains = new Train[16];
	
	static int getTrainCount(int passengers) {
		int count = 0;
		int i=0;
		while (passengers > count){
		    count += trains[i].max_seats;
		    i++;
		    if(i==16)
		    	break;
		}
		return i;
	}
	
	
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
		
		trains[0].isTrainOne = true;
	}
	
	static void dispatchTrain(int i) {
		trains[i].start();
	}
	
	public static void main(String[]args) {
		Passenger passengers[];
		station_init();
		Scanner reader =  new Scanner(System.in);
		System.out.println("Total Passengers: ");
		int x = reader.nextInt();
		int counter=0;
		passengers = new Passenger[x];
		distribute_pass(x);
		for(int i = 0; i<x ; i++) {
			passengers[i] = new Passenger("Pass#"+(i+1), stations[i%8], stations[(i+5)%8]);
			passengers[i].start();
		}
		train_init();
		int train_count = getTrainCount(x);
		trains[train_count-1].isLastTrain = true;
		trains[train_count-1].last_station = stations[7];
		int undispatchedTrain = train_count;
		
		//function as dispatcher
		while(undispatchedTrain!=0) {
			//System.out.println("attempt mutex");
			//stations[0].mutex.acquire();
			System.out.println("attempt dispatch.");
			try{
				stations[0].dispatch.acquire();
			} 
			catch (InterruptedException e){
				e.printStackTrace();
			}
			System.out.println("Train " + (counter+1) + " dispatched ");
			//----------- TRAIN DISPATCHES -----------
			dispatchTrain(counter);
			counter++;
			undispatchedTrain--;
		}		
	}
	




}