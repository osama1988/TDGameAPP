package com.tdgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TowerTest {
	
	Bomber bomber;
	
	@Before
	public void beforeContext() { 
	    bomber = new Bomber();
		
	}
	
	/**
	 * Testing health before attack is made and after attack is made
	 */
	@Test
	public void comparingHealth() {
		Critter critter = new Critter();
		int orig_health = critter.health;
		bomber.towerAttack(5, 5, critter);
		assertNotSame(critter.health, orig_health);
		
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
}
