package com.tdgame;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

/**
 * MenuHandler class handles the Menu events and takes appropriate action based upon the user choice
 * 
 * @author Team 2
 * @version $revision
 */
//
public class MenuHandler implements ActionListener {
	
	Screen screen;
	Frame frame;
	JButton myButton; 
	static boolean mapLoadedorCreated = false;
	
	MenuHandler() {}
	
	MenuHandler(Frame frame) { 

		this.frame = frame;		
		screen = new Screen(frame); 
		this.frame.getContentPane().add(screen); //adding screen to the frame
		this.frame.getContentPane().validate(); //used to validate the screen that calls the paintComponent for JPanel validation.
	}
	

	// this method catches the menu events and handles them accordingly
	@Override
	public void actionPerformed(ActionEvent e) {

		ActionHandler actionHandler = new ActionHandler();
		MouseHandler mouseHandler = new MouseHandler(screen);
		
		myButton = (JButton) e.getSource(); 
	    
		String selectedOption = myButton.getText();
		
		//checking the option clicked by the user and performing actions on its basis
		if(selectedOption.equalsIgnoreCase("Create Map")) {
			Screen.startGame = false;
			ActionHandler.mapCreated = false;
			MouseHandler.count=0;
			Screen.running = false;
			mapLoadedorCreated= true;
			screen.createMap();
		} else if(selectedOption.equalsIgnoreCase("Load Map")) {
			Screen.startGame = false;
			Screen.running = false;
			mapLoadedorCreated= true;
			ActionHandler.mapCreated = true;
			screen.loadMap();
		} else if(selectedOption.equalsIgnoreCase("Save Map")) {
			mouseHandler.saveMapByMenu();
		} else if(selectedOption.equalsIgnoreCase("Instructions")) {
			actionHandler.menuInstructions();
		} else if(selectedOption.equalsIgnoreCase("Edit Map")) {
			actionHandler.editMap();
			MouseHandler.saveEditedMap = true;
			mouseHandler.mapReadyForEditing();
		}
		else if(selectedOption.equalsIgnoreCase("Save Game")) {
			
			if(mapLoadedorCreated) {
				MouseHandler.saveGame = true;				
			}
			
			mouseHandler.saveGame();
			MouseHandler.saveGame = false;
			
		}
		else if(selectedOption.equalsIgnoreCase("Log")) {
			ReadXML readXML=new ReadXML();
			String logFileName = actionHandler.loadLogFiles();
			System.out.println(logFileName);
			

			if(logFileName !=null) {
				
				JFrame frame = new JFrame("Log Frame");
				JTable table = new JTable(new DefaultTableModel(new Object[]{"Time Stamp", "Log Type","Element","Msg"}, 0));
				
				JScrollPane scrollPane = new JScrollPane(table);
				
				
				//frame.add(table.getTableHeader(), BorderLayout.PAGE_START);
				//frame.add(table, BorderLayout.CENTER);
				readXML.readLog("Tower","Tower", table);
				if(logFileName!=null){
					switch (logFileName) {
					case "Individual Tower Log":
						readXML.readLog("Tower","Fire", table);
						readXML.readLog("Tower","Laser", table);
						readXML.readLog("Tower","Bomber", table);
						readXML.readLog("Tower","Tank", table);
						readXML.readLog("Tower","Missile", table);
						break;
					case "Collective Tower Log":
						readXML.readLog("Tower","All", table);
						break;
					case "Wave Log":
						readXML.readLog("Wave","All", table);
						break;
					case "User Log":
						readXML.readLog("User","All", table);
						break;
					case "Map Log":
						readXML.readLog("Map","Map", table);
						break;
					case "Global Log":
						readXML.readLog("Global","All", table);
						break;
					default:
						break;
					}
				}
				
				table.setFillsViewportHeight(true);
				frame.setLayout(new BorderLayout());
				frame.add(scrollPane, BorderLayout.PAGE_START);
				frame.add(scrollPane, BorderLayout.CENTER);
				//4. Size the frame.
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				//frame.setUndecorated(true);
				//5. Show it.
				frame.setVisible(true);
			}

		}
		
//		screen.repaint();
		
		myButton.setSelected(false);
		
	}

}
