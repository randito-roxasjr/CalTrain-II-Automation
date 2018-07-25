import java.util.ArrayList;

class Station implements Runnable{
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private Thread t;
	private String Name;
	private int N=0;

	////////////////// Contructor station_init //////////////////
	Station(String N){
		this.Name = N;
		System.out.println("Created Station " + this.Name);
	}

	public void run(){
		System.out.println("Station "+this.Name+" running.");
		String str = Integer.toString(N);
		Passenger robot;
		for(int i=0; i<5; i++){
			
			//Sleep before adding Passengers to station
			try{
				t.sleep(1000);
				robot = new Passenger(str);
				passengers.add(robot);
				N += 1;
			} 
			catch(InterruptedException e){
				System.out.println("Thread "+this.Name+" interrupted.");
			}
		}

		System.out.println("5 new passengers arrived at Station "+this.Name);
				
	}

	////////////////// Start Station Service //////////////////
	public void open(){
		System.out.println("Station "+this.Name+" is now open.");
		if (t == null){
			t = new Thread(this, this.Name);
			t.start();
		}
	}

}