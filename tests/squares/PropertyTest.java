package squares;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import squares.Property;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    private Player player = new Player("P1","Red");
    private InputStream instructionInputStream;

    @BeforeEach
    void setup(){
        Board.initialiseBoard();
    }

    @AfterEach
    void tearDown(){
        Board.clearBoard();
    }

    @Test
    void testGetNumHotels() {
        Property testprop = Board.properties.get(0);
        assertEquals(0,testprop.getNumHotels());
    }

    @Test
    void testGetNumHouses() {
        Board.properties.get(0).setNumHouses(3);
        Property testprop = Board.properties.get(0);
        assertEquals(3,testprop.getNumHouses());

    }

    @Test
    void testSetNumHouses() {
        Property testprop = Board.properties.get(0);
        testprop.setNumHouses(2);
        assertEquals(2,testprop.getNumHouses());
        testprop.setNumHouses(0);
    }

    @Test
    void testSetNumHotels() {
        Property testprop = Board.properties.get(0);
        testprop.setNumHotels(1);
        assertEquals(1,testprop.getNumHotels());
        testprop.setNumHotels(0);
    }
    @Test
    void testGetSquareColour() {
        Property testprop = Board.properties.get(0);
        assertEquals("Purple", testprop.getSquareColour());
    }

    @Test
    void testBuy() {
        String instruction = "y\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
    	System.out.println("\n-------------------------------");
        System.out.println("TEST: Press y and ENTER");
        System.out.println("-------------------------------");
        Board.properties.get(0).getTitleDeedCard().setOwner(null);
        Board.properties.get(0).buy(player, null);
        assertTrue(player.getTitleDeedList().contains(Board.properties.get(0).getTitleDeedCard()));
    }

    @Test
    void testBuildHousesHotels() {

        String instruction = "0\r\n0\r\ny\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        Board.properties.get(1).setNumHouses(0);

        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        System.out.println(player.getTitleDeedList().size());
        Property testProp =(Property)player.getTitleDeedList().get(0).getOwnableSite();
        testProp.setNumHouses(0);
        testProp.setNumHotels(0);
        System.out.println("\n-------------------------------");
        System.out.println("TEST: Press 0, then 0 and then y");
        System.out.println("-------------------------------");
        Property.buildHousesHotels(player);
        assertEquals(1,testProp.getNumHouses());
    }

    @Test
    void testSellHouses() {

        String instruction = "n\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        System.out.println("\n-------------------------------");
        System.out.println("TEST: Please enter n for NO");
        System.out.println("-------------------------------");
        Property testProp1 = (Property)player.getTitleDeedList().get(0).getOwnableSite();
        Property testProp2 = (Property)player.getTitleDeedList().get(1).getOwnableSite();
        testProp1.setNumHouses(4);
        testProp2.setNumHouses(4);
        testProp1.sellHouses(player,false,false);
        assertEquals(3,testProp1.getNumHouses());
    }

    @Test
    void testSellHotels() {

        String instruction = "n\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);

        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        Property testProp1 = (Property)player.getTitleDeedList().get(0).getOwnableSite();
        Property testProp2 = (Property)player.getTitleDeedList().get(1).getOwnableSite();
        testProp1.setNumHotels(1);
        testProp2.setNumHouses(4);
        System.out.println("\n-------------------------------");
        System.out.println("TEST: Please enter n for NO");
        System.out.println("-------------------------------");
        testProp1.sellHotels(player,false,false);
        assertEquals(4,testProp1.getNumHouses());
    }
}