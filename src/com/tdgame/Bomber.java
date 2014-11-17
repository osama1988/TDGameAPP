package com.tdgame;

import java.awt.Color;
import java.awt.Image;


import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * This class is for Bomber type of tower having specific features
 * @author Team 2
 *
 */
public class Bomber extends Tower {
	
	
	public Bomber()
	{
		setBackground(Color.LIGHT_GRAY);
		//setTowerProperties(id, cost, ammunition, range, type, rateOfFire,      path,       damageToCritters, level, Randomstrategy)
		setTowerProperties(  id,  500,    100,      2,   "Bomber",  10,  "../res/towers/bomber.png", 50,         1,     2);
		setIcon(new ImageIcon(this.imgPath));
	}

}
