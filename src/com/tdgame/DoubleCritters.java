package com.tdgame;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class DoubleCritters implements CritterStrategy {
	
	@Override
	public void startWave() {
		// TODO Auto-generated method stub
		Screen.waveType="Double";
		Screen.noOfCritters += 2;
		Screen.critters = new Critter[Screen.noOfCritters];
		Screen.critters2 = new Critter[Screen.noOfCritters];
		for(int i=0;i<Screen.critters.length;i++)
		{
			Screen.critters[i] = new Critter(50,50,25,0,-15,-60,12);
			Screen.critters2[i] = new Critter(50,50,10,-15,0,-20,0);
		}
		if(Screen.isFirst)
			Screen.crittersImgs[0] = new ImageIcon("../res/critter.gif").getImage();
		Screen.isFirst=false;
		
	}

}
