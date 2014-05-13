package SampleApp;

import org.apache.camel.builder.RouteBuilder;

public class SampleFileRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// DSL to rule.. ehm.. route them all
		// Consumer Endpoint - file that should not be moved or deleted
		// fileName could be provided as well, but we only look at all files
		from("file:flight_input_data?noop=true")
		
		.process(new SampleFlightLogger())
		
		.bean(new SampleAirportCorrection(), "correctNames")
			
		.split(body().tokenize(","))
		
		// Content Based Routing
		.choice()
			.when(body().startsWith("VIE")).transform().simple("${body}"+" | ").to("file:flight_data/VIE/?fileExist=Append")
			.when(body().startsWith("MUC")).to("file:flight_data/MUC/?fileExist=Append")
			// we can use jUnit to test this... 
			.otherwise().to("mock:others");
		
	
		
	}

}
