package com.my.objectRepository;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ankit.DataMaps.CommonDataMaps;
import com.ankit.JavaUtility.MachineSearch;


public class ReadObjectRepository {
	
//	public String xmlPath = "";
//	
//	
//	
//	public ReadObjectRepository(String xml){
//		xmlPath = xml;
//	}
	
	
	/**
	 * Read all the values from the given xml file and saved into HashMap
	 * @author Ankit
	 * @param xmlFileName
	 *                 Name of the file
	 * @param dir
	 *         Directory where the xml file name can be found
	 */
	public void getObjectRepository(String dir, String xmlFileName){
		
		try{
			String xml = new MachineSearch().serachMachineForFile(dir, xmlFileName);
			File fXmlFile = new File(xml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList pList = doc.getDocumentElement().getChildNodes();

//			System.out.println("----------------------------");
//			System.out.println("Total number of Pages are: "+pList.getLength());
			//		System.out.println("Name of first element :"+doc.);

			for (int pages = 0; pages < pList.getLength(); pages++) {
				NodeList pEList = (NodeList) pList.item(pages);
				if (pList.item(pages).getNodeType()== 1){
//					System.out.println();
//					System.out.println();
//					System.out.println("**********"+pList.item(pages).getNodeName()+"***********");
					String pageName = pList.item(pages).getNodeName();
					//  System.out.println("PageName is------ "+pList.item(pages).getNodeName());
					//  System.out.println("Total number of Elements are: "+pEList.getLength());
//					System.out.println();
					HashMap<String, HashMap<String, String>> pageElementMap = new HashMap<String, HashMap<String, String>>();
					for (int pageElements = 0; pageElements < pEList.getLength(); pageElements++){
						NodeList eList = (NodeList) pEList.item(pageElements);
						if (pEList.item(pageElements).getNodeType()== 1){
//							System.out.println(pEList.item(pageElements).getNodeName());
							String pageElementName = pEList.item(pageElements).getNodeName();
							//	  System.out.println("Total number of Elements are: "+eList.getLength());
							//	  System.out.println();
							HashMap<String, String> elementMap = new HashMap<String, String>();
							for (int identifier = 0; identifier < eList.getLength(); identifier++){
								//	NodeList eIdentifierList = (NodeList) eList.item(identifier);

								if (eList.item(identifier).getNodeType()== 1){
									//	  System.out.println("get Node name is: "+eList.item(identifier).getNodeName()+" Get);
									Node iIdentifierNode = eList.item(identifier);
									//	  System.out.println("\nCurrent Element :" + iIdentifierNode.getNodeName());
									if (iIdentifierNode.getNodeType() == Node.ELEMENT_NODE) {
										Element eElement = (Element) iIdentifierNode;
										//     System.out.println("eElement.getLocalName() "+eElement.getLocalName());
										//     System.out.println("eElement.getNodeValue() "+eElement.getNodeValue());
//										System.out.println(":"+ eElement.getTagName());
										String tagName =eElement.getTagName();
//										System.out.println(eElement.getTextContent());
										String textContent = eElement.getTextContent();
										elementMap.put(tagName, textContent);
									}
								}
							}
//							System.out.println();
							pageElementMap.put(pageElementName, elementMap);
						}
					}
					CommonDataMaps.objectRepoMapValues.put(pageName, pageElementMap);
				}
			}
			//System.out.println("\nCurrent Element :" + nNode.getNodeName());
			//			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			//
			//				Element eElement = (Element) nNode;
			//
			//				System.out.println("Staff id : " + eElement.getAttribute("id"));
			//				System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
			//				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			//				System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
			//				System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
			//			}
//			System.out.println("HashMap "+CommonDataMaps.objectRepoMapValues);
		}catch(Exception e){
			System.out.println("Object repository :"+xmlFileName+" not found and "+e.getMessage());
		}
	}
	
	
	
	
	
	

}
