package com.tdgame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.FileNotFoundException;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/** 
 * This class is responsible for controlling the life cycle of the application, it contains the method to create,
 * load and save a map based upon the dimensions entered by the user. This class is also responsible for controlling 
 * the life cycle of the towers on the map.
 * 
 * IMPORTANT: 1) For external resources we are using relative path. For eg, "../level/Base.xml"
 * 
 * 			  2) The width and height of the applet should be set to the screen size manually by going into 'Run Configurations - 
 * 				 Parameters'. This would be changed into a browser driven applet that would display the applet dynamically.
 * 
 * @author Team 2
 * @version $revision
 * 
 */
public class Screen extends JPanel implements Runnable{

	/* Common variables */
	//String pathForTesting="D:/Java/TDGameAPP/level/";
	//	String pathForTesting = "/Users/osamayawar/Desktop/eclipse/wordspace/TDGameAPP/level/"; //Path for testing on MAC by Osama
	String pathForTesting="../level/";
	Frame frame;
	LevelFile levelFile;
	ActionHandler actionHandler;
	MouseHandler mouseHandler;
	Thread thread;// = new Thread(this);
	static boolean running = false;
	int scene;

	/* Map grid variables */
	private static Image image;
	public static boolean startGame = true;
	static String typeOfOperation = null;
	public static int[][] map;
	public Image[] terrain = new Image[100];
	private String packagename = "com/tdgame";
	public int handXPos = 0;
	public int handYPos = 0;
	static String newFileName;	
	static int valueOfX;
	static int valueOfY;
	public static double width;
	public static double height;	
	String towerImgPath;

	/* Critter Variables */
	CritterWave critterWave;
	public static Image[] crittersImgs = new Image[10];
	public static Critter[] critters;
	public static Critter[] critters2;
	public static boolean isFirst = true;
	boolean allowCritters = false;
	public static int noOfCritters = 0;
	static String waveType="Single";
	public static int critterSpeed = 7;

	/* Tower Variables */
	static public Tower[][] towerMap;
	public int towerWidth = 50;
	public int towerHeight = 50;
	static Tower selectedTower;
	static Tower inHandTower;


	public JButton button;
	public Tower towers[]=new Tower[5];
	public String towerNames[]={"Fire","Laser","Bomber","Tank","Missile"};
	public int towerInHand = 0;
	static String towerType="";
	public static User user;
	static int towerId=0;
	boolean placingTower = false;
	Color selectedTowerColor = null;
	int selectedTowerRange = 0;
	Rectangle repaintMapRectangle;
	Rectangle onMapPropertyRectangle;


	// Table for user specific details
	Object userRowData[][] = { { "Cash","" }};
	static Object columnNam[] = { "User", "Data" };
	JTable userDataTbl = new JTable(userRowData, columnNam);

	JScrollPane userScrollPane = new JScrollPane(userDataTbl);

	// Table for displaying Tower store properties
	Object onMapRowData[][] = { { "Type","" },
			{ "Ammunition", "" },
			{ "Range", "" },
			{ "Cost", "" },
			{ "Rate of Fire", "" },
			{ "Refund Rate", "" },
			{ "Add Ammunition Rate", "" },
			{ "Increase Level Cost", "" },
			{ "Fire Strategy", "" }};
	static Object columnNames[] = { "Properties", "Value" };
	JTable onMapTowerPropTbl = new JTable(onMapRowData, columnNames);

	JScrollPane onMapScrollPane = new JScrollPane(onMapTowerPropTbl);

	// Table for displaying Tower store properties
	static Object offMapRowData[][] = { { "Type","" },
		{ "Ammunition", "" },
		{ "Range", "" },
		{ "Cost", "" },
		{ "Rate of Fire", "" },
		{ "Refund Rate", "" },
		{ "Add Ammunition Rate", "" }};
	static JTable offMapTowerPropTbl = new JTable(offMapRowData, columnNames);
	static JScrollPane offMapScrollPane = new JScrollPane(offMapTowerPropTbl);
	String[] strategyList = {"RANDOM", "NEARESTTOTOWERCRITTER", "NEARESTTOENDPOINTCRITTER", "WEAKESTCRITTER", "STRONGESTCRITTER"};
	JComboBox towerStrategyList;

	int xPosInGridMap;
	int yPosInGridMap;


	/* Instruction Variables */
	static String welcomeMessage = "Welcome to Tower Defence Game";
	static String instructions = "In order to play this game, you need a "
			+"\nmap."
			+"\n\nYou can either create your own map by "
			+"\nselecting CREATE MAP from the menu "
			+"\nor you can load an already existing map "
			+"\nby selecting LOAD MAP from the menu. "
			+"\n\nIf you want to create the map, you need "
			+"\nto save the map before you can start "
			+"\nplaying. "
			+ "\n\nOnce the map is saved, you can buy towers"
			+ "\nfrom the tower store and place them on the"
			+ "\nmap.";

	static Graphics tempGraphics;

	JButton nearestToTowerAttackStrategyButton;
	JButton nearestToEndPointAttackStrategyButton;
	JButton weakestCritterAttackStrategyButton;
	JButton strongestCritterAttackStrategyButton;

