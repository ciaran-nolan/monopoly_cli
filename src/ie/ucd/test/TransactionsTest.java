package ie.ucd.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ie.ucd.cards.CommunityChest;
import ie.ucd.game.Board;
import ie.ucd.game.Game;
import ie.ucd.game.Player;
import ie.ucd.operations.Transactions;

class TransactionsTest {
	private CommunityChest temp = new CommunityChest("GET_OUT_OF_JAIL","Get out of jail free. This card may be kept until needed or sold",0);
	
	@BeforeEach
	void setUp() throws Exception {
		Board.initialiseBoard();
		System.out.println("Create 2/3 players");
		Game.playerList = Player.createListPlayers();
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPlayerToPlayerTrade1() {
		
		Board.communityChests.set(0, temp);
		Board.communityChests.set(1, temp);
		Player p1 = Game.playerList.get(0);
		Player p2 = Game.playerList.get(1);
		
		p1.addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		p1.addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		p1.pickCommChestCard();
		
		p2.addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		p2.addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		p2.pickCommChestCard();
		
		System.out.println("Player 1: Add a property, attempt to add the same property. Attempt to add another property, but cancel before. "+
		"Add a jail free card, attempt to add another jail free card you dont have. Add some cash");
		System.out.println("Player 2: Add a property,. Add a jail free card. Add some cash. Complete trade");
		Transactions.playerToPlayerTrade(p1);
		assertTrue(p1.getTitleDeedList().contains(Board.properties.get(2).getTitleDeedCard()));

		Game.playerList.get(0).getTitleDeedList().clear();
		Game.playerList.get(1).getTitleDeedList().clear();
		
		Game.playerList.get(0).getJailCard().clear();
		Game.playerList.get(0).getJailCard().clear();
	}
	
	@Test
	void testPlayerToPlayerTrade2() {
		
		Board.communityChests.set(0, temp);
		Board.communityChests.set(1, temp);
		
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		Game.playerList.get(0).pickCommChestCard();
		
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		
		System.out.println("both players trade. PLayer two attempt to add jail free card that you do not have, player 1 DONT ACCEPT trade terms");
		Transactions.playerToPlayerTrade(Game.playerList.get(0));
		
		Game.playerList.get(0).getTitleDeedList().clear();
		Game.playerList.get(1).getTitleDeedList().clear();
		
		Game.playerList.get(0).getJailCard().clear();
		Game.playerList.get(0).getJailCard().clear();
		
	}
	@Test
	void testPlayerToPlayerTrade3() {
	System.out.println("Player 1 immediately cancel trade, press 0");
	Transactions.playerToPlayerTrade(Game.playerList.get(0));
	}
	
	
	@Test
	void testSaveFromBankruptcyTrade1() {
		System.out.println("Make a normal Trade");
		Player p1 = Game.playerList.get(0);
		Player p2 = Game.playerList.get(1);
		
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		Transactions.saveFromBankruptcyTrade(p1);
		
	}
	@Test
	void testSaveFromBankruptcyTrade2() {
		System.out.println("Players two and three bid for chosen property, until one player decides to no longer bid");
		Player p1 = Game.playerList.get(0);
		Player p2 = Game.playerList.get(1);
		Player p3 = new Player("p3","Green");
		Game.playerList.add(p3);
		
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(0).getTitleDeedCard());
		Game.playerList.get(0).addPurchasedTitleDeed(Board.properties.get(1).getTitleDeedCard());
		
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(2).getTitleDeedCard());
		Game.playerList.get(1).addPurchasedTitleDeed(Board.properties.get(3).getTitleDeedCard());
		Transactions.saveFromBankruptcyTrade(p1);
	}

}
