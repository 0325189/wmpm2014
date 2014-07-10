package at.tuwien.flightfinder.routes;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mail.SplitAttachmentsExpression;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import at.tuwien.flightfinder.beans.Archive;
import at.tuwien.flightfinder.beans.EnrichWithSubscribers;
import at.tuwien.flightfinder.beans.IncommingMailProcessor;
import at.tuwien.flightfinder.beans.JSONStream;
import at.tuwien.flightfinder.beans.MarketingProcessor;
import at.tuwien.flightfinder.beans.PrintFlightoffer;
import at.tuwien.flightfinder.dao.FlightofferDAO;
import at.tuwien.flightfinder.dao.HotelDAO;

@Component
public class MainRouteBuilder extends RouteBuilder {

	@Autowired
	Archive archive;
	@Autowired
	FlightofferDAO flightOfferDAO;
	@Autowired
	HotelDAO hotelDAO;
	@Autowired
	EnrichWithSubscribers enrichWithSubscribers;
	@Autowired
	Environment prop;
	@Autowired
	MarketingProcessor marketingProcessor;
	private String twitterEndpoint = "twitter://timeline/user";
	private String facebookEndpoint = "facebook://postStatusMessage?inBody=message";
	XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
	public static final String DLCH = "activemq:dead-letter";


