package at.tuwien.flightfinder.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
//import org.apache.camel.spring.javaconfig.Main;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightFinderConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new FtpRouteConfig());

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");	
		context.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		context.start();
		System.out.println("CamelContext started");

		// wait for 30 seconds (in which the route will watch out for the endpoint)
		Thread.sleep(30000);

		context.stop();
		System.out.println("CamelContext stopped");

	}

	//	/**
	//    * Returns the CamelContext which support Spring
	//    */
	//	protected CamelContext createCamelContext() throws Exception {
	//		return new SpringCamelContext(getApplicationContext());
	//	}
	//	
	//    protected void setupCamelContext(CamelContext camelContext) throws Exception {
	//        // nothing here :( yet :)

	//    }
	//	
	//	public void afterPropertiesSet() throws Exception {
	//        // just to make Spring happy do nothing here
	//    }

}
