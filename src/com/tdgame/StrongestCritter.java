package com.tdgame;

public class StrongestCritter implements TowerFireStrategy {

	@Override
	public Critter fire(Critter[] blackListedCritters, Critter targetCritter,
			int towerXPos, int towerYPos) {
		// TODO Auto-generated method stub
		int totalTargetEnemies = 0;
		int indexOfCritterWithMaxHealth = 0;
		int maxHealth = 0;
		for(int i=0; i<blackListedCritters.length; i++){
			if(blackListedCritters[i] != null && blackListedCritters[i].inGame){
				totalTargetEnemies++;
				if(blackListedCritters[i].health > maxHealth){
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
			return blackListedCritters[indexOfCritterWithMaxHealth];
		}
		return null;
	}


}
