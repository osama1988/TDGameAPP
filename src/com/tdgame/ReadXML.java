package com.tdgame;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class contains the code to parse an XML file and load it into the 2D array
 * 
 * @author Team 2
 * @version $revision
 *
 */

public class ReadXML {

	Screen screen;
	int valueX, valueY;
	String fileName="";
	public int usermoney=0;

	
	//String pathForTesting="D:/Java/TDGameAPP/level/";
//	String pathForTesting = "/Users/osamayawar/Desktop/eclipse/wordspace/TDGameAPP/level/"; //Added for MAC by Osama
	String pathForTesting="../level/";
	public ReadXML(){}
		
	public ReadXML(int valueX, int valueY, Screen screen, String fileName) {
		this.valueX = valueX;
		this.valueY = valueY;
		this.screen = screen;
		this.fileName = fileName;
	}
	
	// this method gets the length of existing map in order to load it from the Base.XML file
		public String getLengthOfExistingMap(String newFileName) {
			
			int numberOfCols = 0;
			int numberOfRows = 0;
			
			try {
				File xmlFile = new File(pathForTesting + newFileName); //specifying the path to read the files 
				DocumentBuilderFactory documentFactory = DocumentBuilderFactory
						.newInstance(); 
				DocumentBuilder documentBuilder = documentFactory
						.newDocumentBuilder();
				Document doc = documentBuilder.parse(xmlFile);

				doc.getDocumentElement().normalize();
				NodeList nodeList = doc.getElementsByTagName("Row");
				
				//reading the file for getting the number of Cols and Rows
				for (int row = 0; row < nodeList.getLength(); row++) {
					Node node = nodeList.item(row);

					NodeList subList = node.getChildNodes();
					
					numberOfCols = subList.getLength();
					break;
				}
				
				numberOfRows = nodeList.getLength(); 
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return numberOfRows+ "_" +numberOfCols;
			
		}
		
	// this methods reads the Base.XML file when the user wants to create a new map
	public void readXML() {
		Node tile;
		int x, y, value;

		try {

			File xmlFile = new File(pathForTesting + fileName); //reading Base XML
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentFactory
					.newDocumentBuilder();
			Document doc = documentBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize(); 
			NodeList nodeList = doc.getElementsByTagName("Row");

			//read the file for getting the x and y coordinates of the base xml file
			for (int row = 0; row < valueY; row++) {
				Node node = nodeList.item(row);

				NodeList subList = node.getChildNodes();

				for (int cols = 0; cols < valueX * 2; cols++) {

					tile = subList.item(cols);

					if (tile.getNodeType() == Node.ELEMENT_NODE) {
					
						Element col = (Element) tile;
						
						x = Integer.parseInt(col.getAttribute("x"));
						y = Integer.parseInt(col.getAttribute("y"));
						value = Integer.parseInt(col.getAttribute("value"));
												
						screen.map[y][row] = value;
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this method loads an existing XML file into the 2D array
	public void loadXML() {
		Node tile;
		int x, y, value, position;
		Tower towerObjAfterLoading; 
		MouseHandler.hashMap_of_pathIndex_with_position = new TreeMap<Integer, String>(); 
		Element moneyElement = null;
		try {

			File xmlFile = new File(pathForTesting+ fileName);
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentFactory
					.newDocumentBuilder();
			Document doc = documentBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();
			NodeList mapList = doc.getElementsByTagName("Map");			
			Node userMoney = mapList.item(0);
			 moneyElement = (Element) userMoney;
			
			if(moneyElement != null && moneyElement.getAttribute("userMoney") != "")
			{
				try {
					Screen.user.player.money = Integer.parseInt(moneyElement.getAttribute("userMoney"));
					
					
				} catch (Exception e) {
					usermoney = Integer.parseInt(moneyElement.getAttribute("userMoney"));
				}
				
			}
			
			NodeList nodeList = doc.getElementsByTagName("Row");

			for (int row = 0; row < nodeList.getLength(); row++) {
				Node node = nodeList.item(row);

				NodeList subList = node.getChildNodes();

				for (int cols = 0; cols < subList.getLength(); cols++) {

					tile = subList.item(cols);

					if (tile.getNodeType() == Node.ELEMENT_NODE) {
					
						Element col = (Element) tile;
						
						x = Integer.parseInt(col.getAttribute("x"));
						y = Integer.parseInt(col.getAttribute("y"));
						value = Integer.parseInt(col.getAttribute("value"));
						position = Integer.parseInt(col.getAttribute("position"));
						if(MouseHandler.largestPositionValue_inMap < position) {
							MouseHandler.largestPositionValue_inMap = position;
						}
						
						if(col.getAttribute("tower") != null && col.getAttribute("tower") != "" && !col.getAttribute("tower").isEmpty()) {
							
							try {
								Screen.towerMap[y][row]=TowerFactory.getTower(col.getAttribute("tower"));
								System.out.println(Screen.towerMap[y][row]);
								
								System.out.println(col.getAttribute("tower"));
								towerObjAfterLoading = Screen.towerMap[y][row];		
								
								towerObjAfterLoading.ammunition=Integer.parseInt(col.getAttribute("ammunition"));
								System.out.println(towerObjAfterLoading.ammunition + "OSAMA");
//							towerObjAfterLoading.actualAmmunition=Integer.parseInt(col.getAttribute("ammunition"));
//							towerObjAfterLoading.cost = Integer.parseInt(col.getAttribute("cost"));
								towerObjAfterLoading.range = Integer.parseInt(col.getAttribute("range"));
//							towerObjAfterLoading.type=col.getAttribute("tower");
//							towerObjAfterLoading.refundRate=Integer.parseInt(col.getAttribute("refundRate"));
								towerObjAfterLoading.rateOfFire=Integer.parseInt(col.getAttribute("rateOfFire"));
//							towerObjAfterLoading.costToAddAmmunition=Integer.parseInt(col.getAttribute("costToAddAmmunition"));
								towerObjAfterLoading.level=Integer.parseInt(col.getAttribute("towerLevel"));
								towerObjAfterLoading.setTowerStrategy(col.getAttribute("towerStrategy"));
							} catch (Exception e) {
							}
							
						}		
						
												
						Screen.map[y][row] = value;
						
						//setting 1 for the start point
						if(value == 1) { 
							MouseHandler.startPoint_while_loadingMap = y+"_"+row;
						}
						//setting 2 for the end point
						else if(value == 2) {
							MouseHandler.endPoint_while_loadingMap = y+"_"+row;
						}
						//setting 3 for the path
						else if(value == 3) {							
							MouseHandler.hashMap_of_pathIndex_with_position.put(position, y+"_"+row);
							MouseHandler.boxPositionPathNumberMap.put(y+"_"+row, position);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			usermoney=Integer.parseInt(moneyElement.getAttribute("userMoney"));
		}
	}

	public void readLog(String log,String eleType,JTable table){
		try
		{
			String elementType;
			String logType;
			File xmlFile = new File(pathForTesting + "log.xml"); //reading Base XML
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentFactory
					.newDocumentBuilder();
			Document doc = documentBuilder.parse(xmlFile);
	
			doc.getDocumentElement().normalize(); 
			NodeList nodeList = doc.getElementsByTagName("Entry");
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
		 
				Node nNode = nodeList.item(temp);
		 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					elementType=eElement.getAttribute("ElementType");
					logType=eElement.getAttribute("LogType");
					if(log.equals("Tower"))
					{
						if(logType.equals("Tower") || logType.equals("Wave_Tower")){
							if(elementType.equals(eleType)){
								model.addRow(new Object[]{eElement.getAttribute("TimeStamp"),logType, eElement.getAttribute("ElementType"), eElement.getAttribute("Msg")});
								System.out.println(eElement.getAttribute("TimeStamp")+" "+eElement.getAttribute("ElementType")+" "+eElement.getAttribute("Msg"));
							}
							else if(eleType.equals("All")){
								//if you want collective log of towers
								model.addRow(new Object[]{eElement.getAttribute("TimeStamp"),logType, eElement.getAttribute("ElementType"), eElement.getAttribute("Msg")});
								System.out.println(eElement.getAttribute("TimeStamp")+" "+eElement.getAttribute("ElementType")+" "+eElement.getAttribute("Msg"));
							}
						}
					}
					else if(log.equals("Wave")){
						if(logType.equals("Wave") || logType.equals("Wave_Tower") ||  logType.equals("Wave_User")){
							model.addRow(new Object[]{eElement.getAttribute("TimeStamp"),logType, eElement.getAttribute("ElementType"), eElement.getAttribute("Msg")});
							System.out.println(eElement.getAttribute("TimeStamp")+" "+eElement.getAttribute("ElementType")+" "+eElement.getAttribute("Msg"));
							
						}
					}
					else if(log.equals("User")){
						if(logType.equals("User") || logType.equals("Wave_User")){
							model.addRow(new Object[]{eElement.getAttribute("TimeStamp"),logType, eElement.getAttribute("ElementType"), eElement.getAttribute("Msg")});
							System.out.println(eElement.getAttribute("TimeStamp")+" "+eElement.getAttribute("ElementType")+" "+eElement.getAttribute("Msg"));
							
						}
					}
					else if(log.equals("Global")){
						model.addRow(new Object[]{eElement.getAttribute("TimeStamp"),logType, eElement.getAttribute("ElementType"), eElement.getAttribute("Msg")});
						System.out.println(eElement.getAttribute("TimeStamp")+" "+eElement.getAttribute("LogType")+" "+eElement.getAttribute("ElementType")+" "+eElement.getAttribute("Msg"));
					}
						
		 
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


}
