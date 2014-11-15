package com.tdgame;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Fire extends JButton implements Tower,Observable {
	
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
	
	public Fire()
	{
		setBackground(Color.orange);
		setTowerProperties(id,100,10000,1,"Fire",10,"../res/towers/fire.png");
		setIcon(new ImageIcon(this.imgPath));
	}
	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTowerProperties(int id, int cost, int ammunition, int range,
			String type, int rateOfFire, String path) {
		// TODO Auto-generated method stub
		this.id = id;
		this.ammunition=ammunition;
		this.actualAmmunition=ammunition;
		this.cost = cost;
		this.range = range;
		this.type=type;
		this.refundRate=(int)(cost*25)/100;
		this.rateOfFire=rateOfFire;
		this.costToAddAmmunition=(int)(cost*50)/100;
		this.imgPath=path;
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
	public int getCost(){
 		return cost;
 	}
	@Override
 	public int getRange(){
 		return range;
 	}
	@Override
 	public int getAmmunition() {
		return ammunition;
	}
	@Override
 	public String getType(){
 		return type;
 	}
	@Override
 	public int getRefundRate(){
			return refundRate;
	}
	@Override
 	public int getCostToAddAmmunition(){
 		return costToAddAmmunition;
 	}
	@Override
 	public int getRateOfFire(){
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
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setObserver(Screen screen) {
		// TODO Auto-generated method stub
	}
	

}
