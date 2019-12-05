package game;

import cards.*;
import operations.InputOutput;
import squares.Property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Player playerOwed;
    private InputStream instructionInputStream;

    @BeforeEach
    void setUp() throws Exception {
    	Board.initialiseBoard();
    	player = new Player("P1","red");
    	playerOwed = new Player("P2","blue");
    }

    @AfterEach
    void tearDown() throws Exception {
        Board.clearBoard();
        Game.playerList.clear();
    	player = null;
    	playerOwed = null;
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
    	//Checking PAY
    	Board.communityChests.get(0).setCardType("PAY");
    	Board.communityChests.get(0).setCardValue(20);
    	Board.communityChests.get(0).setCardDesc("TEST: You owe 20");
    	player.pickCommChestCard();
    	//Player will have 1480 now
    	assertEquals(1480, player.getMoney(), "Checking pay feature");

    	//Checking Income
    	Board.communityChests.get(0).setCardType("INCOME");
    	Board.communityChests.get(0).setCardValue(20);
    	Board.communityChests.get(0).setCardDesc("TEST: You get 20");
    	player.pickCommChestCard();
    	assertEquals(1500, player.getMoney(), "Checking income feature");

    	//Checking MOVE
    	Board.communityChests.get(0).setCardType("MOVE");
    	Board.communityChests.get(0).setCardValue(4);
    	Board.communityChests.get(0).setCardDesc("TEST: You move to square 4");
    	player.pickCommChestCard();
    	assertEquals(4, player.getLocation(), "Checking location is 4");

    	//Get out of jail free
    	Board.communityChests.get(0).setCardType("GET_OUT_OF_JAIL");
    	Board.communityChests.get(0).setCardDesc("TEST: Get out of jail");
    	player.pickCommChestCard();
    	assertEquals(1, player.getJailCard().size(), "Jail card array is size 1"); //Checking the jail free is in list

    	//Checking Jail
    	Board.communityChests.get(0).setCardType("JAIL");
    	Board.communityChests.get(0).setCardDesc("TEST: Sent to Jail");
    	player.pickCommChestCard();
    	assertTrue(player.isInJail());
    }

    @Test
    void testPickChanceCard() {
    	//Checking pay
    	Board.chances.get(0).setCardType("PAY");
    	Board.chances.get(0).setCardValue(20);
    	Board.chances.get(0).setCardDesc("TEST: You owe 20");
    	player.pickChanceCard();
    	//Player will have 1480 now
    	assertEquals(1480, player.getMoney(), "Checking pay feature");

    	//Checking Income
    	Board.chances.get(0).setCardType("INCOME");
    	Board.chances.get(0).setCardValue(20);
    	Board.chances.get(0).setCardDesc("TEST: You get 20");
    	player.pickChanceCard();
    	assertEquals(1500, player.getMoney(), "Checking income feature");

    	//Checking MOVE
    	Board.chances.get(0).setCardType("MOVE");
    	Board.chances.get(0).setCardValue(4);
    	Board.chances.get(0).setCardDesc("TEST: You move to square 4");
    	player.pickChanceCard();
    	assertEquals(4, player.getLocation(), "Checking location is 4");

    	//Get out of jail free
    	Board.chances.get(0).setCardType("GET_OUT_OF_JAIL");
    	Board.chances.get(0).setCardDesc("TEST: Get out of jail");
    	player.pickChanceCard();
    	assertEquals(1, player.getJailCard().size(), "Jail card array is size 1"); //Checking the jail free is in list

    	//Checking Jail
    	Board.chances.get(0).setCardType("JAIL");
    	Board.chances.get(0).setCardDesc("TEST: Sent to Jail");
    	player.pickChanceCard();
    	assertTrue(player.isInJail());
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
        String instruction = "2\r\nRob,red\r\nBob,blue";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
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
        String instruction = "n\r\nn\r\nn\r\nn\r\nn\r\nn\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);

        Game.playerList.add(player);
        Game.playerList.add(playerOwed);
        Player p3 = new Player("P3","Green");
        Game.playerList.add(p3);

        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
        Board.properties.get(0).setNumHouses(4);
        Board.properties.get(1).setNumHotels(1);
        CommunityChest temp = new CommunityChest("GET_OUT_OF_JAIL","Get out of jail free. This card may be kept until needed or sold",0);
        Board.communityChests.set(0, temp);
        player.pickCommChestCard();

        player.bankrupt(null);
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
        String instruction = "y\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
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
        String instruction = "y\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
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
        String instruction = "y\r\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
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
    	player.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
    	player.setMoney(5);
    	player.bankruptcyMortgage(10);
    	//Should be mortgaged as the value is high
    	boolean status = player.getTitleDeedList().get(0).getMortgageStatus();
    	assertTrue(status,"Checking status was set to true");
    	player.getTitleDeedList().clear();
    }


    @Test
    void testSaveFromBankruptcy() {
        String instruction = "y\r\ny\r\n0\r\n0\r\n1500\r\ny\rn\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        Game.playerList.add(player);
        Game.playerList.add(playerOwed);

        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
        Board.properties.get(0).setNumHouses(1);
        Board.properties.get(2).getTitleDeedCard().setMortgageStatus(true);
        Game.playerList.get(0).reduceMoney(2500,null);
    }

    @Test
    void testClearBankruptcyTradeStatus() {
        String instruction = "y\r\ny\r\n0\r\n0\r\n1500\r\ny\rn\n";
        instructionInputStream = new ByteArrayInputStream(instruction.getBytes());
        System.setIn(instructionInputStream);
        Game.playerList.add(player);
        Game.playerList.add(playerOwed);



        player.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
        player.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
        Board.properties.get(0).setNumHouses(1);
        Board.properties.get(2).getTitleDeedCard().setMortgageStatus(true);
        Board.properties.get(1).getTitleDeedCard().setBankruptcyTradeStatus(1000,playerOwed);

        player.clearBankruptcyTradeStatus();
        assertEquals(2500,playerOwed.getMoney());

    }
}