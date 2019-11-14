package ie.ucd.game;

import java.util.ArrayList;
import java.util.Scanner;

import static ie.ucd.game.BoardReader.board;

public class InputOutput {
	private static Scanner input = new Scanner(System.in);
	
	public static void clearScannerBuffer(){
		input.nextLine();
	}
	
	public static void displayPlayerPosition(Player player) {
		System.out.println(player.getName()+" you are currently on square "+player.getLocation()+" - "
	+ board.get(player.getLocation()).getName());
	}

	public static boolean yesNoInput(String message,Player player) {
		System.out.println(message);
		String acknowledgement = input.nextLine();
		
		while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
			System.out.println(player.getName()+", please enter a valid response (y/n)");
			acknowledgement = input.nextLine();
		}
		return acknowledgement.equalsIgnoreCase("y");
	}

	public static TitleDeed titleDeedOperationMenu(Player player, String operation, boolean housesHotels){
		System.out.println("Please select the title deed card you wish to "+operation);
		ArrayList<TitleDeed>houseHotelList = new ArrayList<>();
		int choiceInput;

		for(int i=0; i<player.getTitleDeedList().size() ;i++){
			if(housesHotels && (player.getTitleDeedList().get(i).getOwnableSite() instanceof Property)){
				houseHotelList.add(player.getTitleDeedList().get(i));
			}
			else if(!housesHotels){
				System.out.println("["+i+"] "+player.getTitleDeedList().get(i).getCardDesc());
			}
		}
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
		else{
			System.out.println("["+(player.getTitleDeedList().size())+"] Cancel");
			choiceInput = integerMenu(0,player.getTitleDeedList().size());
			if(choiceInput == player.getTitleDeedList().size()){
				return null;
			}
			else return player.getTitleDeedList().get(choiceInput);
		}
	}

	public static void squareInformation(int index){

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

	public static int integerMenu(int lowerBound, int upperBound){
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

	public static void handleUserOption(Player currentPlayer,boolean doubleRoll) {
		System.out.println("\n"+currentPlayer.getName()+", please enter in Numeric form what you would like to do!");
		System.out.println("----------------------------------------------------------------\n" +
						"|\t1: Mortgage/Demortgage a property\n" +
						"|\t2: Build Houses/Hotels on Square\n" +
						"|\t3: Buy/Sell Properties or Cards with other players");
		
		if(doubleRoll) System.out.println("|\t4: Roll Dice\n"+
				"----------------------------------------------------------------\n");
		else System.out.println("|\t4: Exit\n"+
				"----------------------------------------------------------------\n");
			
		
		int choiceInput = InputOutput.integerMenu(1, 4);
		
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
						propToMortgage.mortgage(currentPlayer);
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
				 break;
			default:
				System.out.println("Your input did not correspond to any provided actions");
				break;
		}
		if((choiceInput<4) && doubleRoll && InputOutput.yesNoInput("Would you like to do an additional action before rolling the dice? (y/n)", currentPlayer)) {
			handleUserOption(currentPlayer, true);
		}
	}

	public static void playerCanOwnInfo (Player player){
	    System.out.println(player.getName()+"'s current property/utility/train status:");
	    for (TitleDeed titleDeed : player.getTitleDeedList()){
			CanOwn currentProperty = titleDeed.getOwnableSite();
	    	System.out.println("Name:"+titleDeed.getCardDesc()+"\nIs Mortgaged?: "+titleDeed.getMortgageStatus());
	        if(currentProperty instanceof Property){
	            System.out.println("Colour: "+((Property) currentProperty).getSquareColour()+"\nHouses: "
                        +((Property) currentProperty).getNumHouses()+"\nHotels: "+((Property) currentProperty).getNumHotels());
            }
        }
    }

    public static Player selectPlayerMenu (Player selectingPlayer){
		System.out.println("Please select player you wish to interact with");
		ArrayList<Player> playerList = Game.playerList;
		int choiceInput;
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i)==selectingPlayer){
				playerList.remove(i);
				if(i<playerList.size()) i--;
			}
			else System.out.println("["+i+"] "+playerList.get(i).getName());
		}

		choiceInput = integerMenu(0,playerList.size());
		return playerList.get(choiceInput);
	}
}
