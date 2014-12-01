package com.tdgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
	public int row = 0;
	public int col = 0;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;
	int nextDelay = 0;
	public int imageWidth;
	public int imageHeight;
	public int adjustX;
	public int adjustY;
	public int rectangleX;
	public int rectangleY;
	static int crittersExited=0;
	int atTime = 1;
	public int health;
	int healthheight = 3;
	public int originalHealth;
	public int healthBarSpace;
	public int towerX;
	public int towerY;
	boolean towerFixed;
	boolean isHit;
	public boolean showFire = false;
	public boolean slowdown = false;
	int damageTime;
	public double distanceOfBlackListedCritter=999.00;
	JLabel testlabel;
	public int moveFrame = 0;
	public int moveSpeed;
	public boolean splash = false;
	int critterX = 0;
	int critterY = 0;

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
	public Critter(int imgWidth, int imgHeight, int imgX, int imgY, int rectX, int rectY, int healthSpace, int health, int speed)
	{
		this.imageHeight = imgHeight;
		this.imageWidth = imgWidth;
		this.adjustX = imgX;
		this.adjustY = imgY;
		this.rectangleX = rectX;
		this.rectangleY = rectY;
		this.healthBarSpace = healthSpace;
		this.health = health;
		this.originalHealth = 100;
		this.damageTime = 0;
		this.moveSpeed = speed;
	}
	public Critter(){

	}
	/**
	 * To create rectangle of critter
	 * @param critterID index of image stored in array
	 */
	public void createCritter(int critterID) {
		boolean breakTest = false;
		for (int row = 0; row < Screen.map.length; row++){
			if(breakTest){
				break;
			}
			for (int col = 0; col < Screen.map[0].length; col++) {
				if (Screen.map[row][col] == 1) {
					this.critterX = (int)(Screen.width + Screen.width*row) - 30;//292
					this.critterY = (int)(Screen.height + Screen.height*col) - 50;//447;
					//setBounds(critterX, critterY, 30, 20);
					
					this.critterX = (int)(Screen.width + Screen.width*row) + rectangleX;//292
					this.critterY = (int)(Screen.height + Screen.height*col) + rectangleY;//447;
					setBounds(critterX, critterY, 50, 25);
					
					System.out.println("CritterX\t" + critterX);
					System.out.println("CritterY\t" + critterY);
					this.critterID = critterID;
					this.row = row;
					this.col = col;
					breakTest = true;
					break;
				}
			}
		}
		inGame = true;
	}

	public void draw(Graphics g,int imgId) {
		if (inGame) {
			int critterXPos = x + adjustX;
			int critterYPos = y + adjustY;
			//g.drawImage(Screen.crittersImgs[imgId], critterX, critterY, imageWidth, imageHeight, null);
			g.drawImage(Screen.crittersImgs[imgId], critterXPos, critterYPos, imageWidth, imageHeight, null);
			g.setColor(Color.GREEN);
			if(health < originalHealth){
				g.setColor(Color.RED);
			}
			int widthOfHealthBar = (int)(((double)health/(double)originalHealth)*(imageWidth/2));
			int xPos = x + adjustX + 15;
			int yPos = y + healthBarSpace;
			//g.fillRect(critterXpos, critterYPos, widthOfHealthBar, healthheight);
			g.fillRect(xPos , yPos, widthOfHealthBar, healthheight);			
		}
	}

	/**
	 * It keep on checking map array and changes direction of critters accordingly
	 * @param initialDelay
	 * @param addition
	 * @param refreshValue
	 */
	public void physics(int initialDelay, int addition, int refreshValue) {
		// Check map array when movement is 0 or after given delay so that critters wont get direction early	
		int totalDelay = initialDelay + nextDelay;
		if (movement == 0 || movement == totalDelay) {
			// Make sure that row value must not exceed array index bounds and must not already have opposite direction
			System.out.println("changing direction...");
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
			Screen.user.player.money -= health;
			if(Screen.waveType=="Single")
				atTime=1;
			else
				atTime=2;
			if (crittersExited == 10)
			{
				if(Screen.waveType=="Single")
				{
					Screen.waveType="Double";
					for(int i=0;i<Screen.critters.length;i++)
						Screen.critters[i].inGame=false;
				}
				else
				{
					Screen.waveType="Single";
					if(Screen.critters != null){
						for(int i=0;i<Screen.critters.length;i++)
						{
							if(Screen.critters[i] != null){
								Screen.critters[i].inGame=false;
								Screen.critters2[i].inGame=false;
							}
						}
					}
				}

				Screen.isFirst=true;
				crittersExited=0;
				JOptionPane.showMessageDialog(null,"Game Over");
			}
			else if(crittersExited==Screen.noOfCritters)
			{
				Screen.isWaveRunning=false;
				Screen.saveLogXML.writeLog("Wave",Screen.waveType+" "+Screen.noOfCritters , Screen.waveType+" wave of "+Screen.noOfCritters+" finished");
				
			}
		}
		moveFwd();
		movement += 1;
	}

	public void moveFwd() {

		if(damageTime > 0){
			damageTime--;
		}
		
		if (moveFrame >= moveSpeed) {
			
			
			if (direction == right){
				this.critterX++;
				x += 1;
			} else if (direction == left) {
				x -= 1;
				this.critterX--;
			} else if (direction == upward) {
				y -= 1;
				this.critterY--;
			} else if (direction == downward) {
				y += 1;
				this.critterY++;
			}
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
			//this.inGame = false;
		}
		return this;
	}

}
