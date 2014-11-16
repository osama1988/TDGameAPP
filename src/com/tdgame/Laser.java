package com.tdgame;

import java.awt.Color;
import java.awt.Image;

import javafx.beans.InvalidationListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Laser extends JButton implements Tower{
	
	public String imageFile = "";
	public String imgPath="";
	public Image image=null;
	public int id=0;
	public int cost=0;
	public int range=0;
	public int ammunition=0;
	public String type="";
	public int refundRate=0;
	public int costToAddAmmunition=0;
	public int rateOfFire=0;
	public int xPosInTowerMap=0;
	public int yPosInTowerMap=0;
	public int actualAmmunition=0;
	
	public int damageToCritters=0;
	public int level=0;
	public int costToIncreaseLevel=0;
	
	public Laser()
	{
		setBackground(Color.CYAN);
		setTowerProperties(id,200,1000,1,"Laser",5,"../res/towers/laser.png", 10, 1);
		setIcon(new ImageIcon(this.imgPath));
	}
	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTowerProperties(int id, int cost, int ammunition, int range,
			String type, int rateOfFire, String path, int damageToCritters, int level) {
		// TODO Auto-generated method stub
		this.id = id;
		this.ammunition=ammunition;
		this.actualAmmunition=ammunition;
		this.cost = cost;
		this.range = range;
		this.type=type;
		this.refundRate=(int)(cost*25)/100;
		this.rateOfFire=rateOfFire;
		this.costToAddAmmunition=(int)(cost*25)/100;
		this.imgPath=path;
		
		this.damageToCritters=damageToCritters;
		this.level=level;
		this.costToIncreaseLevel = (int)(cost*50)/100;
	}
	@Override
	public void increaseAmmunition(int in_ammunition) {
		// TODO Auto-generated method stub
		ammunition+=in_ammunition;
	}
	@Override
	public void setPosition(int xPos, int yPos) {
		// TODO Auto-generated method stub
		xPosInTowerMap=xPos;
		yPosInTowerMap=yPos;
	}
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return range;
	}
	@Override
	public int getAmmunition() {
		// TODO Auto-generated method stub
		return ammunition;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
	@Override
	public int getRefundRate() {
		// TODO Auto-generated method stub
		return refundRate;
	}
	@Override
	public int getCostToAddAmmunition() {
		// TODO Auto-generated method stub
		return costToAddAmmunition;
	}
	@Override
	public int getRateOfFire() {
		// TODO Auto-generated method stub
		return rateOfFire;
	}
	@Override
	public int getActualAmmunition() {
		// TODO Auto-generated method stub
		return actualAmmunition;
	}
	@Override
	public int getXPosInTowerMap() {
		// TODO Auto-generated method stub
		return xPosInTowerMap;
	}
	@Override
	public int getYPosInTowerMap() {
		// TODO Auto-generated method stub
		return yPosInTowerMap;
	}
	@Override
	public String getImgPath() {
		// TODO Auto-generated method stub
		return imgPath;
	}
	@Override
	public void decreaseAmmunition(int in_ammunition) {
		// TODO Auto-generated method stub
		ammunition-=in_ammunition;
	}
	@Override
	public int getTowerLevel() {
		// TODO Auto-generated method stub
		return level;
	}
	@Override
	public int getCostToIncreaseLevel() {
		// TODO Auto-generated method stub
		return costToIncreaseLevel;
	}
	@Override
	public void increaseLevel(){
		this.range++;
		if((this.rateOfFire % 2) == 0){
			this.rateOfFire += (50/100)*this.rateOfFire;
		} else {
			this.rateOfFire += (50/100)*this.rateOfFire + 1;
		}
		this.level++;
	}
}
