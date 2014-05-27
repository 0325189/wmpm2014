package at.tuwien.flightfinder.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
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
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import at.tuwien.flightfinder.dao.AirportDAO;
import at.tuwien.flightfinder.dao.HotelDAO;
import at.tuwien.flightfinder.pojo.Airport;
import at.tuwien.flightfinder.pojo.Hotel;


public class OffersEnricher implements Processor {

	AirportDAO airportDao1 = new AirportDAO();
	HotelDAO hotelDao1 = new HotelDAO();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		String IATACode = "";
	
		String message = exchange.getIn().getBody(String.class);
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(message));
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		        
		Element flightNode = (Element) doc.getElementsByTagName("Flight").item(0);
		Element iataCode = (Element) flightNode.getElementsByTagName("IATACodeOrigin").item(0);
		
		System.out.println("IATA Code: " + iataCode.getTextContent());
		
		List<Hotel> list = hotelDao1.getHotelByDestAirport(iataCode.getTextContent());
		
		for (Hotel item : list)
			System.out.println("$$$$$HOTEL$$$$$$$" + item.getName());
		
	}
	
}
	
	

