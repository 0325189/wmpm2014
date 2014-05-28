package at.tuwien.flightfinder.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Flightoffer {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="from_id")
	private Airport fromAirport;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to_id")
	private Airport toAirport;
	private String nameOrigin;
	private String nameDestination;
	private String airCompany;
	private String flightNumber;
	private Date flightDate;
	private String ticketId;
	private int price;
	@Enumerated(EnumType.STRING)
	private FlightClass flightClass;
	
	public Flightoffer(Airport fromAirport, Airport toAirport,
			String nameOrigin, String nameDestination, String airCompany,
			String flightNumber, Date flightDate, String ticketId, int price,
			FlightClass flightClass) {
		super();
		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.nameOrigin = nameOrigin;
		this.nameDestination = nameDestination;
		this.airCompany = airCompany;
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.ticketId = ticketId;
		this.price = price;
		this.flightClass = flightClass;
	}

	public Flightoffer() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Airport getFromAirport() {
		return fromAirport;
	}

	public void setFromAirport(Airport fromAirport) {
		this.fromAirport = fromAirport;
	}

	public Airport getToAirport() {
		return toAirport;
	}

	public void setToAirport(Airport toAirport) {
		this.toAirport = toAirport;
	}

	public String getNameOrigin() {
		return nameOrigin;
	}

	public void setNameOrigin(String nameOrigin) {
		this.nameOrigin = nameOrigin;
	}

	public String getNameDestination() {
		return nameDestination;
	}

	public void setNameDestination(String nameDestination) {
		this.nameDestination = nameDestination;
	}

	public String getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public FlightClass getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(FlightClass flightClass) {
		this.flightClass = flightClass;
	}

	
}