	@Override
	public void configure() throws Exception {


		/**
		 **--------------------
		 **HttpRouteConfig
		 **--------------------
		 */

		//errorHandler(deadLetterChannel(MainRouteBuilder.DLCH).useOriginalMessage());
		
		
		from("stream:url?url=http://www.tesla-gui.at/http/offers.json&groupLines=100").
		routeId("Route-HTTP").
		process(new JSONStream()).id("jsonStream")
		.unmarshal(xmlJsonFormat)
		.log("Found at: ${body}")
		.to("activemq:fileOffers");



		/**
		 **--------------------
		 **FtpRouteConfig
		 **--------------------
		 */

		//from("file:mojTest?fileName=FlightFinder_FlightsDetailsInfo_v4.csv&noop=true").
		from("ftp://ftp6291381_workflow@www92.world4you.com?password=workflow2014&consumer.delay=60000&charset=ISO-8859-1").
		routeId("Route-FTP").
		log("Downloading File: ${header.CamelFileName} from the FTP Server").
		//what about routing the faulty file to dead queue?? 
		validate(header("CamelFileName").regex("^[^ÜüÄäÖö]+$")).
		log("The file ${header.CamelFileName} has been validated for german special character").
		to("activemq:fileOffers").
		log("${header.CamelFileName} has been pushed to the fileOffers queue!");

		/**
		 **--------------------
		 **MailToFileOfferRoute
		 **--------------------
		 */
		
		from("imap://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&delete=false&unseen=true&consumer.delay=60000").
		routeId("Route-Mail").
		split(new SplitAttachmentsExpression()).
		process(new IncommingMailProcessor()).
		to("activemq:fileOffers").
		end();
		

		/**
		 **--------------------
		 **CbrRecievedFile
		 **--------------------
		 */


		DataFormat bindy = new BindyCsvDataFormat("at.tuwien.flightfinder.pojo");
		DataFormat jaxb = new JaxbDataFormat("at.tuwien.flightfinder.pojo");

		from("activemq:fileOffers").
		routeId("Route-CBR").
		choice().
		when(header("CamelFileName").regex("^.*(xml)$")).
		log("XML file found on CBR: ${header.CamelFileName}").
		split().tokenizeXML("Flight").log("---before---${body}").unmarshal(jaxb).setHeader("iataCode", simple("${in.body.fromIataCode}")).process(new PrintFlightoffer()).
		log("XML file ${header.CamelFileName} has been splitted.").
		to("activemq:Offers").
		log("-------------Finished XML---------").
		endChoice().

		when(header("CamelFileName").regex("^.*(csv|csl)$")).
		log("CVS file found on CBR: ${header.CamelFileName}").
		split(body().tokenize(";")).log("---before---${body}").
		log("CSV file ${header.CamelFileName} has been splitted.").
		unmarshal(bindy).process(new Processor() {


			/*The Bindy component returned is a List of Map objects. Each Map within the list contains
			the model objects that were marshalled out of each line of the CSV. The reason behind
			this is that each line can correspond to more than one object.
			When we simply expect one object to be returned per line we need to use the processor specified in the 
			camel Bindy documentation.
			 */

			@Override
			public void process(Exchange exchange) throws Exception{
				List<Map<String, Object>> unmarshaledModels = (List<Map<String, Object>>) exchange.getIn().getBody();
				Message msg = exchange.getIn();
				for (Map<String, Object> model : unmarshaledModels) {
					for (String className : model.keySet()) {
						Object obj = model.get(className);
						msg.setBody(obj);
					}
				}
				exchange.setOut(msg);

			}
		}).
		log("CSV file ${header.CamelFileName} has been unmarshaled into Flighfinder POJO.").


		//setHeader("iataCode").simple("${body.fromIataCode}")  --> if not working try this!
		to("activemq:Offers").setHeader("iataCode", simple("${body.fromIataCode}")).process(new PrintFlightoffer()).
		log("-------------Finished CSV---------").endChoice().
		otherwise().to("activemq:dead-letter")
		.end();


		/**
		 **--------------------
		 **OfferProcessingRoute
		 **--------------------
		 */

		from("activemq:Offers").
		routeId("Route-OfferProcess").
		log("Message has been pulled from Offers queue").
		filter().method(flightOfferDAO, "lookupEuropeanIata").
		log("Message has been filtered and is being pushed to enricher").
		process(new PrintFlightoffer()).

		//		process(new OffersEnricher()).
		//		log("Message has been eriched with hotels and is being pushed to enricher").
		//		wireTap("file://offers_archive?fileName=${date:now:yyyy-MM-dd}.xml&fileExist=Append").
		bean(archive).
		log("Message has been stored using WireTap");


		/**
		 **--------------------
		 **NewsletterMailRoute
		 **--------------------
		 */


		from("timer:newsletter?period=60000"). //can be set to specific time "time=yyyy-MM-dd HH:mm:ss" or just set the period to one day "period=86400000"
		routeId("Route-Newsletter").
		log("--------------------timer fired..--------------------------------").
		bean(flightOfferDAO , "getTodaysFlightoffers").id("flightOfferBean").
		split(body()).
		process(enrichWithSubscribers).
		to("velocity:file:data/newsletter.vm").id("velocityTemplate").

		to("smtp://188.40.32.121?username=workflow@seferovic.net&password=workflowpassword&contentType=text/html").
		log("-------------------FINISHED--------------------------------------");


		/**
		 **--------------------
		 **TwitterFacebookRoute
		 **--------------------
		 */


		loadFacebookKeys();

		//onException(FacebookException.class).redeliveryDelay(60000).maximumRedeliveries(3).continued(true);
		//onException(TwitterException.class).redeliveryDelay(60000).maximumRedeliveries(3).continued(true);

		from("timer:socialMarketing?period=86400000"). //can be set to specific time "time=yyyy-MM-dd HH:mm:ss" or just set the period to one day "period=86400000"	
		routeId("Route-Social").
		log("timer fired..").
		process(marketingProcessor).id("MarketingProcessor").
		multicast().parallelProcessing().
		to(facebookEndpoint, twitterEndpoint).
		to("log:Succesful!!!!");

	}

	private void loadFacebookKeys() {
		facebookEndpoint += "&userId=" + prop.getProperty("FlightFinder_userId");
		facebookEndpoint += "&oAuthAppId=" + prop.getProperty("FlightFinder_oAuthAppId");
		facebookEndpoint += "&oAuthAppSecret=" + prop.getProperty("FlightFinder_oAuthAppSecret");
		facebookEndpoint += "&oAuthAccessToken=" + prop.getProperty("FlightFinder_oAuthAccessToken");



		/**
		 **--------------------
		 **DeadletterChannel
		 **--------------------
		 */
		
		from(DLCH).log(LoggingLevel.INFO, "Message in dead letter queue");



	}
}






