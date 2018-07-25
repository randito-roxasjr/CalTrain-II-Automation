class Train{
	private Integer seats;

	////////////////// Contructor station_init //////////////////
	public Train(Integer N){
		this.seats = N;
		System.out.println("Created Train: "+this.seats+" seats");
	}

	public Integer getSeats(){
		return this.seats;
	}
}