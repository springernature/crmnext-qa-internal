package com.sn.dataproviders;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXML {

	String testCName = "ChangeContractTypeAndValidate";
	
	/***
	 * @author mgp0966
	 * @info reads test data from XML file
	 * @return object array
	 */
	@DataProvider(name = "BPTestData")
	//@Test
	public Object[][] readXMLData()
	//public void readXMLData()

	{
		String tcName = this.testCName;
		Object[][] testData = null;
		int testIterations=0;
		int iCol = 0;
	  try {
		  	String xmlPath = System.getProperty("user.dir")+"\\TestData\\TestData.xml";
	         File inputFile = new File(xmlPath);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         
	         NodeList nList = doc.getElementsByTagName("TC");
	         System.out.println("----------------------------");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) 
	         {
	            Node nNode = nList.item(temp);
	            System.out.println("\nCurrent Element :" + nNode.getNodeName());
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	            {
	               Element eElement = (Element) nNode;
	               System.out.println("Mark: " + eElement.getAttribute("Mark"));
	               System.out.println("TCName: " + eElement.getAttribute("TCName"));
	               if(eElement.getAttribute("TCName").equalsIgnoreCase(tcName.toLowerCase()))
	               {             
	            	   NodeList tcDataList = eElement.getElementsByTagName("TestData");
	            	   for(int testItr = 0; testItr<tcDataList.getLength();testItr++)
	            	   {
		            	   Element dataElement = (Element) tcDataList.item(testItr);
		            	   
		            	   if(dataElement.getAttribute("Mark").equalsIgnoreCase("true"))   
			               {
		            		   NodeList dataNode = dataElement.getChildNodes(); 
		            		   iCol = 0;
			            	   for(int idata = 0; idata<dataNode.getLength();idata++ )
			            	   {
			            		   
			            		   if(dataNode.item(idata).getNodeType()==Node.ELEMENT_NODE)
			            		   {
			            			  // testData[testIterations][iCol] = dataNode.item(idata).getTextContent();
			            			   //arr.add(iCol, dataNode.item(idata).getTextContent());
			            			   System.out.println(dataNode.item(idata).getNodeName());
				            		   System.out.println(dataNode.item(idata).getTextContent());
				            		   iCol++;
			            		   }
			            		  
			            	   }
			            	   testIterations++;
			               }
		            	   
	            	   }
	            	    
            		   testData = new Object[testIterations][iCol];
            		   testIterations=0;
            		   for(int testItr = 0; testItr<tcDataList.getLength();testItr++)
	            	   {
		            	   Element dataElement = (Element) tcDataList.item(testItr);
		            	   
		            	   if(dataElement.getAttribute("Mark").equalsIgnoreCase("true"))   
			               {
		            		   NodeList dataNode = dataElement.getChildNodes(); 
		            		   iCol = 0;
			            	   for(int idata = 0; idata<dataNode.getLength();idata++ )
			            	   {
			            		   
			            		   if(dataNode.item(idata).getNodeType()==Node.ELEMENT_NODE)
			            		   {
			            			  testData[testIterations][iCol] = dataNode.item(idata).getTextContent();
				            		  iCol++;
			            		   }
			            		  
			            	   }
			               }
		            	   testIterations++;
	            	   }
	            	   
	               }
	               
	            }
	            
	        }
	      } catch (Exception e) {
	         e.printStackTrace();
	         return testData;
	      }
	  System.out.println(testData.toString());
	  return testData;
	}
	
}
