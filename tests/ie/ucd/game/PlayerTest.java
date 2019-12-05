
package ie.ucd.game;

import ie.ucd.cards.Card;
import ie.ucd.cards.Chance;
import ie.ucd.cards.TitleDeed;
import ie.ucd.operations.InputOutput;
import ie.ucd.squares.Property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Player playerOwed;
    private Property prop1;
    private TitleDeed t1;
    //InputStream in_reset = System.in;
    
    @BeforeEach
    void setUp() throws Exception {
    	Board.initialiseBoard();
    	player = new Player("P1","red");
    	playerOwed = new Player("P2","blue");
    	prop1 = new Property(10,"orange", "Test");
    	t1 = new TitleDeed("Title Deed", "Test", 0, "orange", 100, new int[]{1,2,3,4,5,6}, 10,50,player,prop1);
//    	ByteArrayInputStream in = new ByteArrayInputStream(("y"+System.lineSeparator()+"y"+System.lineSeparator()+"y"+System.lineSeparator()).getBytes());
//    	System.setIn(in);
    }
    
    @AfterEach
    void tearDown() throws Exception {
    	player = null;
    	playerOwed = null;
    	prop1 = null;
    	t1 = null;
//    	System.setIn(in_reset);
    }
    
    @Test
    void testGetName() {
        assertTrue(player.getName().equals("P1"));
    }

    @Test
    void testGetToken() {
        assertTrue(player.getToken().equals("red"));
    }

    @Test
    void testGetMoney() {
        assertEquals(1500,player.getMoney());
    }

    @Test
    void testGetLocation() {
        player.setLocation(20);
        assertEquals(20,player.getLocation());
    }

    @Test
    void testGetTitleDeedList() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
        System.out.println(player.getTitleDeedList().get(0).getCardDesc());
        assertTrue(player.getTitleDeedList().get(0).getCardDesc().equals("Old Kent Road"));
    }

    @Test
    void testGetJailCard() {
    	Card jailTest = new Chance("JAIL","Jail Card1", 0);
    	player.addJailCard(jailTest);
    	assertEquals(jailTest, player.getJailCard().get(0),"Checking index of jail card");
    	player.getJailCard().clear();
    }

    @Test
    void testAddJailCard() {
    	Card jailTest = new Chance("JAIL","Jail Card2", 0);
    	player.addJailCard(jailTest);
    	//Checking that the Jail card array is not empty
    	assertFalse(player.getJailCard().isEmpty());
    	player.getJailCard().clear();
    }

    @Test
    void testSetName() {
    	player.setName("P2");
    	assertEquals("P2",player.getName(),"Checking Set Name works");
    }

    @Test
    void testSetMoney() {
        player.setMoney(2000);
        assertEquals(2000,player.getMoney());
    }

    @Test
    void testGetJailMoves() {
        assertEquals(0, player.getJailMoves());
    }

    @Test
    void testSetJailMoves() {
        player.setJailMoves(2);
        assertEquals(2, player.getJailMoves());
    }

    @Test
    void testSetToken() {
        player.setToken("Green");
        assertEquals("Green", player.getToken());
    }

    @Test
    void testAddMoney() {
        player.addMoney(1000);
        assertEquals(2500,player.getMoney());

    }

    @Test
    void testSetLocation() {
        player.setLocation(25);
        assertEquals(25,player.getLocation());
    }

    @Test
    void testReduceMoney() { 
    	//Testing owing the bank
        player.reduceMoney(100,null);
        assertEquals(1400, player.getMoney());
        //Owe another player
        int initialPlayer2 = playerOwed.getMoney();
        int initialPlayer1 = player.getMoney();
        player.reduceMoney(100, playerOwed);
        assertEquals(initialPlayer1-100,player.getMoney(), "Player should have less money");
        assertEquals(initialPlayer2+100, playerOwed.getMoney(), "Player owed should have gained £100");
    }

    @Test
    void testMovePlayer() {
        player.movePlayer(10);
        assertEquals(10,player.getLocation());
        player.setLocation(38);
        int money = player.getMoney();
        player.movePlayer(5);
        assertEquals(money+200,player.getMoney(),"Checking £200 given for passing GO");
    }

    @Test
    void testMoveToSquare() {
        player.moveToSquare(25);
        assertEquals(25,player.getLocation());
    }

    @Test
    void testPickCommChestCard() {
//    	Board.initialiseBoard();
//    	//First card in deck is random each time
    	player.pickCommChestCard();
    }

    @Test
    void testPickChanceCard() {
//    	Board.initialiseBoard();
//    	//First card is random each time 
    	player.pickChanceCard();
    }

    @Test
    void testIsInJail() {
        assertFalse(player.isInJail());
        player.setInJail(true);
        assertTrue(player.isInJail());
    }

    @Test
    void testSetInJail() {
        player.setInJail(true);
        assertTrue(player.isInJail());
    }


    @Test
    void testCreateListPlayers() {
    	System.out.println("\n-----------------\nTEST: CREATE LIST OF PLAYERS\nPlease enter the following:");
    	System.out.println("2 (PRESS ENTER) Rob,red (PRESS ENTER) Bob,blue (PRESS ENTER)\n-----------------");
        ArrayList<Player> playerList = InputOutput.createListPlayers(null);
        assertEquals("Rob", playerList.get(0).getName(),"Checking Name entered");
        assertEquals("red", playerList.get(0).getToken(),"Checking Token");
        assertEquals("Bob", playerList.get(1).getName(), "Checking Name is Ciaran");
        assertEquals("blue", playerList.get(1).getToken(),"Checking Token");
    }

    @Test
    void testBankrupt() {
    }

    @Test
    void testAddPurchasedTitleDeed() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
    }

    @Test
    void testRemoveOwnedTitleDeed() {
        Board.initialiseBoard();
        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        assertEquals(1,player.getTitleDeedList().size());
        player.removeOwnedTitleDeed(player.getTitleDeedList().get(0));
        assertEquals(0,player.getTitleDeedList().size());
    }

    @Test
    void testPayRentPROPERTY() {
    	//T1 is now in hand of playerOwed
    	//Player owed has the title deed card in their list
    	int initialMoney = player.getMoney();
    	int initialMoneyOwed = playerOwed.getMoney();
    	Board.properties.get(0).getTitleDeedCard().setOwner(playerOwed);
    	playerOwed.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
    
    	//Paying rent on the ownable site
    	((Property)(Board.properties.get(0).getTitleDeedCard().getOwnableSite())).setNumHouses(2);
    	Board.properties.get(0).getTitleDeedCard().setMortgageStatus(false);
    	System.out.println("\n-----------------\nTEST: PAY RENT PROPERTY\n Press y then ENTER\n-----------------");
    	player.payRent(Board.properties.get(0));
    	assertEquals(initialMoney-30, player.getMoney(),"Comparing money gained");
    	assertEquals(initialMoneyOwed+30, playerOwed.getMoney(), "Comparing money gained by person owed");
    }
    
    @Test
    void testPayRentTRAIN() {
    	int initialMoney = player.getMoney();
    	int initialMoneyOwed = playerOwed.getMoney();
    	Board.trains.get(0).getTitleDeedCard().setOwner(playerOwed);
    	playerOwed.addPurchasedTitleDeed(Board.trains.get(0).getTitleDeedCard());
    	
    	//Paying rent on the ownable site
    	System.out.println("-----------------\nTEST: PAY RENT TRAIN\n Press y then ENTER\n-----------------");
    	player.payRent(Board.trains.get(0));
    	assertEquals(initialMoney-25, player.getMoney(),"Comparing money gained");
    	assertEquals(initialMoneyOwed+25, playerOwed.getMoney(), "Comparing money gained by person owed");
    }
    
    @Test
    void testPayRentUTILITY() {
    	int initialMoney = player.getMoney();
    	int initialMoneyOwed = playerOwed.getMoney();
    	Board.utilities.get(0).getTitleDeedCard().setOwner(playerOwed);
    	playerOwed.addPurchasedTitleDeed(Board.utilities.get(0).getTitleDeedCard());
    	//Setting Dievals to 2 and 3 = 5 (rent will be 20)
    	Dice.setDieVals(2,3);
    	//Paying rent on the ownable site
    	System.out.println("\n-----------------\nTEST: PAY RENT UTILITY\n Press y then ENTER\n-----------------");
    	player.payRent(Board.utilities.get(0));
    	assertEquals(initialMoney-20, player.getMoney(),"Comparing money gained");
    	assertEquals(initialMoneyOwed+20, playerOwed.getMoney(), "Comparing money gained by person owed");
    }

    @Test
    void testBankruptcyMortgage() {
    	Board.initialiseBoard();
    	player.getTitleDeedList().clear();
    	player.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
    	player.setMoney(5);
    	player.bankruptcyMortgage(10);
    	//Should be mortgaged as the value is high
    	boolean status = player.getTitleDeedList().get(0).getMortgageStatus();
    	assertTrue(status,"Checking status was set to true");
    	player.getTitleDeedList().clear();
    }

    @Test
    void testBankruptcySellHousesHotels() {
    }

    @Test
    void testCompleteBankruptcyTrade() {
    }

    @Test
    void testSaveFromBankruptcy() {
    }

    @Test
    void testClearBankruptcyTradeStatus() {
    }
}