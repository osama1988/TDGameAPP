package com.tdgame;


/**
 * This class maintains the details of the user like money, health and score.
 * @author Team 2
 * @version $Revision
 *
 */
public class User {

	private Screen screen;
	
	Player player;
	
	public int startingCash = 1500;
	public int startingHealth = 100;
	public int score = 0;
	
	public User(Screen screen) {
		this.screen = screen;
		this.screen.scene = 0;//sets scene to the welcome screen
	}
	
	// calls constructor of the player class to create a player
	public void createPlayer(){ 
		this.player = Player.getInstance(this);
	}

}
