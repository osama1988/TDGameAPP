package com.tdgame;

import java.awt.Graphics;

/**
 * This class is created to implement strategy pattern
 * @author Team 2
 *
 */
interface TowerFireStrategy {
	Critter fire(Critter[] blackListedCritters, Critter targetCritter,int towerXPos, int towerYPos);
}
