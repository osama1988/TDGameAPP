package testcases;


import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CritterTest.class,
	BomberTest.class,
	ScreenTest.class,
	ReadXMLTest.class,
	TowerTest.class,
	ActionHandlerTest.class,
	NearToTowerTest.class
	})

public class TestSuite {

}
