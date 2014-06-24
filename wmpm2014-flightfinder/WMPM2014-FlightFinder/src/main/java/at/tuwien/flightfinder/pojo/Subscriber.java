package at.tuwien.flightfinder.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * Subscriber entity
 * @author Ivan Gusljesevic
 */
@Entity
public class Subscriber {
	@Id
	@GeneratedValue
	private long id;
	private String email;
	private String name;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="airport_id")
	private Airport airport;
	
	Subscriber(){
		
	}
	public Subscriber(String email, String name, Airport airport) {
		this.email = email;
		this.name = name;
		this.airport = airport;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}

}
