package com.tdgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Critter extends Rectangle {

	public int critterSize = 30;
	public int critterID;
	public boolean inGame = false;
	public boolean duplicate = false;
	public int movement = 0;
	public int upward = 0, downward = 1, right = 2 , left = 3;
	public int direction;
	int row = 0, col = 0;
	boolean hasUpward = false;
	boolean hasDownward = false;
	boolean hasLeft = false;
	boolean hasRight = false;
	int nextDelay = 0;

	public void createCritter(int critterID) {
		for (int row = 0; row < Screen.map.length; row++)
			for (int col = 0; col < Screen.map[0].length; col++) {
				if (Screen.map[row][col] == 1) {
					 
					setBounds((int) Screen.width - 15 + (int) Screen.width * row, (int) Screen.height - 45
							+ (int) Screen.height * col, 50, 25);
					this.critterID = critterID;
					this.row = row;
					this.col = col;
									}
			}
		inGame = true;
		
	}

	public void draw(Graphics g) {
		if (inGame)
		{
			g.drawImage(Screen.crittersImgs[critterID], 25 + x, y, 20, 20,
					null);
//			g.setColor(Color.YELLOW);
//			g.fillRect(25 + x, y, 40, 20);
		}
	}

	int moveFrame = 0, moveSpeed = 10;
	int prevdir;
	public void physics() {
		
		if (movement == 0 || movement == 530 + nextDelay) {
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
			
			if (nextDelay >= 49)
				nextDelay = 0;
			
			nextDelay += 2;
			movement = 0;
		}
		if (Screen.map[row][col] == 2)
		{
			inGame = false;
			duplicate = true;
		}
		moveFwd();
		movement += 1;
	}

	public void moveFwd() {
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

}
