package at.tuwien.flightfinder.app;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import at.tuwien.flightfinder.config.FtpRouteConfig;

/**
 * This class starts ONLY the Camel context. Routes and components are added manually.
 * After 30s the context is stopped and the application terminated.
 */
public class FlightFinderSimple {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new FtpRouteConfig());
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		ActiveMQComponent activeMQcomp = new ActiveMQComponent();
		activeMQcomp.setConnectionFactory(connectionFactory);
		
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
