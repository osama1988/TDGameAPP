package com.tdgame;

public class WeakestCritter implements TowerFireStrategy {

	
	@Override
	public Critter fire(Critter[] blackListedCritters, Critter targetCritter,
			int towerXPos, int towerYPos, String type) {
		// TODO Auto-generated method stub
			int totalTargetEnemies = 0;
			int indexOfCritterWithMinHealth = 0;
			int minHealth = 999;
			
			for(int i=0; i<blackListedCritters.length; i++){
				if(blackListedCritters[i] != null && blackListedCritters[i].inGame){
					totalTargetEnemies++;
					if(blackListedCritters[i].health <= minHealth){
						minHealth = blackListedCritters[i].health;
						indexOfCritterWithMinHealth = i;
					}
				}
			}
			System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

			if(totalTargetEnemies > 0){
				System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
				System.out.println("random enemy number\t" + targetCritter);

				blackListedCritters[indexOfCritterWithMinHealth].towerX=towerXPos;
				blackListedCritters[indexOfCritterWithMinHealth].towerY=towerYPos;
				blackListedCritters[indexOfCritterWithMinHealth].towerFixed=true;
				if (type.equals("Tank"))
				{	
					blackListedCritters[indexOfCritterWithMinHealth].slowdown=true;
				}
				if (type.equals("Fire"))
				{	
					blackListedCritters[indexOfCritterWithMinHealth].showFire=true;
				}
				return blackListedCritters[indexOfCritterWithMinHealth];
			}
			return null;
	}

	
	
}
