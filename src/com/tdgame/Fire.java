package com.tdgame;

import java.awt.Color;
import java.awt.Image;	//import java.awt.event.ActionEvent; //import java.awt.event.ActionListener; //import javafx.beans.Observable;


import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * To create Fire type of tower
 * @author TEAM 2
 *
 */
public class Fire extends Tower {
	
	/**
	 * Setting Fire property of the tower
	 */
	public Fire()
	{
		setBackground(Color.orange);
		//setTowerProperties(id, cost, ammunition, range, type, rateOfFire,      path,               damageToCritters, level, weakStrategy)
		setTowerProperties(id,    100,    10000,     1,  "Fire",     10,     "../res/towers/fire.png",         5,        1);
		//setTowerProperties(id,    100,    10000,     1,  "Fire",     10,     "../res/towers/fire.png",         5,        1,         4);
		setIcon(new ImageIcon(this.imgPath));
	}
}
