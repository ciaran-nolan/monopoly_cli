package cards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CommunityChestTest {
    private CommunityChest commChestTest;
    private Player playerTest = new Player("John", "blue");

    @BeforeEach
    void setUp() {
        commChestTest = new CommunityChest("PAY","Pay doctor's fees of £20", 20);
    }

    @Test
    void testDealWithCardPAY() {
    	//Testing a pay card
        int initialMoney = playerTest.getMoney();
        commChestTest.dealWithCard(playerTest, null);
        assertEquals(initialMoney-commChestTest.getCardValue(), playerTest.getMoney(),"Check money is reduced from Player");
    }  
    @Test
    void testDealWithCardINCOME() {
        //INCOME, checking an income card
    	int initialMoney = playerTest.getMoney();
        commChestTest.setCardType("INCOME");
        commChestTest.dealWithCard(playerTest, null);
        assertEquals(initialMoney+commChestTest.getCardValue(),playerTest.getMoney(),"Checking income works");
    }  
    @Test
    void testDealWithCardMOVE() {
        //MOVE, checking a Move card
    	int currLocation = playerTest.getLocation();
        commChestTest.setCardType("MOVE");
        commChestTest.setCardValue(5);
        commChestTest.dealWithCard(playerTest, null);
        assertEquals(currLocation+5,playerTest.getLocation(),"Checking a player can move squares");
    }
    @Test
    void testDealWithCardGETOUTOFJAIL() {
        //GET OUT OF JAIL FREE
        commChestTest.setCardType("GET_OUT_OF_JAIL");
        commChestTest.dealWithCard(playerTest, null);
        assertNotEquals(0, playerTest.getJailCard().size(), "Checking the jail card was added to their jail card array list");
    }
    //In this case, there is a choice to be made and we will pick FINE
    @Test
    void testDealWithCardCHOICE() {

        String instruction = "0\r\n";
        InputStream instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
    	int initialMoney = playerTest.getMoney();
    	commChestTest.setCardDesc("Pay fine of £25 or pick Chance card");
        commChestTest.setCardType("CHOICE");
        commChestTest.dealWithCard(playerTest, null);

        //Do it in the case of minusing the value
        assertEquals(initialMoney-commChestTest.getCardValue(),playerTest.getMoney(),"Checking on choice");
    }
    //Jail card
    @Test
    void testDealWithCardJAIL() {
        //JAIL
        commChestTest.setCardType("JAIL");
        commChestTest.dealWithCard(playerTest, null);
        assertTrue(playerTest.isInJail());
    }

    @AfterEach
    void tearDown() {
        commChestTest = null;
    }
}