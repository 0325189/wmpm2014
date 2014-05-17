package at.tuwien.flightfinder.beans;


import java.io.Serializable;
import javax.xml.bind.annotation.*;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CsvRecord(separator = ",", skipFirstLine = true,crlf = "MAC")

public class FlightsDTO implements Serializable{

	//private static final long serialVersionUID = 1L;

	
	@DataField(pos = 1)
	private String FlightNumber;
    
	@DataField(pos = 2)
	private String AirCompany;
    

	@DataField(pos = 3)
	private String NameOrigin;
    

	@DataField(pos = 4)
	private String Destination;
	
	@DataField(pos = 5)
	private String FlightDate;

	@DataField(pos = 6)
	private String Class;

	@DataField(pos = 7)
	private String TicketID;
	
	@DataField(pos = 8)
	private String Price;

}