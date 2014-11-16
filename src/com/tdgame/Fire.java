package com.tdgame;

import java.awt.Color;
import java.awt.Image;	//import java.awt.event.ActionEvent; //import java.awt.event.ActionListener; //import javafx.beans.Observable;

import javafx.beans.InvalidationListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Fire extends Tower {
	
	
	public Fire()
	{
		setBackground(Color.orange);
		setTowerProperties(id,100,10000,1,"Fire",10,"../res/towers/fire.png", 5, 1);
		setIcon(new ImageIcon(this.imgPath));
	}
}
