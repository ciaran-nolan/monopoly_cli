package operations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import cards.TitleDeed;
import game.Game;
import game.Player;

/**
 * The Transactions class handles all of the transactions between Player objects when they want to trade and during bankruptcy (and saving from
 * bankruptcy)
 * @author Robert Keenan and Ciaran Nolan
 *
 */
public class Transactions {
	//Initialising variables
	private static int traderOneJailFree = 0; //Trading jail free card first player
	private static int traderOneCash = 0;	  //Trading cash first player
	private static HashMap<String,TitleDeed> traderOnePropsToTrade = new HashMap<>(); //Trading properties 1st player
	private static int traderTwoJailFree = 0; //Trading 2nd player jail free cards
	private static int traderTwoCash = 0;     //Trading 2nd player cash
	private static HashMap<String,TitleDeed> traderTwoPropsToTrade = new HashMap<>(); //Trading properties 2nd plaer
	private static ArrayList<Player> tradeList = new ArrayList<>(2);		//A large trade list of players taking part

	/**
	 * Method to display the trade items and the terms of the trade.
	 * This includes what is included in the trade such as Jail free cards, Properties, Cash etc and what each player is offering each other
	 */
	private static void displayTradeItems(){
		//show each players trades
		
	    //Player One
		System.out.println("Terms of trade:\n\n"+tradeList.get(0).getName()+":\nJail free Cards: "+traderOneJailFree+"\nProperties:");
		for(String key: traderOnePropsToTrade.keySet()) {
			System.out.println(key);
		}
		System.out.println("Cash: £"+traderOneCash);

		//PLayer Two
		System.out.println("\n"+tradeList.get(1).getName()+":\nJail free Cards: "+traderTwoJailFree+"\nProperties:");
		for(String key: traderTwoPropsToTrade.keySet()) {
			System.out.println(key);
		}
		System.out.println("Cash: £"+traderTwoCash);
	}
	
	/**
	 * Method to reset all of the class variables which are used in the trade
	 */
	private static void resetTempValues() {
		traderOneJailFree = 0;
		traderOneCash = 0;
		traderOnePropsToTrade.clear();
		traderTwoJailFree = 0;
		traderTwoCash = 0;
		traderTwoPropsToTrade.clear();
		tradeList.clear();
	}

	/**
	 * It is checking the mortgage status of a title deed card when it is sold and when it is not sold. It checks whether the property has just been sold and thus, 
	 * they need to pay the 10% interest instantly. 
	 * @param player The Player object who has just purchased site
	 * @param mortgagedTitledeed The TitleDeed card object of the purchased site 
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */
	public static void handleMortgagedTitledeed(Player player, TitleDeed mortgagedTitledeed, BufferedReader userInput){
		if(userInput==null)  userInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(mortgagedTitledeed.getCardDesc()+" which "+player.getName()+" has received in trade or auction is mortgaged\n"
		+player.getName()+", do you wish to: \n[0]demortgage now\n[1]Pay 10% Interest and demortgage at another time");
		int choiceInput = InputOutput.integerMenu(0,1,userInput);
		if(choiceInput==0){
			mortgagedTitledeed.getOwnableSite().demortgage(true,true);
		}
		else mortgagedTitledeed.getOwnableSite().demortgage(false,true);
	}

