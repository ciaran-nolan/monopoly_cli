package ie.ucd.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    private Player player = new Player("P1","Red");

    @BeforeAll
    public static void setup(){
        BoardReader.initialiseBoard();
    }

    @Test
    void getNumHotels() {
        Property testprop = BoardReader.properties.get(0);
        assertEquals(0,testprop.getNumHotels());
    }

    @Test
    void getNumHouses() {
        Property testprop = BoardReader.properties.get(0);

        assertEquals(0,testprop.getNumHouses());

    }

    @Test
    void setNumHouses() {
        Property testprop = BoardReader.properties.get(0);
        testprop.setNumHouses(2);
        assertEquals(2,testprop.getNumHouses());
        testprop.setNumHouses(0);
    }

    @Test
    void setNumHotels() {
        Property testprop = BoardReader.properties.get(0);
        testprop.setNumHotels(1);
        assertEquals(1,testprop.getNumHotels());
        testprop.setNumHotels(0);
    }
    @Test
    void getSquareColour() {
        Property testprop = BoardReader.properties.get(0);
        System.out.println(BoardReader.properties.get(0).getSquareColour());
        assertTrue(testprop.getSquareColour().equals("Purple"));
    }

    @Test
    void buy() {
        player.addPurchasedTitleDeed(BoardReader.properties.get(0).getTitleDeedCard());
        //BoardReader.properties.get(0).buy(player);
        //BoardReader.properties.get(1).buy(player);
        assertTrue(player.getTitleDeedList().contains(BoardReader.properties.get(0).getTitleDeedCard()));
    }

    @Test
    void buildHousesHotels() {
        BoardReader.properties.get(0).buy(player);
        BoardReader.properties.get(1).buy(player);
        Property testProp = BoardReader.properties.get(0);
        testProp.setNumHouses(0);
        testProp.setNumHotels(0);
        Property.buildHousesHotels(player);
    }

    @Test
    void sellHouses() {
    }

    @Test
    void sellHotels() {
    }
}