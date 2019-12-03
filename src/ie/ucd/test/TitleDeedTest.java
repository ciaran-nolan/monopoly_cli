package ie.ucd.test;
import ie.ucd.game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class TitleDeedTest {
	private  Player p1 = new Player("P1","red");
	private  Player p2 = new Player("P2","blue");
	private Property prop1 = new Property(10,"orange", "Test");
	@BeforeEach
	void setUp() throws Exception {
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
		Board.initialiseBoard();
	}

	@Test
	void testTitleDeed() {
		TitleDeed t1 = new TitleDeed("Title Deed", "Test", 0, "orange", 100, new int[]{1,2,3,4}, 10,50,p1,prop1);
		assertEquals("Title Deed", t1.getCardType(),"Checking card Type");
		assertEquals("Test", t1.getCardDesc(),"Checking Card Desc");
		assertEquals(0,t1.getCardValue(),"Checking card value");
		assertEquals("orange", t1.getSquareColour(),"Checking Square Colour");
		assertEquals(100,t1.getPriceBuy(),"Checking Buy Price");
		assertEquals(10,t1.getHousePrice(),"Checking House Price");
		assertEquals(50,t1.getMortgage(),"Checking mortgage value");
		assertEquals(p1,t1.getOwner(),"Checking owner");
		assertEquals(prop1,t1.getOwnableSite(),"Checking ownable site");
	}

	@Test
	void testGetOwnableSite() {
		String propName = Board.properties.get(3).getTitleDeedCard().getOwnableSite().getName();
		assertEquals("Euston Road", propName, "Checking Name is correct"); 
	}

	@Test
	void testGetHousePrice() {
		int housePrice = Board.properties.get(3).getTitleDeedCard().getHousePrice();
		assertEquals(50,housePrice, "Checking house price is equal to £50 for getter");
	}

	@Test
	void testSetHousePrice() {
		Board.properties.get(3).getTitleDeedCard().setHousePrice(25);
		int housePrice = Board.properties.get(3).getTitleDeedCard().getHousePrice();
		assertEquals(25,housePrice,"Checking set house price to £25");
	}

	@Test
	void testGetPriceBuy() {
		Board.properties.get(3).getTitleDeedCard().setpriceBuy(100);
		int buyPrice = Board.properties.get(3).getTitleDeedCard().getPriceBuy();
		assertEquals(100,buyPrice,"Checking buy price is gotten as £100");
	}

	@Test
	void testSetpriceBuy() {
		Board.properties.get(3).getTitleDeedCard().setpriceBuy(125);
		int buyPrice = Board.properties.get(3).getTitleDeedCard().getPriceBuy();
		assertEquals(125,buyPrice,"Checking buy price has been set as £125");
	}

	@Test
	void testGetRents() {
		int[] propRents = Board.properties.get(3).getTitleDeedCard().getRents();
		int[] testRents  = {6,30,90,270,400,550};
		assertArrayEquals(testRents, propRents, "Checking Getter for rents works");
	}

	@Test
	void testSetRents() {
		int[] testRents  = {10,40,100,270,500,550};
		Board.properties.get(3).getTitleDeedCard().setRents(testRents);
		int[] propRents = Board.properties.get(3).getTitleDeedCard().getRents();
		assertArrayEquals(testRents, propRents, "Checking Setter for rents works");
	}

	@Test
	void testGetSquareColour() {
		String colour = Board.properties.get(3).getTitleDeedCard().getSquareColour();
		assertEquals("Light blue",colour,"Checking getSquareColour gives \"Light blue\"");
	}

	@Test
	void testSetSquareColour() {
		String setColour = "Dark blue";
		Board.properties.get(3).getTitleDeedCard().setSquareColour(setColour);
		String colour = Board.properties.get(3).getTitleDeedCard().getSquareColour();
		assertEquals(setColour,colour,"Checking setSquareColour gives \"Dark blue\"");
	}

	@Test
	void testGetOwner() {
		Board.properties.get(3).getTitleDeedCard().setOwner(p1);
		String ownerName = Board.properties.get(3).getTitleDeedCard().getOwner().getName();
		assertEquals("P1", ownerName, "Checking GetOwner can work");
	}

	@Test
	void testSetOwner() {
		Board.properties.get(3).getTitleDeedCard().setOwner(p2);
		String ownerName = Board.properties.get(3).getTitleDeedCard().getOwner().getName();
		assertEquals("P2", ownerName, "Checking SetOwner can work");
	}

	@Test
	void testGetMortgageStatus() {
		boolean status = Board.properties.get(3).getTitleDeedCard().getMortgageStatus();
		assertFalse(status, "Checking Mortgage status can be gotten");
	}

	@Test
	void testSetMortgageStatus() {
		Board.properties.get(3).getTitleDeedCard().setMortgageStatus(true);
		boolean status = Board.properties.get(3).getTitleDeedCard().getMortgageStatus();
		assertTrue(status, "Checking Mortgage status can be gotten");
	}

	@Test
	void testGetMortgage() {
		Board.properties.get(3).getTitleDeedCard().setMortgage(50);
		int mortgage = Board.properties.get(3).getTitleDeedCard().getMortgage();
		assertEquals(50, mortgage, "Checking getMortgage returns £50");
	}

	@Test
	void testSetMortgage() {
		Board.properties.get(3).getTitleDeedCard().setMortgage(20);
		int mortgage = Board.properties.get(3).getTitleDeedCard().getMortgage();
		assertEquals(20, mortgage, "Checking setMortgage set to £20");
	}

	@Test
	void testSetBankruptcyTradeStatus() {
		Board.properties.get(3).getTitleDeedCard().setBankruptcyTradeStatus(50, p2);
		Set<Integer> status = Board.properties.get(3).getTitleDeedCard().getBankruptcyTradeStatus().keySet();
		//Check whether the keyset is empty
		assertFalse(status.isEmpty());
	}

	@Test
	void testGetBankruptcyTradeStatus() {
		Board.properties.get(3).getTitleDeedCard().setBankruptcyTradeStatus(50, p2);
		Set<Integer> status = Board.properties.get(3).getTitleDeedCard().getBankruptcyTradeStatus().keySet();
		assertTrue(status.contains(50));
	}
	//FIXME think about us testing this @@ciarannolan??
	@Test
	void testPlayerAuction() {
		fail("Not yet implemented");
	}

}
