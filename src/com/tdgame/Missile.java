package com.tdgame;

import java.awt.Color;
import java.awt.Image;


import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * To create Missile type of tower
 * @author TEAM 2
 *
 */

public class Missile extends Tower{
	
	public Missile()
	{
		setBackground(Color.black);
		//setTowerProperties(id, cost, ammunition, range,   type,       rateOfFire,      path,             damageToCritters, level, strongstrategy)
		setTowerProperties(id,   1000,     3,        3,   "Missile",       1,       "../res/towers/missile.png",    25,       1);
		//setTowerProperties(id,   1000,     3,        3,   "Missile",       1,       "../res/towers/missile.png",    25,       1,       3);
		setIcon(new ImageIcon(this.imgPath));
	}
	
	
}
