package at.tuwien.flightfinder.pojo;

import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.*;

import org.apache.camel.Exchange;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

import at.tuwien.flightfinder.dao.AirportDAO;

@Link
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=ObjectFactory.class, factoryMethod="createOrgAirport")
@Entity
public class OriginAirport extends Airport {

	private static final long serialVersionUID = 1L;
	

	@DataField(pos = 3)
	private String fromIataCode;
	
	//@XmlID
	@XmlElement
	//@XmlValue
	public String getFromIataCode() {
		return fromIataCode;
	}


	public void setFromIataCode(String fromIataCode) {
		System.out.println("setterOrigin: "+fromIataCode);
		this.fromIataCode = fromIataCode;
	}
	public OriginAirport(String fromIataCode){
		this.fromIataCode = fromIataCode;
	}
	
	public OriginAirport(){
	}
	
}
