package com.tdgame;

import java.awt.Color;
import java.awt.Image;

import javafx.beans.InvalidationListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Tank extends Tower{
	
	public Tank()
	{
		setBackground(Color.MAGENTA);
		//setTowerProperties(id, cost, ammunition, range, type, rateOfFire,      path,               damageToCritters, level, Strongstrategy)
		setTowerProperties(id,   800,    10,        3,    "Tank",     2,    "../res/towers/tank.png",     20,            1,      3);
		setIcon(new ImageIcon(this.imgPath));
	}
	
	
}
