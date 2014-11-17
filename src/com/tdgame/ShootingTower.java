package com.tdgame;

public class ShootingTower extends Thread{
	//Graphics tempGraphics;
	Screen screen;
	
	public ShootingTower(Screen screen){
		//this.tempGraphics = tempGraphics;
		this.screen = screen;
	}
	public void run(){
		while(true){
			screen.shootingEffect();//this.tempGraphics);
		}
	}
}
