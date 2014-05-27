package at.tuwien.flightfinder.beans;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import at.tuwien.flightfinder.dao.HotelDAO;
import at.tuwien.flightfinder.pojo.Hotel;


public class OffersEnricher implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		//Parsing the xml from the message
		String xmlRecords = exchange.getIn().getBody(String.class);

		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlRecords));

		Document doc = db.parse(is);

		//get the xml structure
		Element rootElement = doc.getDocumentElement();
		NodeList nodes = rootElement.getChildNodes();
		
		//retrieve the value of the iataCodeOrigin-tag
		Node iataCodeNode = nodes.item(2);
		String iataCode = iataCodeNode.getTextContent();
		
//		System.out.println("This is body: "+xmlRecords);
//		System.out.println("These is my root element: "+rootElement.getNodeName());
//		System.out.println("These is my iataCodeNode: "+iataCodeNode.getNodeName());
//		System.out.println("This is the value of my IATACode: "+iataCode);
		
		
		//Create the HotelList-tag which contains only hotel-tags but no values
		Element hotelsListNode = doc.createElement("HotelsList");
		rootElement.appendChild(hotelsListNode);
		
		//Pulling hotel-data from the database
		HotelDAO myHotelDAO = new HotelDAO();
		List<Hotel> hotels = myHotelDAO.getHotelByDestAirport(iataCode);
		
		//Iterate through the List of hotels and store its values in the Hotel-tags
		for(Hotel item:hotels){
			//Create the Hotel-tag and append it to the document
			Element hotelNode = doc.createElement("Hotel");
			hotelsListNode.appendChild(hotelNode);
			
			//Create the NameOfHotel-tag which contains the name of the hotel from the DB
			Element NameOfHotelNode = doc.createElement("NameOfHotel");
			hotelNode.appendChild(NameOfHotelNode);
			//NameOfHotelNode.setTextContent("Hotel Hilton");
			NameOfHotelNode.setTextContent(item.getName());
			
			//Create the starsNumber-tag which contains the number of stars of the hotel retrieved from the DB
			Element starNode = doc.createElement("starsNumber");
			hotelNode.appendChild(starNode);
			//starNode.setTextContent("***");
			starNode.setTextContent(Integer.toString(item.getStarsNumber()));
		
		}
		//Create the new document
		TransformerFactory tFact = TransformerFactory.newInstance();
		Transformer trans = tFact.newTransformer();

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);
		//System.out.println(writer.toString());	

		//write the xml-document to message
		exchange.getOut().setBody(writer.toString());
	}

}



