package ie.ucd.game;

import java.util.Scanner;

public class InputOutput {
	private static Scanner input = new Scanner(System.in);
	
	public static boolean yesNoInput(String message,Player player) {
		@SuppressWarnings("resource")
		Scanner yesNoScanner = new Scanner(System.in);
		System.out.println(message);
		String acknowledgement = yesNoScanner.next();
		
		while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
			System.out.println(player.getName()+", please enter a valid response (y/n)");
			acknowledgement = yesNoScanner.next();
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
		int choiceInput = input.nextInt();
		while(!(choiceInput >= lowerBound && choiceInput <= upperBound)) {
			System.out.println("Please enter a choice of a number between "+lowerBound+" and "+upperBound);
			choiceInput = input.nextInt();
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
}
