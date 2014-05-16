package at.tuwien.flightfinder.beans;

import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;


public class IncommingMailProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
	    Map<String, DataHandler> attachments = exchange.getIn().getAttachments();
	    
	     if (attachments.size() > 0) {
	    	 for (String name : attachments.keySet()) {
	             DataHandler dh = attachments.get(name);
	            
	             // get the file name
	             String filename = dh.getName();
	             
	             
	             if (validateFileExtn(filename))
	             {
		             System.out.println("Validating File " + filename);
		             	
		             //validate xml structure
		             if(filename.endsWith(".xml") && validateXmlStructure(dh.getInputStream()))
		             {
		            	 System.out.println("XML File Structure Validated: " + filename);
			             exchange.getOut().setBody(dh.getContent()); 
		             }
		             else if (filename.endsWith(".csv"))
		            	 exchange.getOut().setBody(dh.getContent());
		             
		         }
	             else
	            	 System.out.println("File extension for file "+ filename + "not valid!");
	             
	           	}
	         }
	     }
	
	
	private static Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(csv|xml))$)");
	
	//Validates file extensions (XML, CSV)
	public static boolean validateFileExtn(String userName){
        
        Matcher mtch = fileExtnPtrn.matcher(userName);
        if(mtch.matches()){
            return true;
        }
        return false;
    }
	
	//Validates xml structure
	public static boolean validateXmlStructure(InputStream is)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			// the "parse" method also validates XML, will throw an exception if misformatted
			Document document = builder.parse(is);
					
			//Do something with xml document
			return true;
		}
		catch (Exception ex)
		{
			System.out.println("Xml Structure not valid " + ex.getMessage());
			return false;
		}
		
	}
	
	
	}
	
	

