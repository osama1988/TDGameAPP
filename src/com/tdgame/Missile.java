package com.tdgame;

import java.awt.Color;
import java.awt.Image;

import javafx.beans.InvalidationListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Missile extends Tower{
	
	public Missile()
	{
		setBackground(Color.black);
		setTowerProperties(id,1000,3,3,"Missile",1,"../res/towers/missile.png", 25, 1);
		setIcon(new ImageIcon(this.imgPath));
	}
	
	
}