	/**
	 * Exchanging of the trade items that have been decided on
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */
	private static void exchangeTradeItems(BufferedReader userInput) {
		if(userInput==null)  userInput = new BufferedReader(new InputStreamReader(System.in));
		Player traderOne = tradeList.get(0);
		Player traderTwo = tradeList.get(1);
		//trade money
		traderOne.reduceMoney(traderOneCash, traderTwo);
		traderTwo.reduceMoney(traderTwoCash, traderOne);

        //trade property Lists -  Trade a property from player 1 to player 2 etc and vice versa
		for(TitleDeed currentTitleDeed: traderOnePropsToTrade.values()) {

			traderOne.removeOwnedTitleDeed(currentTitleDeed);
			traderTwo.addPurchasedTitleDeed(currentTitleDeed);
			if(currentTitleDeed.getMortgageStatus()) handleMortgagedTitledeed(traderTwo, currentTitleDeed,userInput);
		}
	
		for(TitleDeed currentTitleDeed: traderTwoPropsToTrade.values()) {
			traderTwo.removeOwnedTitleDeed(currentTitleDeed);
			traderOne.addPurchasedTitleDeed(currentTitleDeed);
			if(currentTitleDeed.getMortgageStatus()) handleMortgagedTitledeed(traderOne, currentTitleDeed,userInput);
		}
		

	
		//trade Jail Free Cards
		if(traderOneJailFree>0){
			for(int i=0;i<traderOneJailFree;i++){
				traderTwo.addJailCard(traderOne.getJailCard().get(i));
				traderOne.getJailCard().remove(i);
			}
		}
		if(traderTwoJailFree>0){
			for(int i=0;i<traderTwoJailFree;i++){
				traderOne.addJailCard(traderTwo.getJailCard().get(i));
				traderTwo.getJailCard().remove(i);
			}
		}
		//Resetting the trade values
		resetTempValues();
	}
	
