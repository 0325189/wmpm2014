package at.tuwien.flightfinder.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * Hotel entity
 * @author Ivan Gusljesevic
 */
@Entity
public class Hotel {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private int starsNumber;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="destAirport_Id")
	private Airport destinationAirport;

	Hotel(){
		
	}
	public Hotel( String name, int starsNumber, Airport destAirport) {

		this.name = name;
		this.starsNumber = starsNumber;
		this.destinationAirport=destAirport;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Airport getDestinationAirport() {
		return destinationAirport;
	}
	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	
}
