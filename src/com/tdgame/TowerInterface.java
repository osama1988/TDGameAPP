package com.tdgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;


/** Tower Interface (or Abstract class) that is the super-type of all 
 * types of objects produced by the TowerFactory. 
 *
 */
public interface TowerInterface extends Tower{
	
 	void fire();
 	public void setTowerProperties(int id, int cost,int ammunition,int range,String type,int rateOfFire,String path, int damageToCritters, int Level);
 		
}