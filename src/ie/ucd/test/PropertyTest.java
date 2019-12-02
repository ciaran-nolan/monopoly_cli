package ie.ucd.test;

import ie.ucd.game.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    private Player player = new Player("P1","Red");

    @BeforeAll
    public static void setup(){
        Board.initialiseBoard();
    }

    @Test
    void testGetNumHotels() {
        Property testprop = Board.properties.get(0);
        assertEquals(0,testprop.getNumHotels());
    }

    @Test
    void testGetNumHouses() {
        Property testprop = Board.properties.get(0);
        assertEquals(0,testprop.getNumHouses());

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
        System.out.println(Board.properties.get(0).getSquareColour());
        assertTrue(testprop.getSquareColour().equals("Purple"));
    }

    @Test
    void testBuy() {
        //player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        String input = "y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Board.properties.get(0).buy(player);
        //BoardReader.properties.get(1).buy(player);
        assertTrue(player.getTitleDeedList().contains(Board.properties.get(0).getTitleDeedCard()));
    }

    @Test
    void testBuildHousesHotels() {
        String input = "0\r\n0\r\ny\r\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        Property testProp =(Property)player.getTitleDeedList().get(0).getOwnableSite();
        testProp.setNumHouses(0);
        testProp.setNumHotels(0);
        Property.buildHousesHotels(player);
        assertEquals(1,testProp.getNumHouses());
    }

    @Test
    void testSellHouses() {
        String input = "n\r\n" ;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());

        Property testProp1 = (Property)player.getTitleDeedList().get(0).getOwnableSite();
        Property testProp2 = (Property)player.getTitleDeedList().get(1).getOwnableSite();
        testProp1.setNumHouses(4);
        testProp2.setNumHouses(4);
        testProp1.sellHouses(player,false,false);
        assertEquals(3,testProp1.getNumHouses());
    }

    @Test
    void testSellHotels() {
        String input = "n\r\n" ;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        Property testProp1 = (Property)player.getTitleDeedList().get(0).getOwnableSite();
        Property testProp2 = (Property)player.getTitleDeedList().get(1).getOwnableSite();
        testProp1.setNumHotels(1);
        testProp2.setNumHouses(4);
        testProp1.sellHotels(player,false,false);
        assertEquals(4,testProp1.getNumHouses());
    }
}