	public static final int NEARESTTOTOWERCRITTER = 1;			//nearest critter to the tower 
	public static final int NEARESTTOENDPOINTCRITTER = 2;			//nearest critter to the end point
	public static final int STRONGESTCRITTER = 3;		//Critter with max health------ missile & tank
	public static final int WEAKESTCRITTER = 4;			//Critter with min health ----- laser & fire
	public static final int RANDOMCRITTER = 0;			//random to the tower-------bomber

	public static int attackStrategy = 1;				//this strategy is set as the default strategy


	public static int findEnemyTestCount = 0;
	public static boolean isWaveRunning=false;

	public static SaveXML saveLogXML;

	// Screen constructor
	public Screen(Frame frame) {

		this.frame = frame;	
		actionHandler = new ActionHandler(this, frame);
		onMapPropertyRectangle=new Rectangle(this.frame.getWidth() - 300 ,0,300,400);
		critterWave=new CritterWave();
		/* set the image */
		image = new ImageIcon("../res/TowerDefense.png").getImage();



	}	

	/** 
	 *  This method is called when the user wants to create a map.
	 *  
	 *  It would ask the user to enter dimensions of the map and the filename with which he wants to save the map.
	 */
	public void createMap() {

		typeOfOperation = "createMap";
		String mapDimensions = actionHandler.askUserToEnterTheDimensions();
		
		
		
		if(mapDimensions != null) {
			String []splitDimensions = mapDimensions.split(" ");
			valueOfX = Integer.parseInt(splitDimensions[0]);
			valueOfY = Integer.parseInt(splitDimensions[1]);

			map = new int[valueOfX][valueOfY];

			mouseHandler = new MouseHandler(this, valueOfX, valueOfY, true);

			newFileName = actionHandler.saveMapByName();
			newFileName=newFileName+".xml";
			instructions = "Please select a start point!!";

			addingMouseListener(typeOfOperation);			

			//			towerMap = new Tower[valueOfX][valueOfY];

			loadGame();

			towerMap = new Tower[valueOfX][valueOfY];	
			startGame(newFileName, typeOfOperation, user);
			
			//ImageIcon pic = new ImageIcon("grass.png");
			for(int i=0;i<5;i++){
				towers[i] = TowerFactory.getTower(towerNames[i]);
				((AbstractButton) towers[i]).setText(towerNames[i]);
				this.add((Component) towers[i]);
				((AbstractButton) towers[i]).addActionListener(actionHandler);
				((Component) towers[i]).addMouseListener(actionHandler);
				((Component) towers[i]).addMouseMotionListener(actionHandler);
			}
			Rectangle onMapPropertyRectangle=new Rectangle(this.frame.getWidth() - 300 ,0,300,400);
			repaint(onMapPropertyRectangle);

			thread = new Thread(this);

			thread.start();
		}
	}


	/** 
	 *  This method is called when the user wants to load an existing map
	 *  
	 *  A list of existing files will be displayed and the user will chose the files to be loaded as per his wish
	 */
	public void loadMap() {

		typeOfOperation = "loadMap";
		newFileName = actionHandler.loadExistingMap();

		if(newFileName != null) {
			ReadXML readXML = new ReadXML();

			// Based upon the file to be loaded, get the number of rows and columns for loading map
			String rows_cols = readXML.getLengthOfExistingMap(newFileName);

			valueOfX = Integer.parseInt(rows_cols.split("_")[1]);
			valueOfY = Integer.parseInt(rows_cols.split("_")[0]);				

			mouseHandler = new MouseHandler(this, valueOfX, valueOfY, true);
			MouseHandler.mapCompleted = true;

			addingMouseListener(typeOfOperation);			

			loadGame();
			//			towerMap=null;
			towerMap = new Tower[valueOfX][valueOfY];	
			frame.getContentPane().validate();
			startGame(newFileName, typeOfOperation, user);
			//ImageIcon pic = new ImageIcon("grass.png");

			for(int i=0;i<5;i++){
				towers[i] = TowerFactory.getTower(towerNames[i]);
				((AbstractButton) towers[i]).setText(towerNames[i]);
				this.add((Component) towers[i]);
				((AbstractButton) towers[i]).addActionListener(actionHandler);
				((Component) towers[i]).addMouseListener(actionHandler);
				((Component) towers[i]).addMouseMotionListener(actionHandler);
			}

			instructions = "Map loaded!";

			repaint(onMapPropertyRectangle);

			thread = new Thread(this);

			thread.start();
		}
	}

	/**
	 * Adding mouseListerner appropriately in order to avoid multiple mouse listeners added to the same object
	 * 
	 * @param typeOfOperation2 Load map or Create map
	 */
	private void addingMouseListener(String typeOfOperation2) {

		if(typeOfOperation2.equals("loadMap")) {			
			if(this.frame.getMouseListeners().length > 0) {
				this.frame.removeMouseListener(this.frame.getMouseListeners()[0]);
			}
			this.frame.addMouseListener(mouseHandler);
		}
		else {
			if(this.frame.getMouseListeners().length > 0) {
				this.frame.removeMouseListener(this.frame.getMouseListeners()[0]);
			}
			this.frame.addMouseMotionListener(mouseHandler);
			this.frame.addMouseListener(mouseHandler);
		}

	}

	/**
	 * This method will take care of saving the file in XML format
	 * 
	 * @throws FileNotFoundException
	 */
	protected void saveMap() throws FileNotFoundException{

		SaveXML saveXML = new SaveXML(this, newFileName);

		saveXML.createXML();				
	}

