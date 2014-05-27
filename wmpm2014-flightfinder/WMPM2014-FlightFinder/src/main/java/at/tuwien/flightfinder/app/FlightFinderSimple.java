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
	
}
