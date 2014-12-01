package com.tdgame;

import java.util.Map.Entry;

import javax.swing.text.html.StyleSheet.BoxPainter;

/**
 * Implemented NearToEnd strategy to tower
 * @author Team2
 *
 */
public class NearToEnd implements TowerFireStrategy {

	@Override
	public Critter fire(Critter[] blackListedCritters, Critter targetCritter,int towerXPos, int towerYPos, String type) {
		// TODO Auto-generated method stub
		System.out.println("ATTACK**********************************************");
		int totalTargetEnemies = 0;
		boolean isFirstInRange=false;
		int firstInRangeIndex = 0;
		for(int i=0; i<blackListedCritters.length; i++){
			if(blackListedCritters[i] != null){
				totalTargetEnemies++;
				if(!isFirstInRange){
					isFirstInRange = true;
					firstInRangeIndex =i;
				}
				//indexOfSingleCritter = i;
			}
		}
		System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);
		int indexOfTargetCritter = 0;
		String searchKey = "";
		int blockPositionOfFirstCritter = 0;
		if(totalTargetEnemies > 0){
			searchKey = ""+(blackListedCritters[firstInRangeIndex].x/50) + "_" + ((blackListedCritters[firstInRangeIndex].y/50)+1)+"";
			indexOfTargetCritter = firstInRangeIndex;
			System.out.println("searchKey\t" + searchKey.trim());
			blockPositionOfFirstCritter = MouseHandler.boxPositionPathNumberMap.get(searchKey.trim());
			if(totalTargetEnemies > 1){
				for(int i=0; i<blackListedCritters.length; i++){
					if((i != firstInRangeIndex) && (blackListedCritters[i] != null)){
						searchKey = (blackListedCritters[i].x/50) + "_" + ((blackListedCritters[i].y/50) + 1);
						System.out.println("Current critter search key\t" + searchKey);
						int blockPositionOfCurrentCritter = MouseHandler.boxPositionPathNumberMap.get(searchKey);
						if(blockPositionOfFirstCritter <= blockPositionOfCurrentCritter){
							blockPositionOfFirstCritter = blockPositionOfCurrentCritter;
							indexOfTargetCritter = i;
						}
					}
				}
				blackListedCritters[indexOfTargetCritter].towerX=towerXPos;
				blackListedCritters[indexOfTargetCritter].towerY=towerYPos;
				blackListedCritters[indexOfTargetCritter].towerFixed=true;
				blackListedCritters[indexOfTargetCritter].isHit=true;
				if (type.equals("Tank"))
				{	
					blackListedCritters[indexOfTargetCritter].slowdown=true;
					blackListedCritters[indexOfTargetCritter].showFire = false;
					blackListedCritters[indexOfTargetCritter].splash = false;
				}
				else if (type.equals("Fire"))
				{	
					blackListedCritters[indexOfTargetCritter].showFire=true;
					blackListedCritters[indexOfTargetCritter].slowdown = false;
					blackListedCritters[indexOfTargetCritter].splash = false;
				}
				else if(type.equals("Laser")){
					blackListedCritters[indexOfTargetCritter].splash = true;
					blackListedCritters[indexOfTargetCritter].showFire = false;
					blackListedCritters[indexOfTargetCritter].slowdown = false;
				}
				targetCritter = blackListedCritters[indexOfTargetCritter];
				return blackListedCritters[indexOfTargetCritter];
			} 
			//If any problem remove this else block and uncomment "return blackListedCritters[indexOfTargetCritter];" below the block
			else {
				System.out.println("Initially it shud return from here...");
				blackListedCritters[firstInRangeIndex].towerX=towerXPos;
				blackListedCritters[firstInRangeIndex].towerY=towerYPos;
				blackListedCritters[firstInRangeIndex].towerFixed=true;
				blackListedCritters[firstInRangeIndex].isHit=true;
				if (type.equals("Tank"))
				{	
					blackListedCritters[firstInRangeIndex].slowdown=true;
				}
				else if (type.equals("Fire"))
				{	
					blackListedCritters[firstInRangeIndex].showFire=true;
				}
				else if(type.equals("Laser")){
					blackListedCritters[firstInRangeIndex].splash = true;
				}
				targetCritter = blackListedCritters[firstInRangeIndex];
				return blackListedCritters[firstInRangeIndex];
			}
		}
		return null;
	}

}
