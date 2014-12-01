package com.tdgame;

public class TowerFire {
	private TowerFireStrategy strategy;
	public void setFireStrategy(TowerFireStrategy strategy){
		this.strategy=strategy;
	}
	public Critter fire(Critter[] blackListedCritters, Critter targetCritter,int towerXPos, int towerYPos, String type){
		
		return this.strategy.fire(blackListedCritters,targetCritter,towerXPos,towerYPos,type);
	}

}
