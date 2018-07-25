class Station implements Runnable{
	private Passenger[] passengers;
	private Thread t;
	private String Name;

	////////////////// Contructor station_init //////////////////
	Station(String N){
		this.Name = N;
		System.out.println("Created Station " + this.Name);
	}

	public void run(){
		;
	}

}