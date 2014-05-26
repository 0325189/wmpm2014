package at.tuwien.flightfinder.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Airport {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String iataCode;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="destinationAirport", fetch=FetchType.EAGER)
	private List<Hotel> hotels;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="fromAirport", fetch=FetchType.EAGER)
	private List<Flightoffer> flightoffers;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="airport", fetch=FetchType.EAGER)
	private List<Subscriber> subscribers;
	Airport() {
		
	}
	public Airport(String iataCode){
		this.iataCode=iataCode;
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIataCode() {
		return iataCode;
	}
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}
	public List<Flightoffer> getFlightoffers() {
		return flightoffers;
	}
	public void setFlightoffers(List<Flightoffer> flightoffers) {
		this.flightoffers = flightoffers;
	}
	public List<Subscriber> getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(List<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}
	public List<Hotel> getHotels() {
		return hotels;
	}
	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
}
