package at.tuwien.flightfinder.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Hotel {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private int starsNumber;
	@ManyToOne
	@JoinColumn(name="FlightO_id")
	private Flightoffer flightoffer;
	Hotel(){
		
	}
	public Hotel( String name, int starsNumber) {

		this.name = name;
		this.starsNumber = starsNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStarsNumber() {
		return starsNumber;
	}
	public void setStarsNumber(int starsNumber) {
		this.starsNumber = starsNumber;
	}
	public Flightoffer getFlightoffer() {
		return flightoffer;
	}
	public void setFlightoffer(Flightoffer flightoffer) {
		this.flightoffer = flightoffer;
	}
	
}