	/**
	 * Initiating a trade between a player and another player. It asks the initiatingPlayer what Player they want to trade with and this is
	 * received using InputOutput.selectPlayerMenu. This 2nd player is then added to the tradeList class variable of this class
	 * @param initiatingPlayer The Player object initiating the trade
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */
	private static void initiateTrade(Player initiatingPlayer, BufferedReader userInput) {
		if(userInput==null)  userInput = new BufferedReader(new InputStreamReader(System.in));
		tradeList.add(initiatingPlayer);
		//prompt the user who has initiated the desire to trade, to select who they wish to trade with
		Player chosenPlayer = InputOutput.selectPlayerMenu(initiatingPlayer, userInput);
		tradeList.add(chosenPlayer);
	}
	/**
	 * This class handles all of the player to player trading. It calls initiateTrade() from this trade with another player and then proceeds to iterate over both players.
	 * It asks the user's what they want to trade first of all such as Jail Free card, Property, Cash or to leave the trade. It will then check whether the user has entered a valid
	 * entry such as them not having any jail free cards but still wanting to trade one.
	 * It goes through these cases until both players decide they are finished trading all of the items they want to. It will then ask both users to accept the terms of the trade and if they do,
	 * it will complete the relevant trades of assets.
	 * @param initiatingPlayer The Player Object wishing to initiate a trade
	 */
	static void playerToPlayerTrade(Player initiatingPlayer) {
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		initiateTrade(initiatingPlayer, userInput);
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
				transactionChoice = InputOutput.integerMenu(0,3, userInput);

				switch (transactionChoice) {
					case 0:
						System.out.println(tradeList.get(i).getName() + " is exiting without trade. Nothing has been exchanged");
						//return method without conducting a trade
						return;
					case 1:
						if (tradeList.get(i).getJailCard().size() == 0) {
							if (InputOutput.yesNoInput("You do not have any get out of jail free cards.\n\nWould you like to trade something else? (y/n)", tradeList.get(i), userInput)) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else {
							if (tradeList.get(i).getJailCard().size() == tempJail) {
								if (InputOutput.yesNoInput("You do not have any more get out of jail free cards to add to your trade.\n\nWould you like to trade something else? (y/n)", tradeList.get(i), userInput)) {
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
						TitleDeed titleDeedToTrade = InputOutput.titleDeedOperationMenu(tradeList.get(i),"trade",false, userInput);
						if (null == titleDeedToTrade) {
							if (InputOutput.yesNoInput("You have cancelled this operation. Would you like to trade something else? (y/n)", tradeList.get(i), userInput)) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else if (tempPropsToTrade.containsKey(titleDeedToTrade.getCardDesc())) {
							if (InputOutput.yesNoInput("The property you have entered is already in your list of items to trade. Would you like to trade something else? (y/n)", tradeList.get(i), userInput)) {
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
						int cashToTrade = InputOutput.integerMenu(1,(tradeList.get(i).getMoney()-tempCash), userInput);
						tempCash += cashToTrade;
						//We can then ask do they want to add anything to the trade
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + transactionChoice);
				}
				
				if (InputOutput.yesNoInput(tradeList.get(i).getName() + " are you finished making your trade? (y/n)", tradeList.get(i), userInput)) {
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
		if (InputOutput.yesNoInput(tradeList.get(0).getName() + " do you accept the terms of trade? (y/n)", tradeList.get(0), userInput)
				&& InputOutput.yesNoInput(tradeList.get(1).getName() + " do you accept the terms of trade? (y/n)", tradeList.get(1), userInput)) {
			exchangeTradeItems(userInput);
		} else {
			System.out.println("Trade has not been accepted by both parties");
		}
	}

	/**
	 * This method is used for a player who is bankrupt and a single transaction is made with another player. THe trade item can be passed in as a TitleDeed
	 * card if the trade is with a CanOwn item. It will then ask purchasing player if they want to accept the trade and if they do, the trade will
	 * be processed.
	 * @param bankruptPlayer The player object bankrupt who wants to trade with another
	 * @param purchasingPlayer The purchasing player of an asset from the bankrupt player
	 * @param tradeItem The item to be traded in TitleDeed object form
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */
	private static void bankruptcySingleTransaction(Player bankruptPlayer, Player purchasingPlayer, TitleDeed tradeItem, BufferedReader userInput){
		if(userInput==null) userInput = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(purchasingPlayer.getName()+" please enter the amount to purchase "+ tradeItem.getCardDesc());
		int purchaseAmount = InputOutput.integerMenu(1,purchasingPlayer.getMoney(), userInput);
		while(!InputOutput.yesNoInput(bankruptPlayer.getName()+" do you accept this trade amount?",bankruptPlayer, userInput)){
			if(InputOutput.yesNoInput("Do you want to attempt to trade again, "+purchasingPlayer.getName()+"?",purchasingPlayer, userInput)){
				purchaseAmount = InputOutput.integerMenu(1,purchasingPlayer.getMoney(), userInput);
			}
			else break;
		}
		tradeItem.setBankruptcyTradeStatus(purchaseAmount,  purchasingPlayer);
		purchasingPlayer.reduceMoney(purchaseAmount,null);
	}
	//to save from bankruptcy, the player must exchange cards/properties for cash only
	/**
	 * To the save from bankruptcy in terms of trading, we can use this method to trade cards or properties which checks whether there is a player
	 * willing to make a trade with the bankrupt player.
	 * If there are only 2 oeople in the playerList, then the single transaction is enacted between them.
	 * Otherwise, if there are no other players who wish to trade then it goes to auction
	 * @param bankruptPlayer The player object who is bankrupt and wishes to trade to gain money
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */

	public static void saveFromBankruptcyTrade(Player bankruptPlayer, BufferedReader userInput) {
		if(userInput==null){userInput = new BufferedReader(new InputStreamReader(System.in));}
		System.out.println(bankruptPlayer.getName()+" is at risk of bankruptcy");
		Checks.checkPlayerCanOwnStatus(bankruptPlayer);
		if(InputOutput.yesNoInput("Is there a player who is willing to make a trade with you?(y/n)", bankruptPlayer, userInput)){

			if(Game.playerList.size()==2){
				Player purchasingPlayer = InputOutput.selectPlayerMenu(bankruptPlayer, userInput);
				TitleDeed tradeItem = InputOutput.titleDeedOperationMenu(bankruptPlayer, "trade", false, userInput);
				bankruptcySingleTransaction(bankruptPlayer, purchasingPlayer, tradeItem, userInput);
			}
			else {
				TitleDeed tradeItem = InputOutput.titleDeedOperationMenu(bankruptPlayer, "trade", false, userInput);
				if(tradeItem == null){
					System.out.println("Trade has been cancelled.");
				}
				else tradeItem.playerAuction(bankruptPlayer, userInput);
			}
		}
		else{
			System.out.println("No players are willing to trade");
		}
	}
}
