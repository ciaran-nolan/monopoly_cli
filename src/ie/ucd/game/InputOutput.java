package ie.ucd.game;

import java.util.Scanner;

public class InputOutput {
	private static Scanner input = new Scanner(System.in);
	
	public static boolean yesNoInput(String message,Player player) {
		@SuppressWarnings("resource")
		Scanner yesNoScanner = new Scanner(System.in);
		System.out.println(message);
		String acknowledgement = yesNoScanner.nextLine();
		
		while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
			System.out.println(player.getName()+", please enter a valid response (y/n)");
			acknowledgement = yesNoScanner.nextLine();
		}
		if(acknowledgement.equalsIgnoreCase("y")){
			return true;
		}
		else {
			return false;
			}
	}
	
	public static boolean canBuildHouses(CanOwn ownableCard) {
		//only cards of type property can have houses
		if(ownableCard instanceof Property) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static int integerMenu(int lowerBound, int upperBound){
		int choiceInput = Integer.parseInt(input.nextLine());
		while(!(choiceInput >= lowerBound && choiceInput <= upperBound)) {
			System.out.println("Please enter a choice of a number between "+lowerBound+" and "+upperBound);
			choiceInput = Integer.parseInt(input.nextLine());
		}
		
		return choiceInput;
	}
	
	public static Property propertyInput(Player player, String propAction) {
		System.out.println("Please enter the name of the poperty you wish to "+ propAction);
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
	
	public static void handleUserOption(Player currentPlayer,boolean doubleRoll) {
		System.out.println(currentPlayer.getName()+", please enter in Numeric form what you would like to do!");
		System.out.print("1: Mortgage a property / 2: Build Houses/Hotels on Square / "
		+"3: Buy/Sell Properties or Cards with other players");
		
		if(doubleRoll) System.out.println(" 4: Roll Dice /");
		else System.out.println(" 4: Exit /");
			
		
		int choiceInput = InputOutput.integerMenu(1, 4);
		
		switch(choiceInput) {
			case 1:
				Property propToMortgage = InputOutput.propertyInput(currentPlayer, "Mortgage");
				
				if(!(Checks.isPlayerOwner((CanOwn) propToMortgage, currentPlayer))){
					if(InputOutput.yesNoInput("You do not own the property you have entered, would you like to try again? (y/n)", currentPlayer)){
						//restart pre-dice roll options
						handleUserOption(currentPlayer, doubleRoll);
					}
				}
				//This is for mortgaging a property
				//Need to search for the index and then mortgage it using the below
				propToMortgage.mortgage(currentPlayer);
			case 2:
				//This is for choosing to build house on a square
				Property.buildHouses(currentPlayer);				
			case 3:
				//This is for buying a property
				Transactions.playerToPlayerTrade(currentPlayer); 
			case 4: 
				 break;
			default:
				System.out.println("Your input did not correspond to any provided actions");
				break;
		}
	}
}
