package at.tuwien.flightfinder.app;


import java.util.Date;

import javax.swing.Box.Filler;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.FlightClass;
import at.tuwien.flightfinder.pojo.Flightoffer;
import at.tuwien.flightfinder.routes.*;

/**
 * This class starts ONLY the Camel context. Routes and components are added manually.
 * After 30s the context is stopped and the application terminated.
 */
public class FlightFinderSimple {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		// Set up CamelContext
		CamelContext context = new DefaultCamelContext();
		
		// Add ActiveMQ component to the context
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		ActiveMQComponent activeMQcomp = new ActiveMQComponent();
		activeMQcomp.setConnectionFactory(connectionFactory);
		context.addComponent("activemq", activeMQcomp);
		
		// add routes to the context

		//context.addRoutes(new FtpRouteConfig());
		//context.addRoutes(new HttpRouteConfig());
		//context.addRoutes(new MailToFileOffersRoute());
		//context.addRoutes(new CbrRecievedFile());
		//context.addRoutes(new OfferProcessingRoute());
		//context.addRoutes(new NewsletterMailRoute());
		context.addRoutes(new TwitterFacebookRoute());

		
		// lets run it...
		context.start();
		System.out.println("CamelContext started");
		//fillDb();
		// wait for 30 seconds
		Thread.sleep(30000);

		// lets end it
		context.stop();
		System.out.println("CamelContext stopped");

	}
	
	/*public static void fillDb()
	{
		FlightofferDAO foDAO = new FlightofferDAO();
		AirportDAO aDAO = new AirportDAO();
		
		Airport fromAirport = aDAO.getAirportByIataCode("CDG");
		Airport toAirport = aDAO.getAirportByIataCode("MAD");
		
		Flightoffer flightOffer = new Flightoffer();
		
		flightOffer.setFromAirport(fromAirport);
		flightOffer.setToAirport(toAirport);
		flightOffer.setNameOrigin("Paris");
		flightOffer.setNameDestination("Madrid");
		flightOffer.setAirCompany("Lufthansa");
		flightOffer.setFlightNumber("001");
		flightOffer.setFlightDate(new Date());
		flightOffer.setTicketId("12345");
		flightOffer.setPrice(99);
		flightOffer.setFlightClass(FlightClass.Business);
		
		
		foDAO.addFlightoffer(flightOffer);
		
	}*/
	
}
