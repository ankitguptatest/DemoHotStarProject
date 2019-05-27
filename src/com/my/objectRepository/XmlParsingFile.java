package com.my.objectRepository;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlParsingFile {

	public List<String> getUrls(){
		List<String> ls = new ArrayList<String>();
		try {
			URL url = new URL("https://birdeye.com/sitemap.xml");
			URLConnection conn = url.openConnection();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(conn.getInputStream());
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList pList = doc.getDocumentElement().getChildNodes();
			System.out.println("----------------------------");
			System.out.println("Total number of Pages are: "+pList.getLength());
			for (int pages = 0; pages < pList.getLength(); pages++) {
//			for (int pages = 0; pages < 2; pages++) {
				NodeList pEList = (NodeList) pList.item(pages);	
				if (pList.item(pages).getNodeType()== 1){
//					System.out.println();
//					System.out.println();
//					System.out.println("**********"+pList.item(pages).getNodeName()+"***********");
					String pageName = pList.item(pages).getNodeName();
//					System.out.println("PageName is------ "+pList.item(pages).getNodeName());
//					System.out.println("Total number of Elements are: "+pEList.getLength());
					System.out.println();
					HashMap<String, HashMap<String, String>> pageElementMap = new HashMap<String, HashMap<String, String>>();
					for (int pageElements = 0; pageElements < pEList.getLength(); pageElements++){
						if (pEList.item(pageElements).getNodeType()== 1){
//							System.out.println(pEList.item(pageElements).getNodeName());
							String pageElementName = pEList.item(pageElements).getNodeName();
							Node iIdentifierNode = pEList.item(pageElements);
//							System.out.println("\nCurrent Element :" + iIdentifierNode.getNodeName());
							if (iIdentifierNode.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) iIdentifierNode;
//								System.out.println(":"+ eElement.getTagName());
								String tagName =eElement.getTagName();
//								System.out.println(eElement.getTextContent());
								String textContent = eElement.getTextContent();
//								System.out.println(pages);
								if (tagName.equalsIgnoreCase("loc")){
									ls.add(textContent);
									break;
								}
							}
						}
						System.out.println();
					}
//					if(ls.get(ls.size()-1).contains("careers")){
//						ls.remove(ls.size()-1);
//					break;	
//					}
				}
			}
			System.out.println();
			System.out.println(ls);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}catch(Exception e1){
			e1.printStackTrace();
		}

         return ls;
	}

}
