package com.tdgame;

import java.awt.Color;
import java.awt.Image;

import javafx.beans.InvalidationListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TowerProperties extends JButton {

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
	public void increaseAmmunition(int in_ammunition) {
		ammunition+=in_ammunition;
	}
	public void setPosition(int xPos, int yPos) {
		xPosInTowerMap=xPos;
		yPosInTowerMap=yPos;
	}
	public int getCost() {
		return cost;
	}
	public int getRange() {
		return range;
	}
	public int getAmmunition() {
		return ammunition;
	}
	public String getType() {
		return type;
	}
	public int getRefundRate() {
		return refundRate;
	}
	public int getCostToAddAmmunition() {
		return costToAddAmmunition;
	}
	public int getRateOfFire() {
		return rateOfFire;
	}
	public int getActualAmmunition() {
		return actualAmmunition;
	}
	public int getXPosInTowerMap() {
		return xPosInTowerMap;
	}
	public int getYPosInTowerMap() {
		return yPosInTowerMap;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void decreaseAmmunition(int in_ammunition) {
		ammunition-=in_ammunition;
	}
	public int getTowerLevel() {
		return level;
	}
	public int getCostToIncreaseLevel() {
		return costToIncreaseLevel;
	}
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
