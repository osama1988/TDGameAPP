package com.tdgame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NearToTowerTest {

	
	Critter critter1 = null;
	Critter critter2 = null;
	Critter[] blackListedCritters = {critter1, critter2};
	NearToTower nt;
	
	@Before
	public void beforeContext() {
		nt = new NearToTower();
		
		critter1 = new Critter(40,      40,       25,   0,    -15,   -60,   12, 80, 8);
		critter2 = new Critter(40,      40,       25,   0,    -15,   -60,   12, 80, 8);
		
		critter1.inGame = true;
		critter2.inGame = true;
		blackListedCritters[0] = critter1;
		blackListedCritters[1] = critter2;
		critter1.distanceOfBlackListedCritter = 5;
		critter2.distanceOfBlackListedCritter = 10;
				
		
	}
	
	/**
	 * Testing slow down special effect in the case when blackListedCritter is near to Tower
	 */
	@Test
	public void slowDownSpecialEffectTrueTest() {
		nt.fire(blackListedCritters, critter1, 5, 5, "Tank");
		assertTrue(blackListedCritters[0].slowdown);
	}
	
	
	/**
	 * Testing slow down special effect in the case when blackListedCritter is not near to Tower
	 */
	@Test
	public void slowDownSpecialEffectFalseTest() {
		nt.fire(blackListedCritters, critter2, 5, 5, "Tank");
		assertFalse(blackListedCritters[1].slowdown);
	}
	
	/**
	 * Testing fire special effect in the case when blackListedCritter is near to Tower
	 */
	@Test
	public void fireSpecialEffectTrueTest() {
		nt.fire(blackListedCritters, critter1, 5, 5, "Fire");
		assertTrue(blackListedCritters[0].showFire);
	}
	
	
	/**
	 * Testing fire special effect in the case when blackListedCritter is not near to Tower
	 */
	@Test
	public void fireSpecialEffectFalseTest() {
		nt.fire(blackListedCritters, critter2, 5, 5, "Fire");
		assertFalse(blackListedCritters[1].showFire);
	}

}
