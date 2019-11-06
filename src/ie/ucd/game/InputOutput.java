package ie.ucd.game;

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
	
	public static Property propertyInput(Player player, String propAction) {
		System.out.println("Please enter the name of the property you wish to "+ propAction);
		String propName = input.nextLine();
		Property property = Checks.isValidProp(propName, player);
		if(null==property) {
			if(InputOutput.yesNoInput("The property you have entered is not valid, would you like to try again? (y/n)", player)){
				//restart pre-dice roll options
				propertyInput(player, propAction);
			}
			return null;
		}
		else return property;
		
	}

	public static void jailMove(Player player){
		System.out.println(player+" you are currently in jail, please selec");
	}

	public static void miscellaneousStringInputs(){

	}
	public static void handleUserOption(Player currentPlayer,boolean doubleRoll) {
		System.out.println("\n"+currentPlayer.getName()+", please enter in Numeric form what you would like to do!");
		System.out.println("----------------------------------------------------------------\n" +
						"|\t1: Mortgage a property\n" +
						"|\t2: Build Houses/Hotels on Square\n" +
						"|\t3: Buy/Sell Properties or Cards with other players");
		
		if(doubleRoll) System.out.println("|\t4: Roll Dice\n"+
				"----------------------------------------------------------------\n");
		else System.out.println("|\t4: Exit\n"+
				"----------------------------------------------------------------\n");
			
		
		int choiceInput = InputOutput.integerMenu(1, 4);
		
		switch(choiceInput) {
			case 1:
				Property propToMortgage = propertyInput(currentPlayer, "Mortgage");
				
				if(!(Checks.isPlayerOwner(propToMortgage, currentPlayer))){
					if(InputOutput.yesNoInput("You do not own the property you have entered, would you like to try again? (y/n)", currentPlayer)){
						//restart pre-dice roll options
						handleUserOption(currentPlayer, doubleRoll);
					}
				}
				//This is for mortgaging a property
				//Need to search for the index and then mortgage it using the below
				propToMortgage.mortgage(currentPlayer);
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
}
