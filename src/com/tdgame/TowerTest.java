package com.tdgame;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

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
	    
		critter1 = new Critter(40,      40,       25,   0,    -15,   -60,   12, 80);
		critter2 = new Critter(50,      50,       25,   0,    -15,   -60,   12, 50);
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
		
		//sc = new SingleCritters();
		//sc.startWave();
		bomber.range = 5;
		bomber.critterRadius = 5;
		bomber.sqOfDistanceOfCritterFromTower = 10;
		bomber.dradius =2;
		Screen.attackStrategy = 3;
		
		Critter[] critters = {critter1, critter2};
		critter1.inGame = true;
		critter2.inGame = true;
		Critter critterobj = bomber.findTargetCritter(critters, 1, 1);
		//System.out.println("CRITTER ID:: " + critter2.health);
		assertEquals(80, critterobj.health);
	}
	
	/**
	 * Checking for strongest strategy which should return critter with max health
	 */
//	@Test
//	public void nearestToTower() {
//		
//		//sc = new SingleCritters();
//		//sc.startWave();
//		bomber.range = 5;
//		bomber.critterRadius = 5;
//		bomber.sqOfDistanceOfCritterFromTower = 10;
//		bomber.dradius =2;
//		Screen.attackStrategy = 1;
//		
//		Critter[] critters = {critter1, critter2};
//		critter1.inGame = true;
//		critter2.inGame = true;
		//
//		System.out.println("Distance of critter fro tower" + bomber.sqOfDistanceOfCritterFromTower);
		//Critter critterobj = bomber.findTargetCritter(critters, 1, 1);
//		System.out.println("Distance of critter fro tower" + bomber.sqOfDistanceOfCritterFromTower);
//		System.out.println("OSAMA  OSAMA OSAMA \n" + critterobj.health );
		//assertTrue(critterobj.distanceOfBlackListedCritter);
//	}
	
	
	
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
}
