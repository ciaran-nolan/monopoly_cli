package ie.ucd.operations;

import ie.ucd.cards.TitleDeed;
import ie.ucd.game.*;
import ie.ucd.operations.Checks;
import ie.ucd.squares.Property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChecksTest {
    private Player player = new Player("Player", "Red");
    @BeforeEach
    void setUp() {
        Board.initialiseBoard();
        player.getTitleDeedList().clear();
        player.setMoney(1500);
        Game.playerList.clear();
    }
    
    @Test
    void testCheckSquare1() {
    	//Property
    	
    	//Unowned - buy property
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Board.properties.get(0).getTitleDeedCard().setOwner(null);
    	Checks.checkSquare(1, player);
    	assertTrue(player.getTitleDeedList().get(0).getCardDesc().equals(Board.properties.get(0).getTitleDeedCard().getCardDesc()));
    	player.setMoney(1500);
    	
    	//owned by player
    	Board.properties.get(0).getTitleDeedCard().setOwner(player);
    	Checks.checkSquare(1,player);
    	
    	//owned by a different player
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Player player2 = new Player("P2","Green");
    	player.getTitleDeedList().get(0).setOwner(player2);
    	player.getTitleDeedList().clear();
    	
    	Checks.checkSquare(1,player);
    	assertEquals(1498,player.getMoney());
    	player.setMoney(1500);
    }
    
    @Test
    void testCheckSquare2() {
    	//Utility
    	
    	//Unowned - buy property
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Board.utilities.get(0).getTitleDeedCard().setOwner(null);
    	Checks.checkSquare(12, player);
    	assertTrue(player.getTitleDeedList().get(0).getCardDesc().equals(Board.utilities.get(0).getTitleDeedCard().getCardDesc()));
    	player.setMoney(1500);
    	
    	//owned by player
    	Board.utilities.get(0).getTitleDeedCard().setOwner(player);
    	Checks.checkSquare(12,player);
    	
    	//owned by a different player
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Player player2 = new Player("P2","Green");
    	player2.addPurchasedTitleDeed(player.getTitleDeedList().get(0));
    	player.getTitleDeedList().clear();
    	Dice.setDieVals(2,3);
    	
    	Checks.checkSquare(12,player);
    	assertEquals(1480,player.getMoney());
    	player.setMoney(1500);
    }
    
    @Test
    void testCheckSquare3() {
    	//Utility
    	
    	//Unowned - buy property
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Board.trains.get(0).getTitleDeedCard().setOwner(null);
    	Checks.checkSquare(5, player);
    	assertTrue(player.getTitleDeedList().get(0).getCardDesc().equals(Board.trains.get(0).getTitleDeedCard().getCardDesc()));
    	player.setMoney(1500);
    	
    	//owned by player
    	Board.trains.get(0).getTitleDeedCard().setOwner(player);
    	Checks.checkSquare(5,player);
    	
    	//owned by a different player
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Player player2 = new Player("P2","Green");
    	player2.addPurchasedTitleDeed(player.getTitleDeedList().get(0));
    	player.getTitleDeedList().clear();
    	Dice.setDieVals(2,3);
    	
    	Checks.checkSquare(5,player);
    	assertEquals(1475,player.getMoney());
    	player.setMoney(1500);
    }
    
    @Test
    void testCheckSquare4() {
    	//GO
    	Checks.checkSquare(0,player);
    	assertEquals(1700,player.getMoney());
    	
    	//Chance
    	Checks.checkSquare(2,player);
    }
    
    @Test
    void testEnoughFunds() {
        player.setMoney(10);
        assertFalse(Checks.enoughFunds(player,100));
        player.setMoney(200);
        assertTrue(Checks.enoughFunds(player,100));
        player.setMoney(1500);
    }

    @Test
    void testCanBuy() {
      TitleDeed testTitleDeed = Board.properties.get(0).getTitleDeedCard();
      testTitleDeed.setOwner(null);
      assertTrue(Checks.canBuy(testTitleDeed));
      player.addPurchasedTitleDeed(testTitleDeed);
      assertFalse(Checks.canBuy(testTitleDeed));
    }

    @Test
    void testIsPlayerOwner() {
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertTrue(Checks.isPlayerOwner(player.getTitleDeedList().get(0), player));
    }

    @Test
    void testOwnAllColour() {
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        assertFalse(Checks.ownAllColour(player,(Property)player.getTitleDeedList().get(0).getOwnableSite())==null);
        player.removeOwnedTitleDeed(player.getTitleDeedList().get(0));
        assertTrue(Checks.ownAllColour(player,(Property)player.getTitleDeedList().get(0).getOwnableSite())==null);
        assertEquals(null,Checks.ownAllColour(player, null));
    }

    @Test
    void testEvenHouseDistribution() {
    	
    	//buy houses
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        ArrayList<Property> colourGroup = Checks.ownAllColour(player, (Property)player.getTitleDeedList().get(0).getOwnableSite());
        ((Property)player.getTitleDeedList().get(1).getOwnableSite()).setNumHouses(0);
        ((Property)player.getTitleDeedList().get(0).getOwnableSite()).setNumHouses(1);
        assertTrue(Checks.evenHouseDistribution(colourGroup,((Property)player.getTitleDeedList().get(1).getOwnableSite()),true));
        assertFalse(Checks.evenHouseDistribution(colourGroup,((Property)player.getTitleDeedList().get(0).getOwnableSite()),true));
        
        
        //sell houses
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        colourGroup = Checks.ownAllColour(player, (Property)player.getTitleDeedList().get(0).getOwnableSite());
        ((Property)player.getTitleDeedList().get(1).getOwnableSite()).setNumHouses(0);
        ((Property)player.getTitleDeedList().get(0).getOwnableSite()).setNumHouses(1);
        assertFalse(Checks.evenHouseDistribution(colourGroup,((Property)player.getTitleDeedList().get(1).getOwnableSite()),false));
        assertTrue(Checks.evenHouseDistribution(colourGroup,((Property)player.getTitleDeedList().get(0).getOwnableSite()),false));
    }

    @Test
    void testCanBuildHousesHotels() {
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        //valid - can build houses hotels
        assertEquals(0, Checks.canBuildHousesHotels((Property)player.getTitleDeedList().get(0).getOwnableSite(),player));
        player.getTitleDeedList().remove(0);
        //Don't own all the properties in this group, don't try again
        System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "n ENTER"
				);
        assertEquals(-2, Checks.canBuildHousesHotels(Board.properties.get(0),player));
        System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
        //Don't own all the properties in this group, try again
        assertEquals(-1,Checks.canBuildHousesHotels(((Property)player.getTitleDeedList().get(0).getOwnableSite()),player));  
        System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "n ENTER"
				);
        //Null property, don't try again
        assertEquals(-2,  Checks.canBuildHousesHotels(null,player));
        System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
        //Null property, try again
        assertEquals(-1,  Checks.canBuildHousesHotels(null,player));
        
        player.getTitleDeedList().clear();
        Board.properties.get(0).getTitleDeedCard().setOwner(null);
        
        System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "n ENTER"
				);
        //don't try again
        assertEquals(-2,Checks.canBuildHousesHotels(Board.properties.get(0),player));
        System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
        //try again
        assertEquals(-1,Checks.canBuildHousesHotels(Board.properties.get(0),player));
    
        
    }

    @Test
    void testCheckHouseHotelValue() {
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        Board.properties.get(0).setNumHouses(4);
        Board.properties.get(1).setNumHouses(3);
        assertEquals(350/2,Checks.checkHouseHotelValue(player));
    }

    @Test
    void testCheckMortgagingValue() {
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        assertEquals(60, Checks.checkMortgagingValue(player));
    }
    
    @Test
    void testCheckPlayerStatus() {
    	Checks.checkPlayerStatus(player);
    	
    }
    
    @Test
    void testCheckPlayerCanOwnStatus() {
    	player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
    	Checks.checkPlayerCanOwnStatus(player);
    	
    }
    @Test
    void testCheckBankruptcyTradeValue() {
    	Player player2 = new Player("P2","Green");
    	Board.properties.get(3).getTitleDeedCard().setOwner(null);
    	System.out.println("*********\n"+
				"Player 1 Press:\n"
				+ "y ENTER"
				);
    	Board.properties.get(3).buy(player);
    	player.getTitleDeedList().get(0).setBankruptcyTradeStatus(1000, player2);
    	assertEquals(1000,Checks.checkBankruptcyTradeValue(player));
    }
    @Test
    void testCheckIfValidGame() {
    	Player player2 = new Player("P2","Green");
    	Game.playerList.add(player);
    	Game.playerList.add(player2);
    	assertTrue(Checks.checkIfValidGame());
    }

}