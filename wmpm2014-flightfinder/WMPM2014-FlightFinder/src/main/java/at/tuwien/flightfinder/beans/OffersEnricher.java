package at.tuwien.flightfinder.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.xalan.xsltc.compiler.sym;
import org.h2.util.IOUtils;
import org.w3c.dom.Document;

import at.tuwien.flightfinder.dao.HotelDAO;
import at.tuwien.flightfinder.pojo.Hotel;


public class OffersEnricher implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		HotelDAO hotel = new HotelDAO();	    
		String originalBody = exchange.getIn().getBody(String.class);
		//DocumentBuilder
		//parse it and extract the IATAcode
		//String iataCode = "";
		//List<Hotel> list = hotel.getHotelByDestAirport(iataCode);
		//create xml tags with Stringbuffer
		
	}
	
}
	
	

