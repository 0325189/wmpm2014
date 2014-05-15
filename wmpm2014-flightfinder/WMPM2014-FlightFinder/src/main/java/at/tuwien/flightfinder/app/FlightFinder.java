package at.tuwien.flightfinder.app;
import org.apache.camel.main.Main;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.config.FlightFinderConfig;

/**
 * AnnotationConfigApplicationContext is a Spring standalone application context which accepts annotated classes 
 * as input. Annotation @Component will be mostly used for Camel routes. There is also provided @Configuration 
 * class for CamelContext.
 *  
 * @author seferovic
 */
 
@Component
public class FlightFinder extends Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext(FlightFinderConfig.class);
		 FlightFinder ffMain = springContext.getBean(FlightFinder.class);		 
	}
}
