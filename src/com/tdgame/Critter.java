package com.tdgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/**
 * This class has functionality to draw and manage movement of critters on screen according to path saved in map array.
 * @author Team2
 * @version $revision
 */
public class Critter extends Rectangle {

	public int critterSize = 30;
	public int critterID;
	public boolean inGame = false;
	public boolean duplicate = false;
	public int movement = 0;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction;
	int row = 0, col = 0;
	boolean hasUpward = false;
	boolean hasDownward = false;
	boolean hasLeft = false;
	boolean hasRight = false;
	int nextDelay = 0;
	int imageWidth;
	int imageHeight;
	int adjustX;
	int adjustY;
	int rectangleX , rectangleY;
	static int crittersExited;
	int atTime=1;
	int health = 300 ,healthheight = 3;
	public final int originalHealth = health;
	int healthBarSpace;

	/** 
	 * Constructor
	 * @param imgWidth Sets critter image width
	 * @param imgHeight Sets critter image height 
	 * @param imgX Adjusts position of criiter image
	 * @param imgY Adjusts position of critter image
	 * @param rectX Adjusts initial X position of rectangle which contains image of critter
	 * @param rectY Adjusts initial Y position of rectangle which contains image of critter
	 * @param healthSpace Adjusts Y position of health bar displayed over critter
	 */
	public Critter(int imgWidth, int imgHeight, int imgX, int imgY, int rectX, int rectY, int healthSpace)
	{
		imageHeight = imgHeight;
		imageWidth = imgWidth;
		adjustX = imgX;
		adjustY = imgY;
		rectangleX = rectX;
		rectangleY = rectY;
		healthBarSpace = healthSpace;
	}
	public Critter(){
		
	}
	/**
	 * To create rectangle of critter
	 * @param critterID index of image stored in array
	 */
	public void createCritter(int critterID) {
		for (int row = 0; row < Screen.map.length; row++)
			for (int col = 0; col < Screen.map[0].length; col++) {
				if (Screen.map[row][col] == 1) {

					setBounds((int) Screen.width + rectangleX + (int) Screen.width
							* row, (int) Screen.height + rectangleY
							+ (int) Screen.height * col, 50, 25);
					this.critterID = critterID;
					this.row = row;
					this.col = col;
				}
			}
		inGame = true;
	}

	public void draw(Graphics g) {
		if (inGame) {
			g.drawImage(Screen.crittersImgs[critterID], x + adjustX, y + adjustY, imageWidth, imageHeight, null);
			g.setColor(Color.GREEN);
			if(health < originalHealth){
				g.setColor(Color.RED);
			}
			g.fillRect(x + adjustX + 15, y + healthBarSpace, (int)(((double)health/(double)originalHealth)*(imageWidth/2)), healthheight);
		}
	}

	int moveFrame = 0, moveSpeed = 10;
	int prevdir;
/**
 * It keep on checking map array and changes direction of critters accordingly
 * @param initialDelay
 * @param addition
 * @param refreshValue
 */
	public void physics(int initialDelay, int addition, int refreshValue) {
		// Check map array when movement is 0 or after given delay so that critters wont get direction early	
		if (movement == 0 || movement == initialDelay + nextDelay) {
			// Make sure that row value must not exceed array index bounds and must not already have opposite direction
			
			if (row + 1 < Screen.map.length && Screen.map[row + 1][col] > 1 && !hasLeft) {
				direction = right;
				this.row = row + 1;
				hasRight = true;
				hasDownward = false;
				hasUpward = false;
			}

			else if (row > 0 && Screen.map[row - 1][col] > 1 && !hasRight) {
				direction = left;
				this.row = row - 1;
				hasLeft = true;
				hasDownward = false;
				hasUpward = false;
			}

			else if (col > 0 && Screen.map[row][col - 1] > 1 && !hasDownward) {
				direction = upward;
				this.col = col - 1;
				hasUpward = true;
				hasRight = false;
				hasLeft = false;
			}

			else if (col + 1 < Screen.map[0].length && Screen.map[row][col + 1] > 1 && !hasUpward) {
				direction = downward;
				this.col = col + 1;
				hasDownward = true;
				hasRight = false;
				hasLeft = false;
			}
			
			// To keep on adding delay upto a certain value so that critters won't get direction early and later.
			if (nextDelay >= refreshValue)
				nextDelay = 0;

			nextDelay += addition;
			movement = 0;
	
		}
		
		if (Screen.map[row][col] == 2) {
			inGame = false;
			duplicate = true;
			crittersExited++;
			if(Screen.waveType=="Single")
				atTime=1;
			else
				atTime=2;
			
			if (crittersExited >= Screen.noOfCritters * atTime)
			{
				if(Screen.waveType=="Single")
					Screen.waveType="Double";
				else
					Screen.waveType="Single";
				
				Screen.isFirst=true;
				crittersExited=0;
				JOptionPane.showMessageDialog(null,"Game Over");
			}
		}
		moveFwd();
		movement += 1;
	}

	public void moveFwd() {
		// The first check is to produce delay
		if (moveFrame >= moveSpeed) {
			if (direction == right)
				x += 1;
			else if (direction == left)
				x -= 1;
			else if (direction == upward)
				y -= 1;
			else if (direction == downward)
				y += 1;
			moveFrame = 0;
		} else
			moveFrame += 1;
	}

	public int getCritterX()
	{
		return this.x;//(x + adjustX );//this.x;
	}
	public int getCritterY()
	{
		return this.y;//(y + adjustY );//this.y;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public Critter update() {
		if(this.health <= 0){
			return null;
		}
		return this;
	}
	
}
