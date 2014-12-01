package com.tdgame;

import java.awt.Color;
import java.awt.Image;


import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * To create Laser type of tower
 * @author TEAM 2
 *
 */
public class Laser extends Tower{
	
	/**
	 * Laser property of the tower
	 */
	public Laser()
	{
		setBackground(Color.CYAN);
		//setTowerProperties(id, cost, ammunition, range, type,    rateOfFire,      path,           damageToCritters, level, weakstrategy)
		setTowerProperties(  id,  200,     1000,      2,  "Laser",   5,       "../res/towers/laser.png",     10,        1);
		//setTowerProperties(  id,  200,     1000,      1,  "Laser",   5,       "../res/towers/laser.png",     10,        1,     4);
		setIcon(new ImageIcon(this.imgPath));
		
		this.setSplashDamageEffect();
	}
	
}
