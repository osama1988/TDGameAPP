package com.tdgame;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ActionHandlerTest {

	int listCount;
	File[] list;
	ActionHandler ac;
	String pathForTesting="D:/Java/TDGameAPP/level/";
	
	@Before
	public void setContext(){
		ac = new ActionHandler();
		list = ac.getFileList();
		listCount = 0;
	}
	
	/**
	 * Checking if the program is loading all the available maps in level folder
	 */
	@Test
	public void mapListCheck() {
		ActionHandler ac = new ActionHandler();
		File[] list = ac.getFileList();
		listCount = list.length;
		File folder = new File(pathForTesting); //path where maps are stored
		File[] tempList = folder.listFiles();
		assertEquals(tempList.length, listCount);
	}

	@Test
	public void mapListCheckNotTrue() {
		listCount = list.length;
		assertNotEquals(10, listCount);
	}
}
