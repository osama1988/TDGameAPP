package com.tdgame;

import java.awt.Image;
import java.util.Random;

import javax.swing.JButton;

/** Tower Interface (or Abstract class) that is the super-type of all 
 * types of objects produced by the TowerFactory. 
 *
 */
public abstract class Tower extends JButton{

	public String imageFile = "";
	public String imgPath="";
	public Image image=null;
	public int id=0;
	public int cost=0;
	public int range=0;
	public int ammunition=0;
	public String type="";
	public int refundRate=0;
	public int costToAddAmmunition=0;
	public int rateOfFire=0;
	public int xPosInTowerMap=0;
	public int yPosInTowerMap=0;
	public int actualAmmunition=0;

	void fire() {
	}


	public void setTowerProperties(int id, int cost, int ammunition, int range,
			String type, int rateOfFire, String path, int damageToCritters, int level, int strategy) {
		this.id = id;
		this.ammunition=ammunition;
		this.actualAmmunition=ammunition;
		this.cost = cost;
		this.range = range;
		this.type=type;
		this.refundRate=(int)(cost*25)/100;
		this.rateOfFire=rateOfFire;
		this.costToAddAmmunition=(int)(cost*25)/100;
		this.imgPath=path;

		this.damageToCritters=damageToCritters;
		this.level=level;
		this.costToIncreaseLevel = (int)(cost*50)/100;
		this.attackStrategy = strategy;
	}
	public void increaseAmmunition(int in_ammunition) {
		ammunition+=in_ammunition;
	}
	public void setPosition(int xPos, int yPos) {
		xPosInTowerMap=xPos;
		yPosInTowerMap=yPos;
	}
	public int getCost() {
		return cost;
	}
	public int getRange() {
		return range;
	}
	public int getAmmunition() {
		return ammunition;
	}
	public String getType() {
		return type;
	}
	public int getRefundRate() {
		return refundRate;
	}
	public int getCostToAddAmmunition() {
		return costToAddAmmunition;
	}
	public int getRateOfFire() {
		return rateOfFire;
	}
	public int getActualAmmunition() {
		return actualAmmunition;
	}
	public int getXPosInTowerMap() {
		return xPosInTowerMap;
	}
	public int getYPosInTowerMap() {
		return yPosInTowerMap;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void decreaseAmmunition(int in_ammunition) {
		ammunition-=in_ammunition;
	}
	public int getTowerLevel() {
		return level;
	}
	public int getCostToIncreaseLevel() {
		return costToIncreaseLevel;
	}
	public void increaseLevel(){
		this.range++;
		if((this.rateOfFire % 2) == 0){
			this.rateOfFire +=  (int)(this.rateOfFire*50)/100 ;
		} else {
			this.rateOfFire += ((int)(this.rateOfFire*50)/100) + 1;
		}
		this.level++;
	}

	public boolean shootingThread = false;

	public int damageToCritters = 0;
	public int level=0;
	public int costToIncreaseLevel=0;

	public int attackTime = 0; //Lifetime of the attack(bullet or bomb) on the map/screen
	public int attackDelay = 0;//time delay between each attack(time for reloading bullets when finished)

	public int maxAttackTime = 200;
	public int maxAttackDelay = 500;

	public int FIRSTCRITTER = 1;			//nearest critter to the tower 
	public int RANDOMCRITTER = 2;			//random to the tower-------bomber
	public int STRONGESTCRITTER = 3;		//Critter with max health------ missile & tank
	public int WEAKESTCRITTER = 4;			//Critter with min health ----- laser & fire

	public int attackStrategy;
	public Critter targetCritter;


	public Critter findTargetCritter(Critter[] critters, int towerXPos, int towerYPos) {
		if(critters!=null){
			System.out.println("In findTargetCritters...\ncritters!=null\t" + (critters!=null));
			Critter[] blackListedCritters = new Critter[critters.length];
			int towerRadius = this.range;
			int critterXPos = 0;
			int critterYPos = 0;
			int critterRadius = 1;
			for(int i=0; i<critters.length; i++){
				if(critters[i] != null){
					critterXPos = (int) ((critters[i].x)/50);
					critterYPos = (int) ((critters[i].y)/50);
					System.out.println("CritterX\tCritterY\n" + critterXPos + "\t" + critterYPos);
					int dx = critterXPos - towerXPos;
					int dy = critterYPos - towerYPos;
					System.out.println("Critter distanceX\tdistanceX\n" + dx + "\t" + dy);
					int dradius = towerRadius + critterRadius;
					System.out.println("radius (towerRadius - critterRadius)\n" + towerRadius + " + " + critterRadius + " = " + (towerRadius + critterRadius));
					System.out.println("enemy at index\t" + i);
					//int tempmayur=critters[i].x-(towerXPos*50)-50;
					//if(tempmayur< 50){
					if(((dx*dx) + (dy*dy)) < (dradius * dradius)){
						System.out.println(i + "in range...adding to eInRange list");
						blackListedCritters[i] = critters[i];
					}else{
						critters[i].towerFixed = false;
						System.out.println(i + "not in range...");
					}
				}
				
				
				
			}
			if(this.attackStrategy == RANDOMCRITTER){
				int totalTargetEnemies = 0;

				for(int i=0; i<blackListedCritters.length; i++){
					if(blackListedCritters[i] != null){
						totalTargetEnemies++;
					}
				}
				System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

				if(totalTargetEnemies > 0){
					System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
					int targetCritter = new Random().nextInt(totalTargetEnemies);
					System.out.println("random enemy number\t" + targetCritter);
					int crittersKilled = 0;
					int noOfCritterssChecked = 0;

					while(true){
						System.out.println("killed\t" + crittersKilled + "\nchecked\t" + noOfCritterssChecked);
						if(crittersKilled == targetCritter && blackListedCritters[noOfCritterssChecked] != null && blackListedCritters[noOfCritterssChecked].inGame){
							System.out.println("enemiesKilled == enemy && enemiesInRange[noOfEnemiesChecked] != null");
							System.out.println("returning this random enemy to be killed...");
							blackListedCritters[noOfCritterssChecked].towerX=towerXPos;
							blackListedCritters[noOfCritterssChecked].towerY=towerYPos;
							blackListedCritters[noOfCritterssChecked].towerFixed=true;
							return blackListedCritters[noOfCritterssChecked];
						}
						if(targetCritter > crittersKilled){
							if(blackListedCritters != null){
								crittersKilled++;
								System.out.println("crittersInRange != null\tkilled\t" + crittersKilled);
							}
							noOfCritterssChecked++;
							System.out.println("inc... checked\t" + noOfCritterssChecked);
						} else{
							return null;
						}
					}
				}
			} else if(this.attackStrategy == STRONGESTCRITTER){
				int totalTargetEnemies = 0;
				int indexOfCritterWithMaxHealth = 0;
				int maxHealth = 0;
				for(int i=0; i<blackListedCritters.length; i++){
					if(blackListedCritters[i] != null && blackListedCritters[i].inGame){
						totalTargetEnemies++;
						if(blackListedCritters[i].health > maxHealth){
							indexOfCritterWithMaxHealth = i;
						}
					}
				}
				System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

				if(totalTargetEnemies > 0){
					System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
					System.out.println("random enemy number\t" + targetCritter);

					blackListedCritters[indexOfCritterWithMaxHealth].towerX=towerXPos;
					blackListedCritters[indexOfCritterWithMaxHealth].towerY=towerYPos;
					blackListedCritters[indexOfCritterWithMaxHealth].towerFixed=true;
					return blackListedCritters[indexOfCritterWithMaxHealth];
				}
			} else if(this.attackStrategy == WEAKESTCRITTER){
				
				int totalTargetEnemies = 0;
				int indexOfCritterWithMinHealth = 0;
				int minHealth = 999;
				
				for(int i=0; i<blackListedCritters.length; i++){
					if(blackListedCritters[i] != null && blackListedCritters[i].inGame){
						totalTargetEnemies++;
						if(blackListedCritters[i].health < minHealth){
							indexOfCritterWithMinHealth = i;
						}
					}
				}
				System.out.println("total number of inrange enemies...\t" + totalTargetEnemies);

				if(totalTargetEnemies > 0){
					System.out.println("totalEnemies > 0\t" + (totalTargetEnemies > 0));
					System.out.println("random enemy number\t" + targetCritter);

					blackListedCritters[indexOfCritterWithMinHealth].towerX=towerXPos;
					blackListedCritters[indexOfCritterWithMinHealth].towerY=towerYPos;
					blackListedCritters[indexOfCritterWithMinHealth].towerFixed=true;
					return blackListedCritters[indexOfCritterWithMinHealth];
				}
			}
		} else {
			//System.out.println("Fuck");
		}
		return null;
	}
	/*@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void setObserver(Screen screen) {
		// TODO Auto-generated method stub
	}*/

	public int getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}

	public int getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}

	public int getMaxAttackTime() {
		return maxAttackTime;
	}

	public void setMaxAttackTime(int maxAttackTime) {
		this.maxAttackTime = maxAttackTime;
	}

	public int getMaxAttackDelay() {
		return maxAttackDelay;
	}

	public void setMaxAttackDelay(int maxAttackDelay) {
		this.maxAttackDelay = maxAttackDelay;
	}

	public void towerAttack(int x, int y, Critter critter){
		System.out.println("Reducing health...\nOriginal\t" + critter.health +"\nNow\t" + (critter.health-this.getDamageToCritters()));
		critter.health-=this.getDamageToCritters();
		Screen.user.player.money += this.getDamageToCritters();
	}

	public int getDamageToCritters() {
		return damageToCritters;
	}

	public void setDamageToCritters(int damageToCritters) {
		this.damageToCritters = damageToCritters;
	}

	public Critter getTargetCritter() {
		return targetCritter;
	}

	public void setTargetCritter(Critter targetCritter) {
		this.targetCritter = targetCritter;
	}

}