	/**
	 * This methods is used to print instructions on the screen
	 * 
	 * @param g Graphics object
	 * @param text Instructions to be displayed on the screen
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	/*private void clearRect(Graphics g) {
		g.clearRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
	}*/


	/**
	 * This method is responsible for displaying everything on the screen including the grid, tower store, 
	 * tower characteristics etc.
	 * 
	 * @param g Graphics object
	 */
	@Override
	public void paintComponent(Graphics g)
	{	
		tempGraphics = g;
		super.paintComponent(g);
		try 
		{

			g.clearRect(0, 0, this.frame.getWidth(), this.frame.getHeight());

			// Background
			g.setColor(Color.WHITE);

			g.fillRect(0, 0, this.frame.getWidth() - 300, this.frame.getHeight() - 200);		

			// Instructions
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.BOLD, 17));
			g.drawString(welcomeMessage, this.frame.getWidth() - 300 , 25);

			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 14));
			drawString(g,instructions, this.frame.getWidth() - 300 , 50);

			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 14));
			drawString(g,"Tower Store", (int)this.width * 5 + 80, (frame.getHeight() -(frame.getHeight() -(int)this.height * 10) + 100));


			// Main screen image
			if(startGame) {
				g.drawImage(image, 50, 0, null);
			}

			// Grid			
			g.setColor(Color.GRAY);			

			// calculate the dimensions of the tile dynamically by getting the width and height of the screen/frame
			double width1 = this.frame.getWidth()*10000 / (valueOfX * 50);
			double width2 = width1 / 10000;
			double width3 = this.frame.getWidth() / width2;
			width = width3 / valueOfX;

			double height1 = this.frame.getHeight()*10000 / (valueOfY * 50);
			double height2 = height1 / 10000;
			double height3 = this.frame.getHeight() / height2;
			height = height3 / valueOfY;

			// create map based upon the dimensions given by the user			
			for(int row=0; row < map.length; row++) {
				for(int col=0; col < map[0].length; col++) {
					// draw image
					if(map[row][col] == 0) {
						g.drawImage(terrain[map[row][col]], ((int) width + (row * (int) width)), ((int) height + (col * (int) height)) - (int)height , (int) width, (int) height, null);
					}		
					// if the map reads 1 for some array value, it means that is the starting point
					else if(map[row][col] == 1) {
						g.setColor(Color.BLUE);
						//g.drawImage(terrain[map[row][col]], ((int) width + (row * (int) width)), ((int) height + (col * (int) height)) - 50, (int) width, (int) height, null);
						g.fillRect((int) width + (row * (int) width), (col * (int) height), (int) width, (int) height);
					}
					// if the map reads 2 for some array value, it means that is the ending point
					else if(map[row][col] == 2) {
						g.setColor(Color.RED);
						//g.drawImage(terrain[map[row][col]], ((int) width + (row * (int) width)), ((int) height + (col * (int) height)) - 50, (int) width, (int) height, null);
						g.fillRect((int) width + (row * (int) width), (col * (int) height), (int) width, (int) height);
					}
					// if the map reads 3 for some array value, it means that is the path
					else if(map[row][col] == 3) {
						g.setColor(Color.GRAY);
						//g.drawImage(terrain[map[row][col]], ((int) width + (row * (int) width)), ((int) height + (col * (int) height)) - 50, (int) width, (int) height, null);
						g.fillRect((int) width + (row * (int) width), (col * (int) height), (int) width, (int) height);

					}
				}
			}	

			//this conditions only get true if map on screen is complete
			//if the map is complete then only it shows you tower property tables
			if(MouseHandler.mapCompleted) {

				//Sell tower button 			
				JButton sellTower=new JButton("Sell Tower");
				sellTower.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//add fund to player money and delete tower from map by placing null in towerMap array
						//also clears onMapTowerPropTbl
						user.player.money+=selectedTower.getRefundRate();
						towerMap[selectedTower.getXPosInTowerMap()][selectedTower.getYPosInTowerMap()]=null;
						Point location=((Component) selectedTower).getLocation();
						onMapTowerPropTbl.setValueAt("", 0, 1);
						onMapTowerPropTbl.setValueAt("", 1, 1);
						onMapTowerPropTbl.setValueAt("", 2, 1);
						onMapTowerPropTbl.setValueAt("", 3, 1);
						onMapTowerPropTbl.setValueAt("", 4, 1);
						onMapTowerPropTbl.setValueAt("", 5, 1);
						onMapTowerPropTbl.setValueAt("", 6, 1);

						xPosInGridMap=location.x;
						yPosInGridMap=location.y;
						if(isWaveRunning)
						{
							saveLogXML.writeLog("Wave_Tower", selectedTower.type, "User sold "+selectedTower.type);
							saveLogXML.writeLog("Wave_User","User", "User sold "+selectedTower.type+" Current Money "+user.player.money);
						}
						else
						{
							saveLogXML.writeLog("Tower", selectedTower.type, "User sold "+selectedTower.type);
							saveLogXML.writeLog("User","User", "User sold "+selectedTower.type+" Current Money "+user.player.money);
						}
						remove((Component) selectedTower);
						revalidate();
						//frame.getContentPane().validate();
					}
				});
				frame.add(sellTower);
				sellTower.setBounds(this.frame.getWidth() - 6*(int)width , (int)(5.5*height), 2*(int)width, (int)(height/2));

				//add ammunition button
				JButton addAmmunition=new JButton("Add Ammunition");
				addAmmunition.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//first check money to buy ammunition if money is available decrease it from player money and
						//increase ammunition
						try {
							if(user.player.money>=selectedTower.getCostToAddAmmunition()){
								user.player.money-=selectedTower.getCostToAddAmmunition();
								selectedTower.increaseAmmunition(selectedTower.getActualAmmunition());
								onMapTowerPropTbl.setValueAt(selectedTower.getAmmunition(), 1, 1);
								if(isWaveRunning)
								{
									saveLogXML.writeLog("Wave_Tower", selectedTower.type, selectedTower.type+" ammunition increased by "+selectedTower.getActualAmmunition());
									saveLogXML.writeLog("Wave_User","User", "User buy ammunition for  "+selectedTower.type+" Current Money "+user.player.money);
								}
								else
								{
									saveLogXML.writeLog("Tower", selectedTower.type, selectedTower.type+" ammunition increased by "+selectedTower.getActualAmmunition());
									saveLogXML.writeLog("User","User", "User buy ammunition for  "+selectedTower.type+" Current Money "+user.player.money);
								}
							}
							else {
								Object[] options = { "OK" };
								int neededMoney=selectedTower.getCostToAddAmmunition()-user.player.money;
								JOptionPane.showOptionDialog(null, "Not Enough Money To Buy Ammunition!! You need "+neededMoney+" more dollar to add ammunition", "Warning",
										JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
										null, options, options[0]);
								if(isWaveRunning)
									saveLogXML.writeLog("Wave_Tower", selectedTower.type, selectedTower.type+" ammunition increase failed due to insuffiecient fund");
								else
									saveLogXML.writeLog("Tower", selectedTower.type, selectedTower.type+" ammunition increase failed due to insuffiecient fund");
							}
						} catch (Exception e1) {
						}
					}
				});
				frame.add(addAmmunition);
				addAmmunition.setBounds(this.frame.getWidth() - 4*(int)width , (int)(5.5*height), 3*(int)width, (int)(height/2));

				//Increase Tower Level		
				JButton increaseLevel = new JButton("Increase Level");
				increaseLevel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//first check money to buy ammunition if money is available decrease it from player money and
						//increase ammunition
						try {
							if(user.player.money>=selectedTower.getCostToIncreaseLevel()){
								user.player.money-=selectedTower.getCostToIncreaseLevel();
								selectedTower.increaseLevel();
								onMapTowerPropTbl.setValueAt(selectedTower.getRange(), 2, 1);
								onMapTowerPropTbl.setValueAt(selectedTower.getRateOfFire(), 4, 1);
								onMapTowerPropTbl.setValueAt(selectedTower.getTowerLevel(), 7, 1);
								if(isWaveRunning)
								{
									saveLogXML.writeLog("Wave_Tower", selectedTower.type, selectedTower.type+" tower level increased");
									saveLogXML.writeLog("Wave_User","User", "User increased level for  "+selectedTower.type+" Current Money "+user.player.money);
								}
								else
								{
									saveLogXML.writeLog("Tower", selectedTower.type, selectedTower.type+" tower level increased");
									saveLogXML.writeLog("User","User", "User increased level for  "+selectedTower.type+" Current Money "+user.player.money);
								}
							}
							else {
								Object[] options = { "OK" };
								int neededMoney=selectedTower.getCostToIncreaseLevel()-user.player.money;
								JOptionPane.showOptionDialog(null, "Not Enough Money To Increase Level!! You need "+neededMoney+" more dollars", "Warning",
										JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
										null, options, options[0]);
								if(isWaveRunning)
									saveLogXML.writeLog("Wave_Tower", selectedTower.type, selectedTower.type+" level increase failed due to insuffiecient fund");
								else
									saveLogXML.writeLog("Tower", selectedTower.type, selectedTower.type+" level increase failed due to insuffiecient fund");
							}
						} catch (Exception e1) {
						}
					}
				});
				frame.add(increaseLevel);
				increaseLevel.setBounds(this.frame.getWidth() - 6*(int)width , (int)(6* height), 3*(int)width, (int)(height/2));

				//Send critters on map
				JButton sendCritters=new JButton("Send Critters");
				sendCritters.addActionListener(new ActionListener() {
					// On click generate more amount of new critters 
					@Override
					public void actionPerformed(ActionEvent e) {	
						isWaveRunning=true;
						if(waveType=="Single"){
							critterWave.setStrategy(new SingleCritters());
							critterWave.startWave();
						}
						else {
							critterWave.setStrategy(new DoubleCritters());
							critterWave.startWave();
						}
						saveLogXML.writeLog("Wave",waveType+" "+noOfCritters , waveType+" wave of "+noOfCritters+" critters started");

					}
				});
				frame.add(sendCritters);
				sendCritters.setBounds(this.frame.getWidth() - 6*(int)width , (int) (7.5*height), 3*(int)width, (int)(height/2));

				//adding onMapTowerPropTable
				frame.add(onMapScrollPane);
				drawString(g,"Active Tower Properties", this.frame.getWidth() - 6*(int)width ,(int)(1.5*height));
				onMapScrollPane.setBounds(this.frame.getWidth() - 6*(int)width , (int)(2.5*height), 5*(int)width, 3*(int)height);

				//adding user details table
				frame.add(userScrollPane);
				userScrollPane.setBounds(this.frame.getWidth() - 6*(int)width , (int)(6.5*height), 5*(int)width, (int)height);
				userDataTbl.setValueAt(user.player.money, 0, 1);

				//adding offMapTowerPropTable
				frame.add(offMapScrollPane);
				offMapScrollPane.setBounds(this.frame.getWidth() - 6*(int)width , 10*(int)height, 5*(int)width, 3*(int)height);


				towerStrategyList = new JComboBox(strategyList);
				frame.add(towerStrategyList);
				if(selectedTower!=null){
					towerStrategyList.setSelectedItem(selectedTower.strategyName);
				}
				else{
					towerStrategyList.setSelectedIndex(0);
				}

				towerStrategyList.setBounds(this.frame.getWidth() - 6*(int)width , (int)(8.5*height), 5*(int)width, (int)(height/2) );
				towerStrategyList.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getStateChange()==ItemEvent.SELECTED)
						{
							towerStrategyList.setSelectedItem(e.getItem());
							selectedTower.chngStrategy(e.getItem().toString());
							onMapTowerPropTbl.setValueAt(selectedTower.getTowerStrategy(), 8, 1);
							if(isWaveRunning)
								saveLogXML.writeLog("Wave_Tower", selectedTower.type, selectedTower.type+" fire strategy changed to "+selectedTower.strategyName);
							else
								saveLogXML.writeLog("Tower", selectedTower.type, selectedTower.type+" fire strategy changed to "+selectedTower.strategyName);
						}
					}
				});
				//Create towers on the grid
				for(int x=0; x<valueOfX; x++){
					for(int y=0; y<valueOfY; y++){
						if(towerMap[x][y] != null){
							//final 
							final Tower towerOnMapBtn=towerMap[x][y];
							this.add((Component) towerOnMapBtn);
							((AbstractButton) towerOnMapBtn).addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									//set the value onMapTowerPropTbl if that tower is pressed
									selectedTower=towerOnMapBtn;
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getType(), 0, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getAmmunition(), 1, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getRange(), 2, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getCost(), 3, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getRateOfFire(), 4, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getRefundRate(), 5, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getCostToAddAmmunition(), 6, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getTowerLevel(), 7, 1);
									onMapTowerPropTbl.setValueAt(towerOnMapBtn.getTowerStrategy(), 8, 1);
									towerStrategyList.setSelectedIndex(towerOnMapBtn.attackStrategy);
								}
							});
							((Component) towerOnMapBtn).setBounds(((int)width+x*(int)width), ((int)height+y*(int)height)-(int)this.height, (int)width, (int)height);
							//g.drawOval((int)(x*width)-25, (int)(y*height)-75, (int)(2*towerOnMapBtn.range*width), (int)(2*towerOnMapBtn.range*height));
							int ovalWidth = (int)(this.selectedTowerRange*this.width*2);
							int ovalHeight = (int)(this.selectedTowerRange*this.height*2);

						}
					}
				}


				// To draw all critters on screen
				if (critters != null)
				{	
					isWaveRunning = false;
					for(int i=0;i<critters.length;i++)
					{
						if(critters[i] != null){
							if(critters[i].inGame)
							{
								isWaveRunning = true;
								if(critters[i].towerFixed){
									g.drawLine((int)(critters[i].x)+50, (int)(critters[i].y)+25,(50 + (critters[i].towerX * 50) + (int)(50/2)), ((critters[i].towerY * 50) + (int)(50/2)));
									
									
									if(critters[i].getAttackTime() < critters[i].getMaxAttackTime()){
										g.drawLine((int)(critters[i].x)+50, (int)(critters[i].y)+25,(50 + (critters[i].towerX * 50) + (int)(50/2)), ((critters[i].towerY * 50) + (int)(50/2)));
										critters[i].setAttackTime(critters[i].getAttackTime() + 1);
									}
									else{
										critters[i].setAttackTime(0);
									}

									if (critters[i].showFire){
										critters[i].draw(g, 1);
									}	
									else if(critters[i].splash)
										critters[i].draw(g, 2);
									else{
										critters[i].draw(g, 0);
									}	
									g.setColor(Color.MAGENTA);
									if (critters[i].slowdown)
									{
										if (critters[i].moveSpeed <= critterSpeed)
										{	
											critters[i].moveSpeed += 2;
										}
									}

									//critters[i].towerFixed = false;
								}
								else
								{
									if(critters[i].damageTime > 0){
										critters[i].damageTime--;
										if(critters[i].showFire){
											critters[i].draw(g, 1);
										} else if(critters[i].splash){
											critters[i].draw(g, 2);
										}else{
											critters[i].draw(g,0);
										}

									} else {
										critters[i].draw(g, 0);
										critters[i].moveSpeed = critterSpeed;
										critters[i].showFire = false;
										critters[i].splash = false;
									}
								}
							}
						}	

						if(waveType=="Double")
						{
							if(critters2[i].inGame){
								critters2[i].draw(g,0);
								if(critters[i].towerFixed){
									g.setColor(Color.MAGENTA);
									g.drawLine( (int)(critters[i].x)+50, (int)(critters[i].y)+25,(50 + (critters[i].towerX * 50) + (int)(50/2)), ((critters[i].towerY * 50) + (int)(50/2)));
								}
							}
						}
					}
				}

			}

			// List of available towers	
			for(int i=0;i < 5; i++){
				for(int j=0; j < 1 ; j++) {
					((Component) towers[i]).setBounds((int)this.width * 5 + (i * 50), (frame.getHeight() -(frame.getHeight() -(int)this.height * 10)) + (int)this.height * (j+1), (int) width, (int) height);
				}
			}

			//show tower while placing (Special effect)
			if(this.placingTower){// && Tower.towerList[towerInHand - 1] != null){
				//g.drawRect((int)this.handXPos - (int)(this.width) - (int)(this.width/2), (int)this.handYPos - ((int)this.towerWidth) - ((int)this.height/2), (int)this.width, (int)this.height);
				int ovalWidth = (int)(this.selectedTowerRange*this.width*2);
				int ovalHeight = (int)(this.selectedTowerRange*this.height*2);
				g.setColor(this.selectedTowerColor);
				//g.fillRect((int)this.handXPos - (int)(this.width/2), (int)this.handYPos - ((int)this.towerWidth) - ((int)this.height/2), (int)this.width, (int)this.height);
				g.drawImage(new ImageIcon(this.towerImgPath).getImage(), (int)this.handXPos - (int)(this.width/2), 
						(int)this.handYPos - ((int)this.towerWidth) - ((int)this.height/2), 
						(int)this.width, (int)this.height,null);

				//g.drawOval((int)this.handXPos - ((int)(2*this.towerWidth)), (int)this.handYPos - ((int)(3*this.towerWidth)), );
				g.drawOval((int)this.handXPos - ((int)(ovalWidth/2)), ((int)this.handYPos - ((int)((ovalHeight/2))) - (int)this.height), ovalWidth, ovalHeight);
				g.setColor(new Color(64, 64, 64, 64));
				g.fillOval((int)this.handXPos - ((int)(ovalWidth/2)), ((int)this.handYPos - ((int)((ovalHeight/2))) - (int)this.height) , ovalWidth, ovalHeight);
			}

		} catch (Exception e) {

		}
	}

	public int generationTime = (500 / 10)*critterSpeed, generationFrame = 0;

	/* To Generate critters */ 
	public void critterGenerator()
	{
		if(generationFrame >= generationTime)
		{
			if(critters != null){
				for (int i = 0;i < critters.length;i++)
				{
					if(critters[i] != null){
						if(!critters[i].inGame && !critters[i].duplicate) {
							critters[i].createCritter(0);
							if(waveType=="Single")
								break;
						}
						if(waveType=="Double")
						{
							if(!critters2[i].inGame && !critters2[i].duplicate) {
								critters2[i].createCritter(0);
								break;
							}
						}
					}
				}
			}
			generationFrame = 0;
		}
		else
		{
			generationFrame += 1;
		}
	}

	/**
	 * Called for creating a thread that would execute separately from the main thread 
	 */
	@Override
	public void run() {	

		long lastFrame = System.currentTimeMillis();

		int frames = 0;
		int speed1, speed2;
		running = true;
		scene=0;	

		// the map grid would be refreshed every 2 ms so that we don't get the flickering effect
		while(running) {
			frames++;
			if(!isFirst)
			{
				critterGenerator();
				if(critters != null){
					for(int i = 0;i < critters.length;i++)
					{
						if(critters[i] != null){
							if(critters[i].inGame)
							{
								speed1 = critters[i].moveSpeed;
								critters[i].physics((int)(((float)speed1/2)*100)+50-(speed1*3),(int) ((float)(2/10)*speed1),(int) ((float)(49/10)*speed1));
							}
							if(waveType=="Double")
							{
								if(critters2[i].inGame)
								{
									speed2 = critters2[i].moveSpeed;
									critters2[i].physics((int)(((float)speed2/2)*100)+50,(int) ((float)(1/10)*speed2),(int) ((float)(6/10)*speed2));
								}
							}
						}
					}
				}
			}

			if(System.currentTimeMillis()-1000 >= lastFrame){
				frames=0;
				lastFrame=System.currentTimeMillis();
			}

			repaintMapRectangle=new Rectangle((int)width,0,frame.getWidth()-350,getHeight());
			//Rectangle repaintRectangle=new Rectangle(50,0,valueOfX*50,getHeight());
			// to draw stuff all the time on the screen : goes around 2 millions frames per second. Which is of no use.


			repaint(repaintMapRectangle);

			updateMap();

			try {
				Thread.sleep(2);
			} catch (Exception e) {}
		}				
	}

	public void updateMap() {
		updateTowers();
		//If critter health is 0 then destroy that critter
		updateCritters();
	}

	private void updateCritters() {
		if(critters != null){
			for(int i=0; i<critters.length; i++){
				if(critters[i] != null){
					critters[i] = critters[i].update();
				}
			}
		}
	}

	private void updateTowers() {
		for(int x=0; x<valueOfX; x++){
			for(int y=0; y<valueOfY; y++){
				if(towerMap[x][y] != null){
					//System.out.println("tower :\nX\tY\n" + x + "\t" + y);
					towerAttack(x, y);
				}
			}
		}
	}


	private void towerAttack(int x, int y) {
		//IF the current tower does not have a critter-enemy then find next critter to shoot
		if(towerMap[x][y].getTargetCritter() == null){
			//System.out.println("this.towerMap[x][y].getAttackDelay() > this.towerMap[x][y].getMaxAttackDelay()\n" + this.towerMap[x][y].getAttackDelay() + " > " +this.towerMap[x][y].getMaxAttackDelay() + " = " + (this.towerMap[x][y].getAttackDelay() > this.towerMap[x][y].getMaxAttackDelay()));
			if(towerMap[x][y].getAttackDelay() > towerMap[x][y].getMaxAttackDelay()){
				//System.out.println("Finding enemy...calling findenemytoshoot...\nnumber of enemies=\t"+critters.length);
				Critter currentEnemy = towerMap[x][y].findTargetCritter(critters, x, y);
				if(currentEnemy != null){
					towerMap[x][y].setTargetCritter(currentEnemy);
					towerMap[x][y].towerAttack(x, y, currentEnemy);
					//towerMap[x][y].setAttackTime(0);
					towerMap[x][y].setAttackDelay(0);
					//towerMap[x][y].splashEffect){
					if(currentEnemy.splash){
						//System.exit(0);
						int neighbouringBoxUP = 999;
						int neighbouringBoxRight = 999;
						int neighbouringBoxDown = 999;
						int neighbouringBoxLeft = 999;

						String upSearchKey = (currentEnemy.x/50) + "_" + (currentEnemy.y/50);
						String downSearchKey = ((currentEnemy.x/50)) + "_" + ((currentEnemy.y/50) + 2);
						String leftSearchKey = ((currentEnemy.x/50) - 1) + "_" + ((currentEnemy.y/50) + 1);
						String rightSearchKey = ((currentEnemy.x/50) + 1) + "_" + ((currentEnemy.y/50) + 1);

						if(MouseHandler.boxPositionPathNumberMap.containsKey(upSearchKey)){
							neighbouringBoxUP = MouseHandler.boxPositionPathNumberMap.get(upSearchKey);
						}
						if(MouseHandler.boxPositionPathNumberMap.containsKey(downSearchKey)){
							neighbouringBoxUP = MouseHandler.boxPositionPathNumberMap.get(downSearchKey);
						}
						if(MouseHandler.boxPositionPathNumberMap.containsKey(leftSearchKey)){
							neighbouringBoxUP = MouseHandler.boxPositionPathNumberMap.get(leftSearchKey);
						}
						if(MouseHandler.boxPositionPathNumberMap.containsKey(rightSearchKey)){
							neighbouringBoxUP = MouseHandler.boxPositionPathNumberMap.get(rightSearchKey);
						}

						for(int i=0; i<critters.length; i++){
							if(critters[i] != null && !critters[i].towerFixed){
								System.out.println("Critter positions:\nx_y\t" + critters[i].x/50 + "_" + (((critters[i].y)/50) + 1));
								String searchKey = (critters[i].x/50) + "_" + ((critters[i].y/50) + 1);
								System.out.println("At index\t" + i);
								System.out.println("Before checking adj critter pos..searckey\t" + searchKey);
								int currentEnemyBoxNumber = 999;
								if(MouseHandler.boxPositionPathNumberMap.containsKey(searchKey)){
									currentEnemyBoxNumber = MouseHandler.boxPositionPathNumberMap.get(searchKey);
								}
								if(currentEnemyBoxNumber == neighbouringBoxUP || currentEnemyBoxNumber == neighbouringBoxDown || currentEnemyBoxNumber == neighbouringBoxLeft || currentEnemyBoxNumber == neighbouringBoxRight){
									towerMap[x][y].towerAttack(x, y, critters[i]);
								}
							}
						}
					}
				}
			} 
			else{
				towerMap[x][y].setAttackDelay(towerMap[x][y].getAttackDelay() + 1);
			}

		}
		//There is a critter to shoot and to show the attack, we need to keep the bullet visible until attackTime > maxAttackTime
		else{

			towerMap[x][y].setTargetCritter(null);
		}
	}

	/**
	 * This method will create a new User object and load the grass image into the array named terrain[]
	 */ 
	public void loadGame() {

		user = new User(this);
		levelFile = new LevelFile(valueOfX, valueOfY);

		ClassLoader cl = this.getClass().getClassLoader();

		// To load the image in the Image array
		for(int y=0; y < 10; y++) {
			for(int x=0; x < 10; x++) {

				terrain[x + (y * 10)] = new ImageIcon(cl.getResource(packagename  + "/grass2.png")).getImage();
				terrain[x + (y * 10)] = createImage(new FilteredImageSource( terrain[x + (y * 10)].getSource(), new CropImageFilter(x*(int)towerWidth, y*(int)towerHeight, (int)towerWidth, (int)towerHeight)));
			}
		}

		running = true;
	}

	/**
	 *  This method is responsible for loading the XML file to the 2D array
	 *  
	 * @param fileName Name of the file to be loaded
	 * @param typeOfOperation Load map or Create map
	 * @param user User object
	 */

	public void startGame(String fileName, String typeOfOperation, User user) {
		saveLogXML = new SaveXML(fileName);
		saveLogXML.writeLog("Map", "Create Map", "User started new map creation");
		
		user.createPlayer();
		saveLogXML.writeLog("User","User", "User created with "+user.startingCash+" money");
		if(typeOfOperation=="createMap")
			fileName="Base.xml";
		levelFile.readAndLoadMap(fileName, this, typeOfOperation);		
		this.scene = 1;	// game level 1
	}

	/**
	 * This method is called when user drags a tower and leaves the mouse button to place tower on the map
	 * 
	 * @param xPos x-position of the tile
	 * @param yPos y-position of the tile
	 * @param new_towerText type of tower
	 * @return boolean returns True, if the tower was successfully placed, else, returns False
	 */
	public boolean placeTower(int xPos, int yPos,String new_towerText){

		if(xPos > 0 && xPos <= valueOfX && yPos >0 && yPos <= valueOfY){

			xPos -= 1;
			yPos -= 1;
			if(map[xPos][yPos] == 0){

				if(towerMap[xPos][yPos] == null){
					if(inHandTower.getCost() > user.player.money){

						//Display popup												
						Object[] options = { "OK" };
						JOptionPane.showOptionDialog(null, "Insufficient Funds. You need "+(inHandTower.getCost()- user.player.money)+" more dollar", "Warning",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
								null, options, options[0]);

						return false;
					}else{
						user.player.money -= inHandTower.getCost();
						towerMap[xPos][yPos] =TowerFactory.getTower(new_towerText);
						//						System.out.println(xPos+" " +yPos);
						//						System.exit(0);
						towerMap[xPos][yPos].setPosition(xPos, yPos);
						//towerMap[xPos][yPos].addListener(this);
						//towerMap[xPos][yPos].setObserver(this);
						//towerMap[xPos][yPos].yPosInTowerMap=yPos;
						towerId++;
						if(isWaveRunning)
							saveLogXML.writeLog("Wave_Tower", towerMap[xPos][yPos].type, towerMap[xPos][yPos].type+" Placed On Map");
						else
							saveLogXML.writeLog("Tower", towerMap[xPos][yPos].type, towerMap[xPos][yPos].type+" Placed On Map");
						return true;
					}
				}else{
					Object[] options = { "OK" };
					JOptionPane.showOptionDialog(null, "Tower Already Present At This Position!!", "Warning",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
							null, options, options[0]);
					return false;
				}
			}else{
				Object[] options = { "OK" };
				JOptionPane.showOptionDialog(null, "Tower Cannot Be Placed On The Path!!", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[0]);
				return false;
			}
		}else{
			if(placingTower){
				Object[] options = { "OK" };
				JOptionPane.showOptionDialog(null, "Tower Cannot Be Placed Outside The Map Boundary!!", "Warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[0]);
			}
			return false;
		}
	}

	/**
	 * This inner class is responsible handling some of the actions triggered by a mouse click, mainly changing the values
	 * of the 2D array
	 * 
	 * @author Team 2
	 *
	 */
	// Class to handle mouse events
	public class MouseHeld {

		boolean mouseDown;
		boolean createPath;
		boolean placecTower;

		// This method gets the coordinates of the mouse pointer on mouse movement
		public void mouseMoved(MouseEvent e) {
			handXPos = e.getXOnScreen();
			handYPos = e.getYOnScreen();
		}

		/**
		 * This method is responsible for setting the value of the array based upon the start, end and path points
		 * 
		 * @param e MouseEvent
		 * @param count To differentiate between start, end and path point
		 * @param boxNumberX Array index for X
		 * @param boxNumberY Array index for Y
		 */
		public void mouseDown(MouseEvent e, int count, int boxNumberX, int boxNumberY) {
			mouseDown = true;

			// 1 means that its the starting point - paint it blue
			if(count == 1) {
				if(map[boxNumberX][boxNumberY] == 0) {
					map[boxNumberX][boxNumberY] = 1;
				}
				else if(map[boxNumberX][boxNumberY] == 1) {
					map[boxNumberX][boxNumberY] = 0;
				}
				instructions = "Please select an end point!!"; 
			}
			// 2 means that its the ending point - paint it red
			else if(count == 2) {
				if(map[boxNumberX][boxNumberY] == 0) {
					map[boxNumberX][boxNumberY] = 2;
				}
				else if(map[boxNumberX][boxNumberY] == 2) {
					map[boxNumberX][boxNumberY] = 0;
				}
				instructions = "Select a path from start to end point!!";
			}
			// 3 means that this will be the path - paint it gray
			else {
				if(map[boxNumberX][boxNumberY] == 0) {
					map[boxNumberX][boxNumberY] = 3;
				}
				else if(map[boxNumberX][boxNumberY] == 3) {
					map[boxNumberX][boxNumberY] = 0;
				}
			}
			repaint(onMapPropertyRectangle);
		}

		// This method is handles the saving of file on map completion
		public String pathCompleted(Screen screen) {

			int userReply = actionHandler.pathCompleted();

			if(userReply == JOptionPane.YES_OPTION) {

				try {
					try {
						saveMap();
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}
					instructions = "Map Saved..!!";
					typeOfOperation = "saveMap";
					return "YES";
				} catch (NullPointerException e) {
					e.getStackTrace();
					//System.exit(0);
				}
			}
			else {
				return "NO";
			}
			return null;
		}

		// This method makes sure if the map is incomplete before saving it
		public void incompleteMap() {
			actionHandler.pathIncomplete();			
		}
	}
}

