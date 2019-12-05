package operations;

import static game.Board.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import cards.TitleDeed;
import game.Game;
import game.Player;
import squares.*;

/**
 * The Input/Output class handles all of the inputs from the user that are used continuously. 
 * These include Yes/no inputs, a title deed operation menu , information about the square a player object is on, an integer menu to make 
 * choices numerically, handling user options when they land on a square and a menu for a player to select another player to interact with
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class InputOutput {


	//method to handle yes/no inputs
	/**
	 * This method handles any times that a yes or no input is used which is taken in the form of a single character "y" or "n".
	 * It reads in the user input and returns the the acknowledgement or no acknowledgement
	 * @param message The message which is to be presented to the user
	 * @param player The Player object which is making the input
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 * @return
	 */
	public static boolean yesNoInput(String message, Player player, BufferedReader userInput) {
		//If no buffered reader given, then we can create one
		if(userInput==null){userInput=new BufferedReader(new InputStreamReader(System.in));}
		//Print the message to the screen
		System.out.println(message);
		try {
			//Read the user's input
			String acknowledgement = userInput.readLine();
			//ensure the player enters a valid response
			while(!(acknowledgement.equalsIgnoreCase("y") || acknowledgement.equalsIgnoreCase("n"))) {
				System.out.println(player.getName()+", please enter a valid response (y/n)");
				acknowledgement = userInput.readLine();
			}
			//Return a y
			return acknowledgement.equalsIgnoreCase("y");
		}
		catch(Exception e){
			System.out.println("Exception: "+e);
			return yesNoInput(message,player, null);
		}
	}

	//menu to allow players to select a titledeed card to conduct an operation (mortgage/improve etc)
	//house hotels argument ensures only instances of property are shown when true
	/**
	 * This is used for Player objects to select a TitleDeed card to conduct an operation such as mortgaging that CanOwn object associated with the 
	 * TitleDeed card or perhaps build on the site. It loops through the player object's title deed cards to check if they can mortgage or improve the given object 
	 * It then can decide what the type of operation is such as houses or hotels
	 * @param player The player object which is acting on this method
	 * @param operation The operation the player object wishes to do 
	 * @param housesHotels Boolean to determine if it is related to houses or hotels
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 * @return
	 */
	public static TitleDeed titleDeedOperationMenu(Player player, String operation, boolean housesHotels, BufferedReader userInput){
		try {
			if(userInput==null) userInput=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please select the title deed card you wish to " + operation);
			ArrayList<TitleDeed> houseHotelList = new ArrayList<>();
			int choiceInput;

			//loop through available title deeds

			for (int i = 0; i < player.getTitleDeedList().size(); i++) {
				if (!player.getTitleDeedList().get(i).getBankruptcyTradeStatus().isEmpty()) {
					continue;
				}
				//if house and hotels is true so store only instances of property
				if (housesHotels && (player.getTitleDeedList().get(i).getOwnableSite() instanceof Property)) {

					houseHotelList.add(player.getTitleDeedList().get(i));
				}
				//not a house or hotel operation so print any titledeed
				else if (!housesHotels) {
					System.out.println("[" + i + "] " + player.getTitleDeedList().get(i).getCardDesc());
				}
			}
			//print house hotel list
			if (housesHotels) {
				for (int i = 0; i < houseHotelList.size(); i++) {
					System.out.println("[" + i + "] " + houseHotelList.get(i).getCardDesc());
				}
				System.out.println("[" + (houseHotelList.size()) + "] Cancel");
				choiceInput = integerMenu(0, houseHotelList.size(), userInput);
				if (choiceInput == houseHotelList.size()) {
					return null;
				} else return houseHotelList.get(choiceInput);
			}
			//print cancel at the end for normal operation and receive user choice
			else {
				System.out.println("[" + (player.getTitleDeedList().size()) + "] Cancel");
				choiceInput = integerMenu(0, player.getTitleDeedList().size(), userInput);
				//cancel has been chosen so return null
				if (choiceInput == player.getTitleDeedList().size()) {
					return null;
				}
				//a titledeed has been selected so return
				else return player.getTitleDeedList().get(choiceInput);
			}
		}
		catch(Exception e){
			System.out.println("Exception: "+e);
			return null;
		}
	}

	//display information about a square
	/**
	 * This method is used to print a number of details about the current square that a Player object is on on the board and may influence their decision in what they do
	 * next
	 * @param index The index on the board of the square
	 */
	public static void squareInformation(int index){
		//type canown requires additional information
		System.out.println("You have landed on: " + board.get(index).getName()+" (Index: "+index+")");
		switch (board.get(index).getClass().getSimpleName()){
			case "Property":
				((Property)board.get(index)).printInstanceData();
				break;
			case "Train":
				((Train)board.get(index)).printInstanceData();
				break;
			case "Utility":
				((Utility)board.get(index)).printInstanceData();
			default:
				break;
		}
//		if(board.get(index) instanceof CanOwn){
//			System.out.println("You have landed on: " + board.get(index).getName()+" (Index: "+index+")");
//			if(null == (((CanOwn) board.get(index)).getTitleDeedCard().getOwner())){
//				System.out.println("Owner: None") ;
//			}
//			//Print more details
//			else{
//				System.out.println("Owner: "+(((CanOwn) board.get(index)).getTitleDeedCard().getOwner().getName())) ;
//			}
//			//If its a property, I can print the colour and number of houses/hotels
//			if (board.get(index) instanceof Property) {
//				System.out.println("Colour: "+((Property) board.get(index)).getSquareColour()+"\nHouses: "
//						+((Property) board.get(index)).getNumHouses()+"\nHotels: "+((Property) board.get(index)).getNumHotels());
//			}
//		}
//		else{
//			System.out.println("You have landed on "+ board.get(index).getName()+" (Index: "+index+")");
//		}
	}
	/**
	 * This creates the list of players which will take part in a game. It prompts the user to input players in a specific form with
	 * Name,token. The token has to be a colour which are printed on the screen. It then creates Player objects for each of the players entered with
	 * minimum 2 players to maximum 6.
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 * @return listPlayers, an ArrayList of type Player with the Players for the game
	 */
	public static ArrayList<Player> createListPlayers(BufferedReader userInput) {
		try {
			if(userInput==null){userInput=new BufferedReader(new InputStreamReader(System.in));}
			ArrayList<Player> listPlayers = new ArrayList<Player>();
			ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList("blue", "red", "green", "black", "orange", "yellow"));


			System.out.println("How many players will be playing the game(In range 2-6)?");
			int numPlayers = InputOutput.integerMenu(2, 6, userInput);
			System.out.println("You have specified " + numPlayers + " players to play the game");
			//Get the players to be entered by the user -> Have a loop that asks for that number of players.
			if (numPlayers >= 2 && numPlayers <= 6) {
				for (int i = 0; i < numPlayers; i++) {
					String line;
					String[] lineVector = new String[2];
					String name, token;
					while (true) {
						System.out.println("Please enter the relevant details for each player in the format below:");
						System.out.println("\t\t\tName, Token");
						System.out.println("Token picked must be one of the following: " + tokenList +
								"\n----------------------------------------------------------------");
						line = userInput.readLine();
						//Trim the whitespace first before splitting
						if (!line.contains(",")) System.err.println("Please use a comma (,) to separate input");
						else {
							lineVector = line.split(",");
							name = lineVector[0].trim();
							token = lineVector[1].trim();
							if (!tokenList.contains(token)) {
								System.err.println("Token not part of list. Enter details of Player again!");
								continue;
							} else if (name.length() == 0) {
								System.err.println("Name of Player has not been entered or is not valid. Enter details of Player again!\n");
								continue;
							} else {
								//Remove token from the array
								tokenList.remove(token);
								//Do I need to access them using the index though. I suppose I will go through them in
								listPlayers.add(new Player(name, token)); //This will add a new player to the list of players
								//I have added each player in the range of numPlayers to be in the listPlayers array.
								//Running this will return the list of players which I can set in main
								break;
							}
						}
					}
				}
			}

			//Inside of the main, we will return this list of players
			return listPlayers;
		}
		catch(Exception e){
			System.out.println("Exception: "+e);
			return null;
		}
	}

	/**
	 * This integer menu is used for when you are given a choice and the you can provide upper and lower bounds when choosing the input.
	 * It presents the user on the command with a statement of a range of digits and you enter the relevant number. 
	 * @param lowerBound The lower bound of the integer menu as an integer
	 * @param upperBound The lower bound of the integer menu as an integer
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 * @return
	 */
	public static int integerMenu(int lowerBound, int upperBound, BufferedReader userInput){
		try {
			if(userInput==null){userInput=new BufferedReader(new InputStreamReader(System.in));}
			System.out.println("Please enter a choice of a number between " + lowerBound + " and " + upperBound + ":");
			int choiceInput =  Integer.parseInt(userInput.readLine());
			if (!(choiceInput >= lowerBound && choiceInput <= upperBound)) {
				choiceInput = integerMenu(lowerBound,upperBound, userInput);
			}
			return choiceInput;
		}
		catch(NumberFormatException ex){
			System.err.println("Could not convert input string: "+ex.getMessage());
			int choiceInput = integerMenu(lowerBound,upperBound, userInput);
			return choiceInput;
		}
		catch(IOException e){
			System.out.println("Exception: "+e);
			return -1;
		}
	}

	/**
	 * This class handles the user option from the integer menu. When an Integer menu number is entered by the user, it is interpreted by this method and actions are
	 * carried out as a result such as mortgaging, building, buying/selling or just printing a player's status.
	 * These choices are handled by a switch statement.
	 * @param currentPlayer The current player object which entered the options
	 * @param doubleRoll Whether a double has been rolled
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 */
	public static void handleUserOption(Player currentPlayer, boolean doubleRoll, BufferedReader userInput) {
		if(userInput==null){userInput=new BufferedReader(new InputStreamReader(System.in));}
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


		int choiceInput = InputOutput.integerMenu(1, 5, userInput);

		switch(choiceInput) {
			case 1:
				System.out.println("[0] Mortgage\n[1] Demortgage");
				choiceInput = integerMenu(0,1, userInput);
				if(choiceInput==0) {
					TitleDeed titleDeedToMortgage = titleDeedOperationMenu(currentPlayer, "mortgage", false, null);
					if (null == titleDeedToMortgage) {
						System.out.println("Cancelling Operation");
					} else {
						CanOwn propToMortgage = (titleDeedToMortgage.getOwnableSite());
						propToMortgage.mortgage(currentPlayer, false);
					}
				}
				else{
					TitleDeed titleDeedToDemortgage = titleDeedOperationMenu(currentPlayer, "mortgage", false, null);
					if (null == titleDeedToDemortgage) {
						System.out.println("Cancelling Operation");
					} else {
						CanOwn propToDemortgage = (titleDeedToDemortgage.getOwnableSite());
						propToDemortgage.demortgage(false,false);
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
		if((choiceInput<5) && doubleRoll && InputOutput.yesNoInput("Would you like to do an additional action before rolling the dice? (y/n)", currentPlayer, userInput)) {
			handleUserOption(currentPlayer, true,userInput);
		}
	}
	/**
	 * This is a method that handles the interaction between 2 players. The player who wants to interact must specify the player object as an argument and then 
	 * we can search through the global playerList to find the player they wish to interact with. 
	 * @param selectingPlayer The Player who is selecting a player to interact with
	 * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
	 * @return playerMenu.get(choiceInput), The player object you wish to interact with
	 */
    public static Player selectPlayerMenu(Player selectingPlayer, BufferedReader userInput){
		if(userInput==null){
			userInput = new BufferedReader(new InputStreamReader(System.in));
		}
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

		choiceInput = integerMenu(0,playerMenu.size(), userInput);
		return playerMenu.get(choiceInput);
	}
}
