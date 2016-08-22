package com.example.xslt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlHandlerTest {

	public static void main (String[] args){
		
		XmlHandlerTest xmlHandlerTest = new XmlHandlerTest();
		xmlHandlerTest.validateTestHappyPath();
		xmlHandlerTest.transformTestHappyPath();			
	}
	
	public void validateTestHappyPath (){
		
		String xml = null;
		String xsd = null;
		boolean result = false;
		
		try {
			xml = new String(Files.readAllBytes(Paths.get("shipment.xml")));
			xsd = new String(Files.readAllBytes(Paths.get("shipment.xsd")));

			XmlHandler xmlHandler = new XmlHandler();
			result = xmlHandler.validate(xml, xsd);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void transformTestHappyPath (){
		
		String xml = null;
		String xsl = null;
		String result = null;
		
		try {
			xml = new String(Files.readAllBytes(Paths.get("shipment.xml")));
			xsl = new String(Files.readAllBytes(Paths.get("shipment.xsl")));

			XmlHandler xmlHandler = new XmlHandler();
			result = xmlHandler.transform(xml, xsl);			
			
			System.out.println("Test Result is: \n" + result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}
