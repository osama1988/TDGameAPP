package com.tdgame;

public class NearToTower implements TowerFireStrategy {

	@Override
	public Critter fire(Critter[] blackListedCritters,
			Critter targetCritter, int towerXPos, int towerYPos, String type) {
		// TODO Auto-generated method stub
		int totalTargetEnemies = 0;
		int indexOfCritterWithMinDistanceFromTower = 0;
		double minDistance = 999;
		
		for(int i=0; i<blackListedCritters.length; i++){
			if(blackListedCritters[i] != null && blackListedCritters[i].inGame){
				totalTargetEnemies++;
				if(blackListedCritters[i].distanceOfBlackListedCritter < minDistance){
					minDistance = blackListedCritters[i].distanceOfBlackListedCritter;
					indexOfCritterWithMinDistanceFromTower = i;
				}
			}
		}
		System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

		if(totalTargetEnemies > 0){
			System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
			System.out.println("random enemy number\t" + targetCritter);

			blackListedCritters[indexOfCritterWithMinDistanceFromTower].towerX=towerXPos;
			blackListedCritters[indexOfCritterWithMinDistanceFromTower].towerY=towerYPos;
			blackListedCritters[indexOfCritterWithMinDistanceFromTower].towerFixed=true;
			blackListedCritters[indexOfCritterWithMinDistanceFromTower].isHit=true;
			if (type.equals("Tank"))
			{	
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].slowdown=true;
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].showFire = false;
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].splash = false;
			}
			else if (type.equals("Fire"))
			{	
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].showFire=true;
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].slowdown = false;
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].splash = false;
			}
			else if(type.equals("Laser")){
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].splash = true;
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].showFire = false;
				blackListedCritters[indexOfCritterWithMinDistanceFromTower].slowdown = false;
			}
			targetCritter = blackListedCritters[indexOfCritterWithMinDistanceFromTower];

			return blackListedCritters[indexOfCritterWithMinDistanceFromTower];
		}
		return null;
	}

	

}
