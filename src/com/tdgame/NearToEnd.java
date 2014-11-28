package com.tdgame;

import java.util.Random;

public class NearToEnd implements TowerFireStrategy {

	@Override
	public Critter fire(Critter[] blackListedCritters, Critter targetCritter,int towerXPos, int towerYPos, String type) {
		// TODO Auto-generated method stub
		int totalTargetEnemies = 0;

		for(int i=0; i<blackListedCritters.length; i++){
			if(blackListedCritters[i] != null){
				totalTargetEnemies++;
			}
		}
		System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);
		int indexOfTargetCritter = 0;
		String searchKey = "";
		int targetBlockPosition = 0;
		if(totalTargetEnemies > 0){
			searchKey = (blackListedCritters[0].x/50) + "_" + (blackListedCritters[0].y/50);
			indexOfTargetCritter = 0;
			targetBlockPosition = MouseHandler.boxPositionPathNumberMap.get(searchKey);
			if(totalTargetEnemies > 1){
				for(int i=0; i<totalTargetEnemies; i++){
					searchKey = (blackListedCritters[i].x/50) + "_" + (blackListedCritters[i].y/50);
					if(targetBlockPosition <= MouseHandler.boxPositionPathNumberMap.get(searchKey)){
						targetBlockPosition = MouseHandler.boxPositionPathNumberMap.get(searchKey);
						indexOfTargetCritter = i;
					}
				}
				return blackListedCritters[indexOfTargetCritter];
			} 
			//If any problem remove this else block and uncomment "return blackListedCritters[indexOfTargetCritter];" below the block
			else {
				return blackListedCritters[0];
			}
		}
		return null;
	}

}
