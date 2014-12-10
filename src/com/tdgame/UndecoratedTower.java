package com.tdgame;


public class UndecoratedTower extends TowerDecorator{

	public UndecoratedTower(Tower decoratedTower) {
		super(decoratedTower);
	}
	
	public void increaseLevel(){
		super.increaseLevel();
	}

}
