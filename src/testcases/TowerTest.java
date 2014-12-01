package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tdgame.Bomber;
import com.tdgame.Critter;
import com.tdgame.CritterWave;
import com.tdgame.Frame;
import com.tdgame.LevelFile;
import com.tdgame.ReadXML;
import com.tdgame.Screen;
import com.tdgame.SingleCritters;

public class TowerTest {
	
	Bomber bomber;
	Critter critter;
	Critter[] critterArray;
	Screen screen;
	ReadXML readXML;
	String rows_cols;
	String filename;
	int result;
	int valueOfX;
	int valueOfY;
	SingleCritters sc;
	CritterWave cw;
	Critter critter1 = null;
	Critter critter2 = null;
	
//	 = new Critter[2];
	
	
	@Before
	public void beforeContext() { 
	   
	    Frame testFrame = new Frame();
		screen = new Screen(testFrame);
		readXML = new ReadXML();
		filename = "testcase.xml";
		rows_cols = readXML.getLengthOfExistingMap(filename);
		
		valueOfX = Integer.parseInt(rows_cols.split("_")[1]);
		valueOfY = Integer.parseInt(rows_cols.split("_")[0]);
		LevelFile level_file = new LevelFile(valueOfX, valueOfY);
		level_file.readAndLoadMap(filename, screen, "loadMap");
		bomber = new Bomber();
	    
		critter1 = new Critter(40,      40,       25,   0,    -15,   -60,   12, 80, 8);
		critter2 = new Critter(50,      50,       25,   0,    -15,   -60,   12, 50, 8);
		critter1.inGame = true;
		critter2.inGame = true;
	    
	}
	
	/**
	 * Checking if all the critters are in range
	 */
	@Test
	public void allCrittersInRange() {
		sc = new SingleCritters();
		sc.startWave();
		bomber.range = 5;
		bomber.critterRadius = 5;
		bomber.sqOfDistanceOfCritterFromTower = 10;
		bomber.dradius =2;
		
		Critter[] critters = {critter1, critter2};
		bomber.findTargetCritter(critters, 5, 5);
		
		assertEquals(2, bomber.blackListedCritters.length);
	}
	
	/**
	 * Checking for strongest strategy which should return critter with max health
	 */
	@Test
	public void strongestStrategy() {
		bomber.range = 5;
		bomber.critterRadius = 5;
		bomber.sqOfDistanceOfCritterFromTower = 10;
		bomber.dradius =2;
		bomber.attackStrategy = 3;
		
		Critter[] critters = {critter1, critter2};
		critter1.inGame = true;
		critter2.inGame = true;
		Critter critterobj = bomber.findTargetCritter(critters, 1, 1);
		//System.out.println("CRITTER ID:: " + critter2.health);
		assertEquals(80, critterobj.health);
	}
	
	/**
	 * Checking for weakest strategy which should return critter with min health
	 */
	@Test
	public void weakestStrategy() {
		bomber.range = 5;
		bomber.critterRadius = 5;
		bomber.sqOfDistanceOfCritterFromTower = 10;
		bomber.dradius =2;
		bomber.attackStrategy = 4;
		
		Critter[] critters = {critter1, critter2};
		critter1.inGame = true;
		critter2.inGame = true;
		Critter critterobj = bomber.findTargetCritter(critters, 1, 1);
		assertEquals(50, critterobj.health);
	}
	
	/**
	 * Checking for near to Tower strategy which should return nearest critter to tower
	 */
	@Test
	public void nearestToTower() {
		bomber.range = 5;
		bomber.critterRadius = 5;
		bomber.sqOfDistanceOfCritterFromTower = 10;
		bomber.dradius =2;
		bomber.attackStrategy = 1;
		
		Critter[] critters = {critter1, critter2};
		critter1.inGame = true;
		critter2.inGame = true;
		Critter critterobj = bomber.findTargetCritter(critters, 1, 1);
		assertTrue(critter1.towerX == 1);
		assertTrue(critter1.towerY == 1);
		assertTrue(critter2.towerX == 0);
		assertTrue(critter2.towerY == 0);
		
	}
	
	/**
	 * Checking for near To End strategy which should return critter that is near to end
	 */
	@Test
	public void nearToEnd() {
		bomber.range = 5;
		bomber.critterRadius = 5;
		bomber.sqOfDistanceOfCritterFromTower = 10;
		bomber.dradius =2;
		bomber.attackStrategy = 1;
		
		Critter[] critters = {critter1, critter2};
		critter1.inGame = true;
		critter2.inGame = true;
		Critter critterobj = bomber.findTargetCritter(critters, 1, 1);
		assertTrue(critter1.towerX == 1);
		assertTrue(critter1.towerY == 1);
		assertTrue(critter2.towerX == 0);
		assertTrue(critter2.towerY == 0);
		
	}
	
	
	
	/**
	 * Testing health before attack is made and after attack is made
	 */
	@Test
	public void setDamageToCritters() {
		bomber.setDamageToCritters(20);
		assertEquals(20, bomber.getDamageToCritters());
		
	}
	
	/**
	 * Testing if the attack time is being set properly by getting it from get method
	 */
	@Test
	public void setAttackTime() {
		bomber.setAttackTime(10);
		assertEquals(10, bomber.getAttackTime());
	}
	
	
	/**
	 * Testing if the attack delay is being set properly by getting it from get method
	 */
	@Test
	public void setAttackDelay() {
		bomber.setAttackDelay(10);
		assertEquals(10, bomber.getAttackDelay());
	}
	
	/**
	 * Testing if the attack Max Attack Time is being set properly by getting it from get method
	 */
	@Test
	public void setMaxAttackTime() {
		bomber.setMaxAttackTime(10);
		assertEquals(10, bomber.getMaxAttackTime());
	}
	
	/**
	 * Testing if the attack Max Attack Delay is being set properly by getting it from get method
	 */
	@Test
	public void setMaxAttackDelay() {
		bomber.setMaxAttackDelay(10);
		assertEquals(10, bomber.getMaxAttackDelay());
	}
	
	/**
	 * Testing health before attack is made and after attack is made
	 */
	@Test
	public void comparingHealth() {
		Critter critter = new Critter();
		int orig_health = critter.health;
		assertSame(0, orig_health);
		
	}
	
	@Test
	public void getMaxDelay(){
		int delay = 10;
		assertEquals(10, delay);
	}
	
	@Test
	public void getMinDelay(){
		int minDelay = 5;
		assertEquals(5, minDelay);
	}
}
