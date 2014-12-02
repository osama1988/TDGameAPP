package com.tdgame;

/**
 * WeakestCritter strategy for TowerFire
 * @author Team2
 *
 */
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
				blackListedCritters[indexOfCritterWithMinHealth].setAttackTime(0);
				blackListedCritters[indexOfCritterWithMinHealth].towerX=towerXPos;
				blackListedCritters[indexOfCritterWithMinHealth].towerY=towerYPos;
				blackListedCritters[indexOfCritterWithMinHealth].towerFixed=true;
				blackListedCritters[indexOfCritterWithMinHealth].isHit=true;
				if (type.equals("Tank"))
				{	
					blackListedCritters[indexOfCritterWithMinHealth].slowdown=true;
					blackListedCritters[indexOfCritterWithMinHealth].showFire = false;
					blackListedCritters[indexOfCritterWithMinHealth].splash = false;
				}
				else if (type.equals("Fire"))
				{	
					blackListedCritters[indexOfCritterWithMinHealth].showFire=true;
					blackListedCritters[indexOfCritterWithMinHealth].slowdown = false;
					blackListedCritters[indexOfCritterWithMinHealth].splash = false;
				}
				else if(type.equals("Laser")){
					blackListedCritters[indexOfCritterWithMinHealth].splash = true;
					blackListedCritters[indexOfCritterWithMinHealth].showFire = false;
					blackListedCritters[indexOfCritterWithMinHealth].slowdown = false;
				}
				targetCritter = blackListedCritters[indexOfCritterWithMinHealth];
				return blackListedCritters[indexOfCritterWithMinHealth];
			}
			return null;
	}

	
	
}
