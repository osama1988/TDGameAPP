package com.tdgame;

/**
 * 
 * This class is calling methods of User class for setting user properties.
 * It will also be used in the next builds.
 * 
 * @author Team2
 * @version $revision 
 *
 */
public class Player{

	private static Player instanse;
	
	public int health;
	public int money;
	
	
	private Player(int in_health,int in_money) {
		health=in_health;
		money=in_money;
	}
	
	public static Player getInstance(User user){
		if(instanse==null){
			instanse=new Player(user.startingHealth,user.startingCash);
		}
		return instanse;
	}

}
