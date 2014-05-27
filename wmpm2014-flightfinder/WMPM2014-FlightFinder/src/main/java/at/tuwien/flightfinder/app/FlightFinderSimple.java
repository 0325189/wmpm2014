package at.tuwien.flightfinder.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.HotelDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Hotel;
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
		// Populates the databses with European IATA codes
		PopulateIATACodes();
		PopuplateHotelData();
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
		context.addRoutes(new MailToFileOffersRoute());
		context.addRoutes(new CbrRecievedFile());
		context.addRoutes(new OfferProcessingRoute());
		//context.addRoutes(new PromotionProcessingRoute());

		
		// lets run it...
		context.start();
		System.out.println("CamelContext started");

		// wait for 30 seconds
		Thread.sleep(30000);

		// lets end it
		context.stop();
		System.out.println("CamelContext stopped");

	}
	
	//Populates databses with European IATA codes
	public static void PopulateIATACodes()
	{
		AirportDAO airportDao1 = new AirportDAO();
		Airport airport = new Airport("");
		
		Scanner sc;
		try 
		{
			sc = new Scanner(new File("mojTest/IATAEuropeanCodes.csv"));
			sc.useDelimiter("\n");
			
			while(sc.hasNext())
			{
				airport.setIataCode(sc.nextLine());
				airportDao1.addAirport(airport);
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	

	//Populated databases with hotel data
	public static void PopuplateHotelData()
	{
		AirportDAO airportDao1 = new AirportDAO();
		HotelDAO hotelDao1 = new HotelDAO();
		Airport airport1 = airportDao1.getAirportByIataCode("CDG");
		
		Hotel hiltonHotel = new Hotel("Hitlon", 5, airport1);
		Hotel emiratesHotel = new Hotel("Emirates", 7, airport1);

		hotelDao1.addHotel(hiltonHotel);
		hotelDao1.addHotel(emiratesHotel);
	}
	

}
