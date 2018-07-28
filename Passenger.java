import java.util.concurrent.*;

class Passenger implements Runnable{
	private String name;
	private Thread robot;
	private boolean active;
	private Semaphore sem = new Semaphore(1);

	Passenger(String name){
		this.name = name;
	}

	public void run(){
		;
	}
}