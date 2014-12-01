package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tdgame.Bomber;

public class BomberTest {
	
	Bomber bomber;

	@Before
	public void beforeContext() {
		bomber = new Bomber();
		//bomber.setTowerProperties(1,500,100,2,"Bomber",3,"../res/towers/bomber.png", 15, 1, 2);
		bomber.setTowerProperties(1,500,100,2,"Bomber",3,"../res/towers/bomber.png", 15, 1);
	}
	
	/**
	 * Testing if setTowerProperties is getting appropriate values
	 */
	@Test
	public void towerPropertiesTest() {
		assertEquals(1, bomber.id);
		assertEquals(500, bomber.cost);
		assertEquals(100, bomber.ammunition);
		assertEquals(2, bomber.range);
		assertEquals("Bomber", bomber.type);
		assertEquals(3, bomber.rateOfFire);
		assertEquals("../res/towers/bomber.png", bomber.imgPath);
		assertEquals(15, bomber.damageToCritters);
		assertEquals(1, bomber.level);
	}

	/**
	 * Increasing ammunition by 10 and checking getAmmunition method 
	 */
	@Test
	public void increaseAmmunition() {
		bomber.increaseAmmunition(10); //After increasing ammunition by 10, total amm would be 110
		assertEquals(110, bomber.getAmmunition());
		assertNotEquals(100, bomber.getAmmunition());
	}
	
	/**
	 * Setting position and testing if we are getting the same value
	 */
	@Test
	public void setPosition() {
		bomber.setPosition(10, 15);
		assertEquals(10, bomber.getXPosInTowerMap());
		assertEquals(15, bomber.getYPosInTowerMap());
	}
	
	/**
	 * Comparing Cost by getCost method 
	 */
	@Test
	public void getCost() {
		assertEquals(500, bomber.getCost());
	}
	
	/**
	 * Comparing Range by getRange method 
	 */
	@Test
	public void getRange() {
		assertEquals(2, bomber.getRange());
	}
	
	/**
	 * Comparing Ammunition by getAmmunition method 
	 */
	@Test
	public void getAmmunition() {
		assertEquals(100, bomber.getAmmunition());
	}
	
	/**
	 * Comparing Type by getType method 
	 */
	@Test
	public void getType() {
		assertEquals("Bomber", bomber.getType());
	}
	
	/**
	 * Comparing RefundRate by getRefundRate method 
	 */
	@Test
	public void getRefundRate() {
		assertEquals(125, bomber.getRefundRate());
	}
	
	/**
	 * Comparing Decrease Ammunition by decreaseAmmunition method 
	 */
	@Test
	public void decreaseAmmunition() {
		bomber.decreaseAmmunition(10);
		assertEquals(90, bomber.getAmmunition());
	}
	
	/**
	 * Comparing TowerLevel by getTowerLevel method 
	 */
	@Test
	public void getTowerLevel() {
		assertEquals(1, bomber.getTowerLevel());
	}
	
	/**
	 * Comparing CostToIncreseLevel by getCostToIncreaseLevel method 
	 */
	@Test
	public void getCostToIncreaseLevel() {
		assertEquals(250, bomber.getCostToIncreaseLevel());
	}
	
	/**
	 * Increasing level and testing if is actually increased
	 */
	@Test
	public void increaseLevel() {
		bomber.rateOfFire = 8;
		bomber.range = 4;
		bomber.increaseLevel();
		System.out.println(bomber.getTowerLevel());
		assertEquals(2, bomber.getTowerLevel());
	}
	
	
	
	
	
}
