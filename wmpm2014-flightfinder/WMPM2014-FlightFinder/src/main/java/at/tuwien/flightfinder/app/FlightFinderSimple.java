package at.tuwien.flightfinder.app;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.dao.SubscriberDAO;
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
		JndiContext jndiContext = new JndiContext();
		jndiContext.bind("airportDAO", new AirportDAO());
		jndiContext.bind("flightOfferDAO", new FlightofferDAO());

		CamelContext context = new DefaultCamelContext(jndiContext);

		
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
		context.addRoutes(new NewsletterMailRoute());

		
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
