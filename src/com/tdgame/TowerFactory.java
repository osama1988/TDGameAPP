package com.tdgame;

/**
 * Implementation for Tower Factory Pattern
 */
public class TowerFactory {

	static public Tower getTower(String towerType)
	{
		
		if (towerType.equalsIgnoreCase("FIRE")){
			return new Fire();
		}
        else if (towerType.equalsIgnoreCase("LASER")){
        	return new Laser();
        }
        else if (towerType.equalsIgnoreCase("BOMBER")){
        	return new Bomber();
        }
        else if (towerType.equalsIgnoreCase("TANK")){
        	return new Tank();
        }
        else if (towerType.equalsIgnoreCase("MISSILE")){
        	return new Missile();
        }
		return null;
	}
}
