package com.tdgame;

public class TowerDecorator extends Tower{

	public Tower decoratedTower;
	
	public TowerDecorator(Tower decoratedTower) {
		this.decoratedTower = decoratedTower;
		this.decoratedTower.increaseLevel();
	}
	public void increaseLevel(){
		
		
		decoratedTower.increaseLevel();
		
		
		this.range++;
		if((this.rateOfFire % 2) == 0){
			this.rateOfFire +=  (int)((this.rateOfFire*50)/100) ;
		} else {
			this.rateOfFire += ((int)(this.rateOfFire*50)/100) + 1;
		}
		this.level++;
	}

}
