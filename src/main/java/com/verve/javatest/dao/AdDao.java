package com.verve.javatest.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.verve.javatest.entity.Tags;

public class AdDao {
	
	private static AdDao adDaoInstance = null;
	
	private AdDao() {
		
	}
	
	/**
	 * Singleton instance of a class
	 * @return
	 */
	public static AdDao instance() {
		if(adDaoInstance == null) {
			adDaoInstance = new AdDao();
		}
		
		return adDaoInstance;
	}
	
	/**
	 * Get Ad in XML format using information from tags object
	 * @param tags
	 * @return
	 */
	public String getAdByTags(Tags tags) {
		// Get an instance of a document factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ClassLoader classLoader = getClass().getClassLoader();
		// Get an Ad XML template file without tags information
		InputStream inputStream = classLoader.getResourceAsStream("AdTemplate.xml");
		
		try {
			// Get document builder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Parse XML file template in document
			Document document = builder.parse(inputStream);
			
			// Update document with tags information
			
			NodeList adNodes = document.getElementsByTagName("Ad");
			
			for(int i=0; i<adNodes.getLength(); i++) {
				Element AdElement = (Element) adNodes.item(i);
				AdElement.setAttribute("id", tags.id + "");
			}
			
			NodeList adTitleNodes = document.getElementsByTagName("AdTitle");
			
			for(int i=0; i<adTitleNodes.getLength(); i++) {
				Element AdElement = (Element) adTitleNodes.item(i);
				AdElement.setTextContent(tags.name);
			}
			
			NodeList MediaFile = document.getElementsByTagName("MediaFile");
			
			for(int i=0; i<MediaFile.getLength(); i++) {
				Element AdElement = (Element) MediaFile.item(i);
				AdElement.setTextContent(tags.tagUrl);
			}
			
			// Create an instance of String writer
			StringWriter stringWriter = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        
	        // Transform and return XML document in string format
	        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
	        return stringWriter.toString();
		
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tags.name + " ad is being served in xml format";
	}
}
