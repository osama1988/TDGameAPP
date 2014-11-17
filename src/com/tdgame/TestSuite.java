package com.tdgame;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CritterTest.class,
	ScreenTest.class,
	ReadXMLTest.class,
	TowerTest.class,
	ActionHandlerTest.class
	})
public class TestSuite {
  //nothing
}