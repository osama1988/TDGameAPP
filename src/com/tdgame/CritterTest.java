package com.tdgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CritterTest {

	/**
	 * Checking if the id for critters is 0 which defines 
	 * the properties of the critters. 
	 */
	
	Critter critter;
	Screen screen;
	ReadXML readXML;
	String rows_cols;
	String filename;
	int result;
	int valueOfX;
	int valueOfY;
	int upward, downward, right, left;
	int row = 0, col = 0;
	boolean hasUpward = false;
	boolean hasDownward = false;
	boolean hasLeft = false;
	boolean hasRight = false;
	
	@Before
	public void setContext() {
		
		Frame testFrame = new Frame();
		screen = new Screen(testFrame);
		readXML = new ReadXML();
		filename = "testfinal.xml";
		rows_cols = readXML.getLengthOfExistingMap(filename);
		
		valueOfX = Integer.parseInt(rows_cols.split("_")[1]);
		valueOfY = Integer.parseInt(rows_cols.split("_")[0]);
		LevelFile level_file = new LevelFile(valueOfX, valueOfY);
		level_file.readAndLoadMap(filename, screen, "loadMap");
		result = 0;
		critter = new Critter();
		critter.moveFrame = 10;
		critter.moveSpeed = 0;
		upward = 0;
		downward = 1;
		right = 2;
		left = 3;
		critter.movement = 0;
	}
	
	
	/**
	 * Check if the creaters entered the map
	 */
	@Test
	public void critterInGame() {
		for (int row = 0; row < Screen.map.length; row++)
			for (int col = 0; col < Screen.map[0].length; col++) {
				if (Screen.map[row][col] == 1) {
					 
					critter.setBounds((int) Screen.width - 15 + (int) Screen.width * row, (int) Screen.height - 45
							+ (int) Screen.height * col, 50, 25);
					critter.createCritter(0);
					assertTrue(critter.inGame);
				}
			}
	}
	
	/**
	 * 
	 * Checking if the direction of the critter is right
	 * then x must increment
	 * 
	 */
	@Test
	public void critterRightDirection() {
		critter.direction = right;
		critter.moveFwd();
		assertEquals(1, critter.x);
		assertEquals(0, critter.y);
		
	}
	
	/**
	 * 
	 * Checking if the direction of the critter is left
	 * then y must decrement
	 * 
	 */
	@Test
	public void critterUpwardDirection() {
		critter.direction = upward;
		critter.moveFwd();
		assertEquals(-1, critter.y);
		assertEquals(0, critter.x);
		
	}
	
	/**
	 * 
	 * Checking if the direction of the critter is downward
	 * then y must increment
	 * 
	 */
	@Test
	public void critterDownwardDirection() {
		critter.direction = downward;
		critter.moveFwd();
		assertEquals(1, critter.y);
		assertEquals(0, critter.x);
		
	}
	
	/**
	 * 
	 * Checking if the direction of the critter is left
	 * then x must decrement
	 * 
	 */
	@Test
	public void critterLeftDirection() {
		critter.direction = left;
		critter.moveFwd();
		assertEquals(-1, critter.x);
		assertEquals(0, critter.y);
		
	}
	
	/**
	 * 
	 * Check if the critters are following the path properly,
	 * and if the directions are followed according to the path
	 * 
	 */
	@Test
	public void physics() {
		
	}
	
	
	/**
	 * Validating critter ID that defines its properties e.g. Image
	 */
	@Test
	public void crittersIDCheck() {
		int expected_critterID = 0;
		assertEquals(expected_critterID, critter.critterID);
	}
	

}
