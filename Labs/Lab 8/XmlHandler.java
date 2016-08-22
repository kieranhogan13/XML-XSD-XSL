package com.example.xslt;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XmlHandler {

	public boolean validate(String xml, String xsd){
		
		boolean result = false;
		
		try{
			
			System.out.println("Creating Schema...");
			
			//Load the xml schema and create a validator for it...
			SchemaFactory factory =
				SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(new StringReader(xsd)));
			Validator validator = schema.newValidator();
			
			System.out.println("Creating XML doc...");

			//Load the xml file...
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(xml)));
			
			System.out.println("Validating XML....");
			
			//Validate the xml file against the schema...
			validator.validate(new DOMSource(doc));
			System.out.println("Validation Success");
			
			result = true;
		}
		catch (Exception e){
			System.out.println("Problem:");
			System.out.println(e.getMessage());
		}	
		
		return result;
		
	}

	public String transform(String xml, String xsl){

		StreamResult streamResult = new StreamResult(new StringWriter());
		System.out.println("Attempting transformation...");
		
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tFactory.newTransformer(new StreamSource(new StringReader(xsl)));
			transformer.transform(new StreamSource(new StringReader(xml)), streamResult);
			
			//System.out.println("XML>>>>>>>>>> " + xml);
			//System.out.println("XSL>>>>>>>>>> " + xsl);
			
			System.out.println("Transformation complete");
			
		} catch (TransformerConfigurationException e) {
			System.out.println("Problem:");
			System.out.println(e.getMessage());		
		} catch (TransformerException e) {
			System.out.println("Problem:");
			System.out.println(e.getMessage());
		}		
	
		return streamResult.getWriter().toString();
	}	
	
}
