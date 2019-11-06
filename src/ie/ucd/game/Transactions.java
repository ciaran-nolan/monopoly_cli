package ie.ucd.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Transactions {
	
	public static int traderOneJailFree = 0;
	public static int traderOneCash = 0;
	public static HashMap<String,Property> traderOnePropsToTrade = new HashMap<String, Property>();
	
	public static int traderTwoJailFree = 0;
	public static int traderTwoCash = 0;
	public static HashMap<String,Property> traderTwoPropsToTrade = new HashMap<String, Property>();
	public static ArrayList<Player> tradeList = new ArrayList<Player>(2);
	
	public static void displayTradeItems(){
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
	
	public static void exchangeTradeItems() {
		//trade property Lists
		for(Property currentProperty: traderOnePropsToTrade.values()) {
			tradeList.get(0).removeOwnedProperty(currentProperty);
			tradeList.get(1).addPurchasedCard(currentProperty);
		}
	
		for(Property currentProperty: traderTwoPropsToTrade.values()) {
			tradeList.get(1).removeOwnedProperty(currentProperty);
			tradeList.get(0).addPurchasedCard(currentProperty);
		}
		
		//trade money
		tradeList.get(0).reduceMoney(traderOneCash, tradeList.get(1));
		tradeList.get(1).reduceMoney(traderTwoCash, tradeList.get(0));
	
		//trade Jail Free Cards - FIXME
		//tradeList.get(0).jailFreeCard+=(traderTwoJailFree-traderOneJailFree);
		//tradeList.get(1).jailFreeCard+=(traderOneJailFree-traderTwoJailFree);
	}
	
	public static void initiateTrade(Player initiatingPlayer) {
		tradeList.add(initiatingPlayer);
		
		//prompt the user who has initiated the desire to trade, to select who they wish to trade with
		System.out.println("Please indicate the player who you wish to initiate a transaction with, using their name");
		Scanner input = new Scanner(System.in);
		String playerChoice;
		
		playerChoice= input.next();
		Player chosenPlayer = Checks.isValidPlayer(playerChoice);
		
		while(null==chosenPlayer) {
			System.out.println(initiatingPlayer.getName()+", please enter a valid name");
			playerChoice= input.next();
			chosenPlayer = Checks.isValidPlayer(playerChoice);
		}
		
		tradeList.add(chosenPlayer);
		
	}
	
	public static void playerToPlayerTrade(Player initiatingPlayer) {

		initiateTrade(initiatingPlayer);

		Scanner input = new Scanner(System.in);
		String transactionChoice;
		//iterate over both players 
		for (int i = 0; i < 2; i++) {

			int tempCash = 0;
			int tempJail = 0;
			HashMap<String, Property> tempPropsToTrade = new HashMap<>();
			boolean finishedTrade = false;

			while (!finishedTrade) {
				Checks.playerStatus(tradeList.get(i));

				System.out.println("Please select what you wish to trade:\n[0]Cancel Trade\n[1]Jail Free Card\n[2]Property\n[3]Cash");
				transactionChoice = input.next();

				switch (transactionChoice) {
					case "0":
						System.out.println(tradeList.get(i).getName() + " is exiting without trade. Nothing has been exchanged");
						//return method without conducting a trade
						break;
					case "1":
						if (tradeList.get(i).getJailFreeNum() == 0) {
							if (InputOutput.yesNoInput("You do not have any get out of jail free cards.\n\nWould you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else {
							if (tradeList.get(i).getJailFreeNum() == tempJail) {
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
					case "2":
						if (input.hasNext()) {
							input.nextLine();
						}
						System.out.println("Please enter the name of the property you wish to include in the trade");
						transactionChoice = input.nextLine();
						Property propToTrade = Checks.isValidProp(transactionChoice, tradeList.get(i));
						if (null == propToTrade) {
							if (InputOutput.yesNoInput("The property you have entered is either invalid or not owned by you. Would you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else if (tempPropsToTrade.containsKey(propToTrade.getName())) {
							if (InputOutput.yesNoInput("The property you have entered is already in your list of items to trade. Would you like to trade something else? (y/n)", tradeList.get(i))) {
								continue;
							} else {
								finishedTrade = true;
								break;
							}
						} else {
							tempPropsToTrade.put(propToTrade.getName(), propToTrade);
						}
						break;
					case "3":
						System.out.println("Please specify the amount of cash you would like to include in this trade:");
						int cashToTrade = input.nextInt();
						if (cashToTrade > tradeList.get(i).getMoney()) {
							if (cashToTrade > tradeList.get(i).getMoney()) {
								if (InputOutput.yesNoInput("You have specified more cash than you currently have. Would you like to trade something else? (y/n)", tradeList.get(i))) {
									continue;
								} else {
									finishedTrade = true;
									break;
								}
							} else {
								tempCash += cashToTrade;
								break;
							}
						}
						//FIXME @@ciarannolan this condition isnt needed
						//if (!finishedTrade) {
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
						//}
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + transactionChoice);
				}
			}

			displayTradeItems();
			//Trade Acceptance
			if (InputOutput.yesNoInput(tradeList.get(0).getName() + " do you accept the terms of trade? (y/n)", tradeList.get(0))
					&& InputOutput.yesNoInput(tradeList.get(1).getName() + " do you accept the terms of trade? (y/n)", tradeList.get(1))) {
				exchangeTradeItems();
			} else {
				System.out.println("Trade has not been accepted by both parties");
				return;
			}
		}
	}
	//to save from bankruptcy, the player must exchange cards/properties for cash only
	public static void saveFromBankruptcyTrade(Player bankruptPlayer) {
		System.out.println(bankruptPlayer.getName()+" is at risk of bankruptcy");
		InputOutput.playerCanOwnInfo(bankruptPlayer);
		if(InputOutput.yesNoInput("Is there a player who is willing to make a trade with you?(y/n)", bankruptPlayer)){
			//FIXME please implement here
		}
		else{
			System.out.println("No players are willing to trade");
		}
	}
}
