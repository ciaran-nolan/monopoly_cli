package ie.ucd.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Transactions {
	
	private static int traderOneJailFree = 0;
	private static int traderOneCash = 0;
	private static HashMap<String,TitleDeed> traderOnePropsToTrade = new HashMap<>();
	private static int traderTwoJailFree = 0;
	private static int traderTwoCash = 0;
	private static HashMap<String,TitleDeed> traderTwoPropsToTrade = new HashMap<>();
	private static ArrayList<Player> tradeList = new ArrayList<>(2);

	private static void displayTradeItems(){
		//show each players trades
		
	    //Player One
		System.out.println("Terms of trade:\n\n"+tradeList.get(0).getName()+"\n\nJail free Cards: "+traderOneJailFree+"\nProperties:");
		for(String key: traderOnePropsToTrade.keySet()) {
			System.out.println(key);
		}
		System.out.println("Cash: "+traderOneCash);

		//PLayer Two
		System.out.println("\n"+tradeList.get(1).getName()+"\n\nJail free Cards: "+traderTwoJailFree+"\nProperties:");
		for(String key: traderTwoPropsToTrade.keySet()) {
			System.out.println(key);
		}
		System.out.println("Cash: "+traderTwoCash);
		
	}
	
	private static void exchangeTradeItems() {
		Player traderOne = tradeList.get(0);
		Player traderTwo = tradeList.get(1);
		//trade property Lists
		for(TitleDeed currentTitleDeed: traderOnePropsToTrade.values()) {
			traderOne.removeOwnedTitleDeed(currentTitleDeed);
			traderTwo.addPurchasedTitleDeed(currentTitleDeed);
		}
	
		for(TitleDeed currentTitleDeed: traderTwoPropsToTrade.values()) {
			traderTwo.removeOwnedTitleDeed(currentTitleDeed);
			traderOne.addPurchasedTitleDeed(currentTitleDeed);
		}
		
		//trade money
		traderOne.reduceMoney(traderOneCash, traderTwo);
		traderTwo.reduceMoney(traderTwoCash, traderOne);
	
		//trade Jail Free Cards
		if(traderOneJailFree>0){
			for(int i=0;i<traderOneJailFree;i++){
				traderTwo.addJailCard(traderOne.getJailCard().get(i));
				traderOne.getJailCard().remove(i);
			}
		}
		else if(traderTwoJailFree>0){
			for(int i=0;i<traderTwoJailFree;i++){
				traderOne.addJailCard(traderTwo.getJailCard().get(i));
				traderTwo.getJailCard().remove(i);
			}
		}
	}
	
	private static void initiateTrade(Player initiatingPlayer) {
		tradeList.add(initiatingPlayer);
		//prompt the user who has initiated the desire to trade, to select who they wish to trade with
		Player chosenPlayer = InputOutput.selectPlayerMenu(initiatingPlayer);
		tradeList.add(chosenPlayer);
	}
	
	public static void playerToPlayerTrade(Player initiatingPlayer) {

		initiateTrade(initiatingPlayer);
		int transactionChoice;
		//iterate over both players 
		for (int i = 0; i < 2; i++) {

			int tempCash = 0;
			int tempJail = 0;
			HashMap<String, TitleDeed> tempPropsToTrade = new HashMap<>();
			boolean finishedTrade = false;

			while (!finishedTrade) {
				Checks.checkPlayerStatus(tradeList.get(i));
				System.out.println("Please select what you wish to trade:\n[0]Cancel Trade\n[1]Jail Free Card\n[2]Property\n[3]Cash");
				transactionChoice = InputOutput.integerMenu(0,3);

				switch (transactionChoice) {
					case 0:
						System.out.println(tradeList.get(i).getName() + " is exiting without trade. Nothing has been exchanged");
						//return method without conducting a trade
						break;
					case 1:
						if (tradeList.get(i).getJailCard().size() == 0) {
							if (InputOutput.yesNoInput("You do not have any get out of jail free cards.\n\nWould you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else {
							if (tradeList.get(i).getJailCard().size() == tempJail) {
								if (InputOutput.yesNoInput("You do not have any more get out of jail free cards to add to your trade.\n\nWould you like to trade something else? (y/n)", tradeList.get(i))) {
									continue;
								} else {
									finishedTrade = true;
									break;
								}
							}
							tempJail++;
						}
						break;
					case 2:
						TitleDeed titleDeedToTrade = InputOutput.titleDeedOperationMenu(tradeList.get(i),"trade",false);
						if (null == titleDeedToTrade) {
							if (InputOutput.yesNoInput("You have cancelled this operation. Would you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else if (tempPropsToTrade.containsKey(titleDeedToTrade.getCardDesc())) {
							if (InputOutput.yesNoInput("The property you have entered is already in your list of items to trade. Would you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else {
							tempPropsToTrade.put(titleDeedToTrade.getCardDesc(), titleDeedToTrade);
						}
						break;
					case 3:
						System.out.println("Please specify the amount of cash you would like to include in this trade:");
						int cashToTrade = InputOutput.integerMenu(1,(tradeList.get(i).getMoney()-tempCash));
						tempCash += cashToTrade;
						//We can then ask do they want to add anything to the trade
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + transactionChoice);
				}
				if (InputOutput.yesNoInput(tradeList.get(i).getName() + " are you finished making your trade? (y/n)", tradeList.get(i))) {
					if (i == 0) {
						traderOneCash = tempCash;
						traderOneJailFree = tempJail;
						traderOnePropsToTrade.putAll(tempPropsToTrade);
					} else {
						traderTwoCash = tempCash;
						traderTwoJailFree = tempJail;
						traderTwoPropsToTrade.putAll(tempPropsToTrade);
					}
					finishedTrade = true;
				}
			}
		}
		displayTradeItems();
		//Trade Acceptance
		if (InputOutput.yesNoInput(tradeList.get(0).getName() + " do you accept the terms of trade? (y/n)", tradeList.get(0))
				&& InputOutput.yesNoInput(tradeList.get(1).getName() + " do you accept the terms of trade? (y/n)", tradeList.get(1))) {
			exchangeTradeItems();
		} else {
			System.out.println("Trade has not been accepted by both parties");
		}
	}
	private static void bankruptcySingleTransaction(Player bankruptPlayer, Player purchasingPlayer, TitleDeed tradeItem){
		System.out.println(purchasingPlayer.getName()+" please enter the amount to purchase "+ tradeItem.getCardDesc());
		int purchaseAmount = InputOutput.integerMenu(1,purchasingPlayer.getMoney());
		while(!InputOutput.yesNoInput(bankruptPlayer.getName()+" do you accept this trade amount?",bankruptPlayer)){
			if(InputOutput.yesNoInput("Do you want to attempt to trade again, "+purchasingPlayer.getName()+"?",purchasingPlayer)){
				purchaseAmount = InputOutput.integerMenu(1,purchasingPlayer.getMoney());
			}
			else break;
		}
		tradeItem.setBankruptcyTradeStatus(purchaseAmount,  purchasingPlayer);
		purchasingPlayer.reduceMoney(purchaseAmount,null);
	}
	//to save from bankruptcy, the player must exchange cards/properties for cash only
	static void saveFromBankruptcyTrade(Player bankruptPlayer) {
		System.out.println(bankruptPlayer.getName()+" is at risk of bankruptcy");
		Checks.checkPlayerCanOwnStatus(bankruptPlayer);
		if(InputOutput.yesNoInput("Is there a player who is willing to make a trade with you?(y/n)", bankruptPlayer)){

			if(Game.playerList.size()==2){
				Player purchasingPlayer = InputOutput.selectPlayerMenu(bankruptPlayer);
				TitleDeed tradeItem = InputOutput.titleDeedOperationMenu(bankruptPlayer, "trade", false);
				bankruptcySingleTransaction(bankruptPlayer, purchasingPlayer, tradeItem);
				System.out.println(Game.playerList.size());
			}
			else {
				TitleDeed tradeItem = InputOutput.titleDeedOperationMenu(bankruptPlayer, "trade", false);
				if(tradeItem == null){
					System.out.println("Trade has been cancelled.");

				}
				tradeItem.playerAuction(bankruptPlayer);
			}
		}
		else{
			System.out.println("No players are willing to trade");
		}
	}
}
