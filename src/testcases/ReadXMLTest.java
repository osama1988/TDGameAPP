package testcases;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tdgame.Bomber;
import com.tdgame.Frame;
import com.tdgame.LevelFile;
import com.tdgame.MouseHandler;
import com.tdgame.Player;
import com.tdgame.ReadXML;
import com.tdgame.Screen;
import com.tdgame.Tower;
import com.tdgame.TowerFactory;
import com.tdgame.User;

public class ReadXMLTest {

	Screen screen;
	User user;
	ReadXML readXML;
	Bomber bomber;
	String rows_cols;
	String filename;
	int result;
	int valueOfX;
	int valueOfY;
	String pathForTesting = "/Users/osamayawar/Desktop/eclipse/wordspace/TDGameAPP/level/";
	DocumentBuilderFactory documentFactory;
	DocumentBuilder documentBuilder;
	Document doc;
	
	@Before
	public void beforeContext() throws ParserConfigurationException, SAXException, IOException {
		Frame testFrame = new Frame();
		screen = new Screen(testFrame);
		readXML = new ReadXML(10, 10, screen, "MapWithUserMoney.xml");
		filename = "MapWithUserMoney.xml";
		
		rows_cols = readXML.getLengthOfExistingMap(filename);
		
		valueOfX = Integer.parseInt(rows_cols.split("_")[1]);
		valueOfY = Integer.parseInt(rows_cols.split("_")[0]);
		System.out.println(valueOfX+"\t"+valueOfY);
		LevelFile level_file = new LevelFile(valueOfX, valueOfY);
		level_file.readAndLoadMap(filename, screen, "loadMap");
		
		user = new User(screen);
		
		bomber = new Bomber();
		documentFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentFactory.newDocumentBuilder();
		doc = documentBuilder.parse(pathForTesting + filename);

	}
	
//	/**
//	 * Checking if ReadXML object is not null
//	 * @throws Exception
//	 */
//	@Test
//	public void testReadXML() throws Exception {
//		int valueX = 0;
//		int valueY = 0;
//		ReadXML result = new ReadXML(valueX, valueY, screen, filename);
//		assertNotNull(result);
//	}
//	
//	/**
//	 * Checking if the length of the map is not 0
//	 * @throws Exception
//	 */
//	@Test
//	public void testGetLengthOfExistingMap() throws Exception {
//			ReadXML fixture = new ReadXML(18, 18, new Screen(new Frame()), "");
//			String result = fixture.getLengthOfExistingMap(filename);
//			assertNotEquals("0_0", result);
//		}
	
	/**
	 * Testing state of user in an already saved map
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void stateTestForUsers() throws ParserConfigurationException, SAXException, IOException
	{
		int money = 0;
		
		
		doc.getDocumentElement().normalize();
		NodeList mapList = doc.getElementsByTagName("Map");			
		Node userMoney = mapList.item(0);
		Element moneyElement = (Element) userMoney;
		
		if(moneyElement != null && moneyElement.getAttribute("userMoney") != "")
		{
			money = Integer.parseInt(moneyElement.getAttribute("userMoney"));
		
		}
		readXML.loadXML();
		assertEquals(money, readXML.usermoney);
	}
	
	/**
	 * Testing if the tower state is stored with the same values or not
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void stateTestForTower() throws ParserConfigurationException, SAXException, IOException
	{
		int ammunition = 0;
		int range = 0;
		int rateOfFire = 0;
		int level = 0;
		int x = 0;
		int y = 0;
		int value = 0;

		NodeList nodeList = doc.getElementsByTagName("Row");
		Node tile;
		
		for (int row = 0; row < nodeList.getLength(); row++) {
			Node node = nodeList.item(row);

			NodeList subList = node.getChildNodes();

			for (int cols = 0; cols < 2; cols++) {

				tile = subList.item(cols);

				if (tile.getNodeType() == Node.ELEMENT_NODE) {
				
					Element col = (Element) tile;
					
					x = Integer.parseInt(col.getAttribute("x"));
					y = Integer.parseInt(col.getAttribute("y"));
					value = Integer.parseInt(col.getAttribute("value"));
					
					
					if(col.getAttribute("tower") != null && col.getAttribute("tower") != "" && !col.getAttribute("tower").isEmpty())
					{
						try {
							ammunition=Integer.parseInt(col.getAttribute("ammunition"));
							range = Integer.parseInt(col.getAttribute("range"));
							rateOfFire=Integer.parseInt(col.getAttribute("rateOfFire"));
							level=Integer.parseInt(col.getAttribute("towerLevel"));
							
//							System.out.println(+ ammunition );
							
						} catch (Exception e) {}
					}
				}
			}
		}
		
		
		readXML.loadXML();
		assertEquals(10000, ammunition);
		assertEquals(1, range);
		assertEquals(10, rateOfFire);
		assertEquals(1, level);
	}
}
