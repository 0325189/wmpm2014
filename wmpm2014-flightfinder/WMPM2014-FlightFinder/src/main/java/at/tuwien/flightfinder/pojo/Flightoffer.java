package at.tuwien.flightfinder.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

import at.tuwien.flightfinder.beans.XMLDateAdapter;


@XmlRootElement(name = "Flight")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(factoryClass=ObjectFactory.class, factoryMethod="createFlightoffers")
@CsvRecord(separator = ",", skipFirstLine = false,crlf = "MAC")
@Entity
public class Flightoffer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@XmlTransient
	private long id;

	@DataField(pos = 1)
	private String flightNumber;
	
	@DataField(pos = 2)
	private String airCompany;

	@DataField(pos = 3)
	private String fromIataCode;
	
	@DataField(pos = 4)
	private String nameOrigin;
	
	@DataField(pos = 5)
	private String toIataCode;

	@DataField(pos = 6)
	private String nameDestination;

	@DataField(pos = 7, pattern = "yyyyMMdd")
	private Date flightDate;

	@Enumerated(EnumType.STRING)
	@DataField(pos = 8)
	private FlightClass flightClass;
	
	@DataField(pos = 9)
	private String ticketId;

	@DataField(pos = 10)
	private String price;


	private List<Hotel> hotels;
	private Date insertDate;

	public Flightoffer(String fromAirport, String toAirport,
			String nameOrigin, String nameDestination, String airCompany,
			String flightNumber, Date flightDate, String ticketId, String price,
			FlightClass flightClass) {
		super();
		this.fromIataCode = fromAirport;
		this.toIataCode = toAirport;
		this.nameOrigin = nameOrigin;
		this.nameDestination = nameDestination;
		this.airCompany = airCompany;
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.ticketId = ticketId;
		this.price = price;
		this.flightClass = flightClass;
		this.insertDate = new Date();

	}

	//Konstruktor
	public Flightoffer() {
		this.insertDate = new Date();
	}


	@XmlTransient
	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	@XmlTransient
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	@XmlElement(name = "IATACodeOrigin")
	public String getFromIataCode() {
		return fromIataCode;
	}

	public void setFromIataCode(String fromIataCode) {
		this.fromIataCode = fromIataCode;	
	}
	

	@XmlElement(name = "IATACodeDestination")
	public String getToIataCode() {
		return toIataCode;
	}

	public void setToIataCode(String toIataCode) {
		this.toIataCode = toIataCode;
	}

	@XmlElement(name = "Origin")
	public String getNameOrigin() {
		return nameOrigin;
	}


	public void setNameOrigin(String nameOrigin) {
		this.nameOrigin = nameOrigin;
	}

	@XmlElement(name = "Destination")
	public String getNameDestination() {
		return nameDestination;
	}

	public void setNameDestination(String nameDestination) {
		this.nameDestination = nameDestination;
	}

	@XmlElement(name = "AirCompanyName")
	public String getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(String airCompany) {
		this.airCompany = airCompany;
	}

	@XmlElement(name = "FlightNumber")
	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	@XmlElement(name = "FlightDate")
	@XmlJavaTypeAdapter(XMLDateAdapter.class)
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	@XmlElement(name = "TicketID")
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	@XmlElement(name = "Price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@XmlElement(name = "Class")
	public FlightClass getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(FlightClass flightClass) {
		this.flightClass = flightClass;
	}
	
	@Override
	public String toString(){
		return "id: "+id+"\nfromAirport: "+fromIataCode+"\ntoAirport: "+toIataCode+"\nnameOrigin: "+nameOrigin+"\nnameDestination: "+nameDestination+"\nairCompany: "+airCompany+
				"\nflightDate: "+flightDate+"\nticketId: "+ticketId+"\nprice: "+price+"\nflightClass: "+flightClass;
	}
		
	
}
