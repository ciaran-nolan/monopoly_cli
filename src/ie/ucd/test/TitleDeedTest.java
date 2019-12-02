package ie.ucd.test;
import ie.ucd.game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TitleDeedTest {
	private  Player p1 = new Player("P1","red");
	private  Player p2 = new Player("P2","blue");
	@BeforeEach
	void setUp() throws Exception {
		Board.initialiseBoard();
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testTitleDeed() {
		fail("Not yet implemented");
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
		int mortgage 
		fail("Not yet implemented");
	}

	@Test
	void testSetMortgage() {
		fail("Not yet implemented");
	}

	@Test
	void testSetBankruptcyTradeStatus() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBankruptcyTradeStatus() {
		fail("Not yet implemented");
	}

	@Test
	void testPlayerAuction() {
		fail("Not yet implemented");
	}

}
