package com.tdgame;


import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CritterTest.class,
	BomberTest.class,
	ScreenTest.class,
	ReadXMLTest.class,
	TowerTest.class,
	ActionHandlerTest.class
	})

public class TestSuite {

}
