package at.tuwien.flightfinder.app;

import org.apache.log4j.Logger;

import org.apache.camel.main.Main;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.config.FlightFinderConfig;

/**
 * AnnotationConfigApplicationContext is a Spring standalone application context which accepts annotated classes 
 * as input. Annotation @Component will be mostly used for Camel routes. There is also provided @Configuration 
 * class for CamelContext.
 */
 
@Component
public class FlightFinder extends Main {
	
	private static final Logger logger = Logger.getLogger(FlightFinder.class);

	public static void main(String[] args) throws Exception {
		 System.setProperty("spring.profiles.active", "production");
		 new AnnotationConfigApplicationContext(FlightFinderConfig.class);
		 new FlightFinder().runApp();	
	}
	
	public void runApp() {
		
		super.enableHangupSupport();
		
		try {
            super.run();
        } catch (Exception e) {
            logger.error("Cannot start app with Spring");
            logger.error(e.toString());
            return;
        }
	}
}
