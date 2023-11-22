package pfe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class xmllll {
	
	
	
	static String[] get_element_xml_mysql() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, XPathExpressionException
	{
		
		String tab[] = new String[3];
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new FileInputStream(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml")));// same xml comments as above.

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        Element userElement = (Element) xpath.evaluate("/schema/element", document,
            XPathConstants.NODE);
       String  mysql_user = userElement.getAttribute("mysql_user");
       String  mysql_password = userElement.getAttribute("mysql_password");
       String  mysql_db = userElement.getAttribute("mysql_db");
        
       //System.out.println(id);
       //System.out.println(name);
       
       tab[0] = mysql_user;
       tab[1] = mysql_password;
       tab[2] = mysql_db;

		return tab;
		
		
	}
	static String[] get_element_xml_orcl() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, XPathExpressionException
	{
		
		String tab[] = new String[2];
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new FileInputStream(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml")));// same xml comments as above.

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        Element userElement = (Element) xpath.evaluate("/schema/element", document,
            XPathConstants.NODE);
       String  orcl_user = userElement.getAttribute("orcl_user");
       String  orcl_password = userElement.getAttribute("orcl_password");
        
       //System.out.println(id);
       //System.out.println(name);
       
       tab[0] = orcl_user;
       tab[1] = orcl_password;

		return tab;
		
		
	}
	static void set_element_xml_mysql(String user,String password,String db) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, XPathExpressionException
	{
		
		try {
	         // Create a DocumentBuilderFactory object
	         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	         // Use the factory to create a DocumentBuilder object
	         DocumentBuilder builder = factory.newDocumentBuilder();

	         // Parse the XML file into a Document object
	         Document doc = builder.parse(new FileInputStream(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml")));

	         // Get the first element in the document
	         Element firstElement = (Element) doc.getElementsByTagName("element").item(0);

	         // Change the name of the first element
	         firstElement.setAttribute("mysql_user", user);
	         firstElement.setAttribute("mysql_password", password);
	         firstElement.setAttribute("mysql_db", db);

	         // Write the updated document to a new XML file
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml"));
	         transformer.transform(source, result);

	         // Print a message indicating success
	        // System.out.println("XML file updated successfully.");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		
	}
	
	
	static void set_element_xml_orcl(String user, String password) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, XPathExpressionException
	{
		
		try {
	         // Create a DocumentBuilderFactory object
	         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	         // Use the factory to create a DocumentBuilder object
	         DocumentBuilder builder = factory.newDocumentBuilder();

	         // Parse the XML file into a Document object
	         Document doc = builder.parse(new FileInputStream(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml")));

	         // Get the first element in the document
	         Element firstElement = (Element) doc.getElementsByTagName("element").item(0);

	         // Change the name of the first element
	         firstElement.setAttribute("orcl_user", user);
	         firstElement.setAttribute("orcl_password", password);

	         // Write the updated document to a new XML file
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml"));
	         transformer.transform(source, result);

	         // Print a message indicating success
	        // System.out.println("XML file updated successfully.");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		
	}
	public static void main(String[] args) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		/*
		
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document document = db.parse(new FileInputStream(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml")));// same xml comments as above.

         XPathFactory xpf = XPathFactory.newInstance();
         XPath xpath = xpf.newXPath();
         Element userElement = (Element) xpath.evaluate("/schema/element[@id='_1']", document,
             XPathConstants.NODE);
         String s = userElement.getAttribute("id");
         System.out.println(s);
         System.out.println(userElement.getAttribute("name"));
		*/

		/*

	      try {
	         // Create a DocumentBuilderFactory object
	         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	         // Use the factory to create a DocumentBuilder object
	         DocumentBuilder builder = factory.newDocumentBuilder();

	         // Parse the XML file into a Document object
	         Document doc = builder.parse(new FileInputStream(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml")));

	         // Get the first element in the document
	         Element firstElement = (Element) doc.getElementsByTagName("element").item(0);

	         // Change the name of the first element
	         firstElement.setAttribute("name", "annan");

	         // Write the updated document to a new XML file
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File("C:\\Users\\UTENTE\\eclipse-workspace\\PFE\\src\\pfe\\xml.xml"));
	         transformer.transform(source, result);

	         // Print a message indicating success
	         System.out.println("XML file updated successfully.");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		*/
		
		//set_element_xml();
		
		for ( String n : get_element_xml_mysql()) {
			System.out.println(n);
			
		}
	}

}
