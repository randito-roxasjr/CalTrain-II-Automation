import java.util.Scanner;

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
		open_stations();

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
			this.trains[i] = new Train(x);
		}
	}
	////////////////// Open All Stations //////////////////
	public void open_stations(){
		
		for(int i=0; i<8; i++){
			stations[i].open();
		}

	}

	////////////////// A train arrives at the station; count=seats and is treated as an input //////////
	public void station_load_train(int count){	
		/* Has no available seat, just let the train pass by. */
		if (count <= 0)
			return;

		lock_acquire(&station->lock);
		station->free_seats = count;
		station->boarding_passengers = 0;

		while (station->waiting_passengers > 0 && station->free_seats > 0) {
			/* Notify there are available seats. */
			cond_signal(&station->has_waiting_seats, &station->lock);
			lock_release(&station->lock);
			lock_acquire(&station->lock);
		}

			
		/* Waiting until all passengers onboarded. */
		while (station->boarding_passengers > 0) {
			lock_release(&station->lock);
			lock_acquire(&station->lock);
	    }

		/* Train is leaving, mark there is no seat available. */
		station->free_seats = 0;
		lock_release(&station->lock);		
	}

	////////////////// station waits for the train //////////////////
	public void station_wait_for_train(){
		lock_acquire(&station->lock);

		/* Wait for seat. */
		station->waiting_passengers = station->waiting_passengers + 1;
		
		while (station->free_seats <= 0) {
			cond_wait(&station->has_waiting_seats, &station->lock);
		}

		/* Book the seat. */
		station->free_seats = station->free_seats - 1;
		/* Boarding */
	
		station->boarding_passengers = station->boarding_passengers + 1;
		lock_release(&station->lock);
		// Will call station_on_board after this returns.
	}

	////////////////// Passengers are seated //////////////////
	public void station_on_board(){
		// Let train know passengers are on board
		lock_acquire(&station->lock);
		station->waiting_passengers = station->waiting_passengers - 1;
		station->boarding_passengers = station->boarding_passengers - 1;
		lock_release(&station->lock);
	}

}