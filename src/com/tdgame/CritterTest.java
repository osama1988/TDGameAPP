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
		filename = "testcase.xml";
		rows_cols = readXML.getLengthOfExistingMap(filename);
		
		valueOfX = Integer.parseInt(rows_cols.split("_")[1]);
		valueOfY = Integer.parseInt(rows_cols.split("_")[0]);
		LevelFile level_file = new LevelFile(valueOfX, valueOfY);
		level_file.readAndLoadMap(filename, screen, "loadMap");
		result = 0;
		critter = new Critter(col, col, col, col, col, col, col);
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
	 * Checking direction - Setting conditions true for Right direction
	 * Rest directions should be false.
	 * 
	 */
	@Test
	public void rightDirection() {
		//boolean direction;
		Screen.map[row + 1][col] = 10;
		critter.physics(520, 2, 49);
		assertTrue(critter.hasRight);
		assertFalse(critter.hasLeft);
		assertFalse(critter.hasUpward);
		assertFalse(critter.hasDownward);
	}
	
	/**
	 * 
	 * Checking direction - Setting conditions true for Left direction
	 * Rest directions should be false.
	 * 
	 */
	@Test
	public void leftDirection() {
		//boolean direction;
		critter.row = 1;
		critter.col = 1;
				
		row = 2;
		//col = 5;
		//col= 50;
		critter.physics(520, 2, 49);
		assertTrue(critter.hasLeft);
		assertFalse(critter.hasRight);
		assertFalse(critter.hasUpward);
		assertFalse(critter.hasDownward);
	}
	
	/**
	 * 
	 * Checking direction - Setting conditions true for Upward direction
	 * Rest directions should be false.
	 * 
	 */
	@Test
	public void UpwardDirection() {
		critter.col = 2;
		critter.physics(520, 2, 49);
		assertTrue(critter.hasUpward);
		assertFalse(critter.hasLeft);
		assertFalse(critter.hasRight);
		assertFalse(critter.hasDownward);
	}
	
	/**
	 * 
	 * Checking direction - Setting conditions true for Downwards direction
	 * Rest directions should be false.
	 * 
	 */
	@Test
	public void DownwardDirection() {
		critter.physics(520, 2, 49);
		assertFalse(critter.hasUpward);
		assertFalse(critter.hasLeft);
		assertFalse(critter.hasRight);
		assertTrue(critter.hasDownward);
	}
	
	/**
	 * 
	 * Testing if the Critter object is taking the exact values
	 * 
	 */
	@Test
	public void critterContructor() {
		Critter critter = new Critter(50,50,25,0,-15,-60,12);
		assertEquals(50, critter.imageHeight);
		assertEquals(50, critter.imageWidth);
		assertEquals(25, critter.adjustX);
		assertEquals(0, critter.adjustY);
		assertEquals(-15, critter.rectangleX);
		assertEquals(-60, critter.rectangleY);
		assertEquals(12, critter.healthBarSpace);
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
