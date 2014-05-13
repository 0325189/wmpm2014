package SampleApp;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class SampleApp {

	public static void main(String[] args) throws Exception {

		// we need a CamelContext 
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new SampleFileRoute());
		
		// fire up the context
		context.start();
		System.out.println("CamelContext started");
		
		// wait for 30 seconds (in which the route will watch out for the endpoint)
		Thread.sleep(30000);
		
		context.stop();
		System.out.println("CamelContext stopped");
	}

}
