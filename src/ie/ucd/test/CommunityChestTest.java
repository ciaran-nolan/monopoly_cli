package ie.ucd.test;


import ie.ucd.game.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommunityChestTest {
    private CommunityChest commChestTest;
    private Player playerTest = new Player("John", "blue");
    @BeforeEach
    void setUp() {
        commChestTest = new CommunityChest("PAY","Pay doctor's fees of €20", 20);
    }

    @Test
    void testDealWithCardPAY() {
        int initialMoney = playerTest.getMoney();
        //PAY
        commChestTest.dealWithCard(playerTest);
        assertEquals(initialMoney-commChestTest.getCardValue(), playerTest.getMoney(),"Check money is reduced from Player");
    }  
    @Test
    public void testDealWithCardINCOME() {
        //INCOME
    	int initialMoney = playerTest.getMoney();
        commChestTest.setCardType("INCOME");
        commChestTest.dealWithCard(playerTest);
        assertEquals(initialMoney+commChestTest.getCardValue(),playerTest.getMoney(),"Checking income works");
    }  
    @Test
    public void testDealWithCardMOVE() {
       //MOVE
    	int currLocation = playerTest.getLocation();
        commChestTest.setCardType("MOVE");
        commChestTest.setCardValue(5);
        commChestTest.dealWithCard(playerTest);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
    }
    @Test
    public void testDealWithCardGETOUTOFJAIL() {
        //GET OUT OF JAIL FREE
        commChestTest.setCardType("GET_OUT_OF_JAIL");
        commChestTest.dealWithCard(playerTest);
        assertTrue(playerTest.getJailCard().size()>0);
    }
    @Test
    public void testDealWithCardCHOICE() {
        //CHOICE CARD
        //Fine is input of 0 and Chance is input of 1
        //FIXME @@ciarannolan....unsure about how to process the choice on this
    	int initialMoney = playerTest.getMoney();
    	commChestTest.setCardDesc("Pay fine of €25 or pick Chance card");
        commChestTest.setCardType("CHOICE");
        commChestTest.dealWithCard(playerTest);
        //Do it in the case of minusing the value
        assertEquals(initialMoney-commChestTest.getCardValue(),playerTest.getMoney(),"Checking on choice");
    }
    @Test
    public void testDealWithCardJAIL() {
        //JAIL
        commChestTest.setCardType("JAIL");
        commChestTest.dealWithCard(playerTest);
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() {
        commChestTest = null;
    }
}