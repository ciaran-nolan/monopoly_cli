package ie.ucd.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommunityChestTest {
    private CommunityChest commChestTest;
    @BeforeEach
    void setUp() {
        commChestTest = new CommunityChest("PAY","Pay doctor's fees of £20", 20);
    }

    @Test
    void dealWithCard() {
        //In this case I need to test all cases of the chance card
        Player playerTest = new Player("John", "blue");
        //FIXME @@ciarannolan does this need to be static?
        int initialMoney = playerTest.getMoney();
        int currLocation = playerTest.getLocation();
        //PAY
        assertEquals(initialMoney-commChestTest.getCardValue(), playerTest.getMoney(),"Check money is reduced from Player");
        //INCOME
        commChestTest.setCardType("INCOME");
        assertEquals(initialMoney+commChestTest.getCardValue(),playerTest.getMoney(),"Checking income works");
        //MOVE
        commChestTest.setCardType("MOVE");
        commChestTest.setCardValue(5);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
        //GET OUT OF JAIL FREE
        commChestTest.setCardType("GET_OUT_OF_JAIL");
        assertTrue(playerTest.getJailFreeNum()>0);
        //CHOICE CARD
        //Fine is input of 0 and Chance is input of 1
        //FIXME @@ciarannolan....unsure about how to process the choice on this
        commChestTest.setCardDesc("Pay fine of £25 or pick Chance card");
        commChestTest.setCardType("CHOICE");
        assertEquals(initialMoney-commChestTest.getCardValue(),playerTest.getMoney(),"Checking on choice");
        //JAIL
        commChestTest.setCardType("JAIL");
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() {
        commChestTest = null;
    }
}