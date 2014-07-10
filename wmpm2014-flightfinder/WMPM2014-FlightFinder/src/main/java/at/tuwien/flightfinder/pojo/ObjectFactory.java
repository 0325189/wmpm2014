package at.tuwien.flightfinder.pojo;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public static Flightoffer createFlightoffers() {
        return new Flightoffer();
    }
	public static DestinationAirport createDesAirport() {
        return new DestinationAirport();
    }
	public static OriginAirport createOrgAirport() {
        return new OriginAirport();
    }
	
}
