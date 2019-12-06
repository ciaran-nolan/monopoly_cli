
package squares;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import cards.TitleDeed;
import game.*;
import squares.Utility;

import org.junit.jupiter.api.*;
//import java.util.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {
    private Utility utilityNew;

    @BeforeEach
    void setUp() {
        Board.initialiseBoard();
        //In this I will create a new Utility
        utilityNew = new Utility("Water Works",20);
        TitleDeed titleDeed = new TitleDeed("Title Deed","Water Works",0,null,200, new int[]{4,10},0,20, null,utilityNew);
        utilityNew.setTitleDeedCard(titleDeed);
    }

    @Test
    void testGetRents() {
        int[] expectedRent = new int[]{4,10};
        assertArrayEquals(expectedRent, utilityNew.getTitleDeedCard().getRents(), "Checking getRent() works");
    }

    @Test
    void testSetRents() {
        int[] expectedRent = new int[]{10,20};
        int[] overfillRent = new int[]{10,20,30,40};
        utilityNew.getTitleDeedCard().setRents(expectedRent);
        assertArrayEquals(expectedRent, utilityNew.getTitleDeedCard().getRents(),"Checking Rent is {10,20}");
        //This should fail as i cant overfill the array
        utilityNew.getTitleDeedCard().setRents(overfillRent);
        //Shouldn't be able to overfill re(expectedRent, not(equalTo()));
        assertNotEquals(expectedRent, utilityNew.getTitleDeedCard().getRents());
    }

    @Test
    void testBuy(){
        String instruction = "y\r\n";
        InputStream instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        Player player1 = new Player("John", "blue");
        utilityNew.buy(player1, null);
        assertEquals(true, player1.getTitleDeedList().contains(utilityNew.getTitleDeedCard()));
        player1.removeOwnedTitleDeed(utilityNew.getTitleDeedCard());
        player1.setMoney(150);
        instruction = "n\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        utilityNew.buy(player1, null);
        assertNotEquals(true, player1.getTitleDeedList().contains(utilityNew.getTitleDeedCard()));
    }

    @AfterEach
    void tearDown() {
        Board.clearBoard();
        utilityNew = null;
    }

}