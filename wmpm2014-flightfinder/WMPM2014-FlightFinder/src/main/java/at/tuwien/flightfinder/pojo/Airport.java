package at.tuwien.flightfinder.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.io.Serializable;

import javax.xml.bind.annotation.*;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;
/**
 * Airport entity
 * 
 * @author Ivan Gusljesevic
 */
@Entity
@XmlTransient
public class Airport implements Serializable{
	
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue
	@XmlTransient
	private long id;
	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="destinationAirport", fetch=FetchType.EAGER)
	private List<Hotel> hotels;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="fromAirport", fetch=FetchType.EAGER)
	private List<Flightoffer> flightoffers;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="airport", fetch=FetchType.EAGER)
	private List<Subscriber> subscribers;
	
	//needed for correct functioning of Bindy
	public Airport(){	
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
