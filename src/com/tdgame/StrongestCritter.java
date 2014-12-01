package com.tdgame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * StrongestCritter Strategy for TowerFire
 * @author Team 2
 *
 */
public class StrongestCritter implements TowerFireStrategy {

	@Override
	public Critter fire(Critter[] blackListedCritters, Critter targetCritter,
			int towerXPos, int towerYPos, String type) {
		// TODO Auto-generated method stub
		int totalTargetEnemies = 0;
		int indexOfCritterWithMaxHealth = 0;
		int maxHealth = 0;
		for(int i=0; i<blackListedCritters.length; i++){
			if(blackListedCritters[i] != null && blackListedCritters[i].inGame){
				totalTargetEnemies++;
				if(blackListedCritters[i].health >= maxHealth){
					maxHealth = blackListedCritters[i].health;
					indexOfCritterWithMaxHealth = i;
				}
			}
		}
		System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

		if(totalTargetEnemies > 0){
			System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
			System.out.println("random enemy number\t" + targetCritter);

			blackListedCritters[indexOfCritterWithMaxHealth].towerX=towerXPos;
			blackListedCritters[indexOfCritterWithMaxHealth].towerY=towerYPos;
			blackListedCritters[indexOfCritterWithMaxHealth].towerFixed=true;
			blackListedCritters[indexOfCritterWithMaxHealth].isHit=true;
			if (type.equals("Tank"))
			{	
				blackListedCritters[indexOfCritterWithMaxHealth].slowdown=true;
			}
			else if (type.equals("Fire"))
			{	
				blackListedCritters[indexOfCritterWithMaxHealth].showFire=true;
			}
			else if(type.equals("Laser")){
				blackListedCritters[indexOfCritterWithMaxHealth].splash = true;
			}
			targetCritter = blackListedCritters[indexOfCritterWithMaxHealth];
			return blackListedCritters[indexOfCritterWithMaxHealth];
		}
		return null;
	}


}
