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
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;

import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

import at.tuwien.flightfinder.beans.XMLDateAdapter;
import at.tuwien.flightfinder.dao.AirportDAO;


@XmlRootElement(name = "Flight")
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso( {DestinationAirport.class, OriginAirport.class, Airport.class} )
@XmlType(factoryClass=ObjectFactory.class, factoryMethod="createFlightoffers")
@CsvRecord(separator = ",", skipFirstLine = false,crlf = "MAC")

@Entity
public class Flightoffer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="from_id")
	@Link
	private OriginAirport fromAirport;


	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="to_id")
	@Link
	private DestinationAirport toAirport;


	@DataField(pos = 4)
	private String nameOrigin;

	@DataField(pos = 6)
	private String nameDestination;

	@DataField(pos = 2)
	private String airCompany;

	@DataField(pos = 1)
	private String flightNumber;

	@DataField(pos = 7, pattern = "yyyyMMdd")
	private Date flightDate;

	@DataField(pos = 9)
	private String ticketId;

	@DataField(pos = 10)
	private int price;

	@Enumerated(EnumType.STRING)
	@DataField(pos = 8)
	private FlightClass flightClass;

	private List<Hotel> hotels;
	
	private Date insertDate;

	public Flightoffer(OriginAirport fromAirport, DestinationAirport toAirport,
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
		this.insertDate = new Date();

	}


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

	//@XmlMixed
	//@XmlElementRef(type = OriginAirport.class, name = "IATACodeOrigin")
	//@XmlAnyElement
	public OriginAirport getFromAirport() {
		System.out.println("getterOffer: "+fromAirport.getFromIataCode());
		return fromAirport;
	}
	
	public boolean isEuropean(Exchange exchange){
		AirportDAO airDAO = new AirportDAO();
		List<Airport> euIataCodesList = airDAO.getAllAirports();
		Boolean flag = false;
		for(Airport ap: euIataCodesList){
			OriginAirport oA  =  exchange.getIn().getBody(Flightoffer.class).getFromAirport();
					if (oA.getFromIataCode().trim().contains(ap.toString()))
						flag = true;
		}
		return flag;
	}

	public void setFromAirport(OriginAirport airportOrigin) {
		System.out.println("setterOffer: "+airportOrigin.getFromIataCode());
		this.fromAirport = airportOrigin;	
	}
	
	//@XmlMixed
    //@XmlElementRef(type = DestinationAirport.class, name = "IATACodeDestination")
	//@XmlAnyElement
	public DestinationAirport getToAirport() {
		return toAirport;
	}

	public void setToAirport(DestinationAirport toAirport) {
		this.toAirport = toAirport;
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
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@XmlElement(name = "Class")
	public FlightClass getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(FlightClass flightClass) {
		this.flightClass = flightClass;
	}
}
