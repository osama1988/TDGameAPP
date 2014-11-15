package com.tdgame;

import java.awt.Graphics;

public class CritterWave {
	
	private CritterStrategy strategy;
	public void setStrategy(CritterStrategy strategy){
		this.strategy=strategy;
	}
	public Critter[] startWave(){
		return this.strategy.startWave();
	}
	
}
