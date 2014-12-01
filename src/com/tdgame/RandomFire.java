package com.tdgame;

import java.util.Random;

public class RandomFire implements TowerFireStrategy {

	@Override
	public Critter fire(Critter[] blackListedCritters,
			Critter targetCritter, int towerXPos, int towerYPos, String type) {
		// TODO Auto-generated method stub
		int totalTargetEnemies = 0;

		for(int i=0; i<blackListedCritters.length; i++){
			if(blackListedCritters[i] != null){
				totalTargetEnemies++;
			}
		}
		System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

		if(totalTargetEnemies > 0){
			System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
			int targetedCritter = new Random().nextInt(totalTargetEnemies);
			System.out.println("random enemy number\t" + targetCritter);
			int crittersKilled = 0;
			int noOfCritterssChecked = 0;

			while(true){
				System.out.println("killed\t" + crittersKilled + "\nchecked\t" + noOfCritterssChecked);
				if(crittersKilled == targetedCritter && blackListedCritters[noOfCritterssChecked] != null && blackListedCritters[noOfCritterssChecked].inGame){
					System.out.println("enemiesKilled == enemy && enemiesInRange[noOfEnemiesChecked] != null");
					System.out.println("returning this random enemy to be killed...");
					blackListedCritters[noOfCritterssChecked].towerX=towerXPos;
					blackListedCritters[noOfCritterssChecked].towerY=towerYPos;
					blackListedCritters[noOfCritterssChecked].towerFixed=true;
					blackListedCritters[noOfCritterssChecked].isHit=true;
					if (type.equals("Tank"))
					{	
						blackListedCritters[noOfCritterssChecked].slowdown=true;
						blackListedCritters[noOfCritterssChecked].showFire = false;
						blackListedCritters[noOfCritterssChecked].splash = false;
					}
					else if (type.equals("Fire"))
					{	
						blackListedCritters[noOfCritterssChecked].showFire=true;
						blackListedCritters[noOfCritterssChecked].slowdown = false;
						blackListedCritters[noOfCritterssChecked].splash = false;
					}
					else if(type.equals("Laser")){
						blackListedCritters[noOfCritterssChecked].splash = true;
						blackListedCritters[noOfCritterssChecked].showFire = false;
						blackListedCritters[noOfCritterssChecked].slowdown = false;
					}
					targetCritter = blackListedCritters[noOfCritterssChecked];
					return blackListedCritters[noOfCritterssChecked];
				}
				if(targetedCritter > crittersKilled){
					if(blackListedCritters != null){
						crittersKilled++;
						System.out.println("crittersInRange != null\tkilled\t" + crittersKilled);
					}
					noOfCritterssChecked++;
					System.out.println("inc... checked\t" + noOfCritterssChecked);
				} else{
					return null;
				}
			}
		}
		return null;
	}

}
