package com.tdgame;


public class SingleCritters implements CritterStrategy{
//	public static Image[] crittersImgs = new Image[10];
	public Critter[] critters;
	public int noOfCritters = 8;
	
	@Override
	public Critter[] startWave() {
		// TODO Auto-generated method stub
		noOfCritters += 2;
		critters = new Critter[noOfCritters];
		for(int i=0;i<critters.length;i++)
		{
			critters[i] = new Critter(50,50,25,0,-15,-60);
		}
		
		return critters;
	}

}
