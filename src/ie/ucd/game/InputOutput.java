package ie.ucd.game;

import java.util.ArrayList;
import java.util.Scanner;

import static ie.ucd.game.Board.board;

public class InputOutput {
	//single scanner for project
	private static Scanner input = new Scanner(System.in);
	
	private static void clearScannerBuffer(){
		input.nextLine();
	}

	//method to handle yes/no inputs
	public static boolean yesNoInput(String message,Player player) {
		System.out.println(message);
		String acknowledgement = input.nextLine();

		//ensure the player enters a valid response
		while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
			System.out.println(player.getName()+", please enter a valid response (y/n)");
			acknowledgement = input.nextLine();
		}
		return acknowledgement.equalsIgnoreCase("y");
	}

	//menu to allow players to select a titledeed card to conduct an operation (mortgage/improve etc)
	//house hotels arguemnet ensures only instances of property are shown when true
	static TitleDeed titleDeedOperationMenu(Player player, String operation, boolean housesHotels){
		System.out.println("Please select the title deed card you wish to "+operation);
		ArrayList<TitleDeed>houseHotelList = new ArrayList<>();
		int choiceInput;

		//loop thrugh available title deeds
		for(int i=0; i<player.getTitleDeedList().size() ;i++){
		    if(!player.getTitleDeedList().get(i).getBankruptcyTradeStatus().isEmpty()){
		        continue;
            }
		    //if house and hotels is true so store only instances of property
			if(housesHotels && (player.getTitleDeedList().get(i).getOwnableSite() instanceof Property)){
				houseHotelList.add(player.getTitleDeedList().get(i));
			}
			//not a house or hotel operation so print any titledeed
			else if(!housesHotels){
				System.out.println("["+i+"] "+player.getTitleDeedList().get(i).getCardDesc());
			}
		}
		//print house hotel list
		if(housesHotels){
			for(int i=0; i<houseHotelList.size();i++){
				System.out.println("["+i+"] "+houseHotelList.get(i).getCardDesc());
			}
			System.out.println("["+(houseHotelList.size())+"] Cancel");
			choiceInput = integerMenu(0,houseHotelList.size());
			if(choiceInput == houseHotelList.size()){
				return null;
			}
			else return houseHotelList.get(choiceInput);
		}
		//print cancel at the end for normal operation and receive user choice
		else{
			System.out.println("["+(player.getTitleDeedList().size())+"] Cancel");
			choiceInput = integerMenu(0,player.getTitleDeedList().size());
			//cancel has been chosen so return null
			if(choiceInput == player.getTitleDeedList().size()){
				return null;
			}
			//a titledeed has been selected so return
			else return player.getTitleDeedList().get(choiceInput);
		}
	}

	//display information about a square
	static void squareInformation(int index){

		//type canown requires additional information
		if(board.get(index) instanceof CanOwn){
			System.out.println("You have landed on: " + board.get(index).getName()+" (Index: "+index+")");
			if(null == (((CanOwn) board.get(index)).getTitleDeedCard().getOwner())){
				System.out.println("Owner: None") ;
			}
			else{
				System.out.println("Owner: "+(((CanOwn) board.get(index)).getTitleDeedCard().getOwner().getName())) ;
			}
			if (board.get(index) instanceof Property) {
				System.out.println("Colour: "+((Property) board.get(index)).getSquareColour()+"\nHouses: "
						+((Property) board.get(index)).getNumHouses()+"\nHotels: "+((Property) board.get(index)).getNumHotels());
			}
		}
		else{
			System.out.println("You have landed on "+ board.get(index).getName()+" (Index: "+index+")");
		}
	}

	static int integerMenu(int lowerBound, int upperBound){
		int choiceInput;
		System.out.println("Please enter a choice of a number between "+lowerBound+" and "+upperBound+":");

		do{
			while(!input.hasNextInt()) {
				System.out.println("Please enter a valid integer between "+lowerBound+" and "+upperBound+":");
				input.next();
			}
			choiceInput = input.nextInt();
			if(!(choiceInput >= lowerBound && choiceInput <= upperBound)) {
				System.out.println("Please enter a choice of a number between "+lowerBound+" and "+upperBound+":");
			}
		}while(!(choiceInput >= lowerBound && choiceInput <= upperBound));
		clearScannerBuffer();
		return choiceInput;
	}

	static void handleUserOption(Player currentPlayer, boolean doubleRoll) {
		System.out.println("\n"+currentPlayer.getName()+", please enter in Numeric form what you would like to do!");
		System.out.println("----------------------------------------------------------------\n" +
						"|\t1: Mortgage/Demortgage a property\n" +
						"|\t2: Build Houses/Hotels on Square\n" +
						"|\t3: Buy/Sell Properties or Cards with other players\n" +
                        "|\t4: Player Status");
		
		if(doubleRoll) System.out.println("|\t5: Roll Dice\n"+
				"----------------------------------------------------------------\n");
		else System.out.println("|\t5: Exit\n"+
				"----------------------------------------------------------------\n");
			
		
		int choiceInput = InputOutput.integerMenu(1, 5);
		
		switch(choiceInput) {
			case 1:
				System.out.println("[0] Mortgage\n[1] Demortgage");
				choiceInput = integerMenu(0,1);
				if(choiceInput==0) {
					TitleDeed titleDeedToMortgage = titleDeedOperationMenu(currentPlayer, "mortgage", false);
					if (null == titleDeedToMortgage) {
						System.out.println("Cancelling Operation");
					} else {
						CanOwn propToMortgage = (titleDeedToMortgage.getOwnableSite());
						propToMortgage.mortgage(currentPlayer, false);
					}
				}
				else{
					TitleDeed titleDeedToDemortgage = titleDeedOperationMenu(currentPlayer, "mortgage", false);
					if (null == titleDeedToDemortgage) {
						System.out.println("Cancelling Operation");
					} else {
						CanOwn propToDemortgage = (titleDeedToDemortgage.getOwnableSite());
						propToDemortgage.demortgage(false);
					}
				}
				break;
			case 2:
				//This is for choosing to build house on a square
				Property.buildHousesHotels(currentPlayer);
				break;
			case 3:
				//This is for buying a property
				Transactions.playerToPlayerTrade(currentPlayer);
				break;
			case 4:
			     Checks.checkPlayerStatus(currentPlayer);
			     Checks.checkPlayerCanOwnStatus(currentPlayer);
				 break;
            case 5:
                break;
			default:
				System.out.println("Your input did not correspond to any provided actions");
				break;
		}
		if((choiceInput<5) && doubleRoll && InputOutput.yesNoInput("Would you like to do an additional action before rolling the dice? (y/n)", currentPlayer)) {
			handleUserOption(currentPlayer, true);
		}
	}

    static Player selectPlayerMenu(Player selectingPlayer){
		System.out.println("Please select player you wish to interact with");
		ArrayList<Player> playerMenu = new ArrayList<>(Game.playerList);
		int choiceInput;
		for(int i = 0; i<playerMenu.size();i++){
			if(playerMenu.get(i)==selectingPlayer){
				playerMenu.remove(i);
				if(i<playerMenu.size()) i--;
			}
			else System.out.println("["+i+"] "+playerMenu.get(i).getName());
		}

		choiceInput = integerMenu(0,playerMenu.size());
		return playerMenu.get(choiceInput);
	}
}
