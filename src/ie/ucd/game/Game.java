package ie.ucd.game;

import java.util.*;
//This will be the file that reads the configuration of the board and asks for user input
//It is a main file
public class Game {
	private static int remainingHouses=32;
	private static int remainingHotels=12;
	public static ArrayList<Player> playerList;
	ArrayList<Square> board = BoardReader.board;
	public static int numPlayersBankrupt=0;
	public Game() {
		
	}
	
	public static int getRemainingHouses() {
		return remainingHouses;
	}
	
	public static int getRemainingHotels() {
		return remainingHotels;
	}
	
	public static void setRemainingHouses(int newHouseCount) {
		remainingHouses = newHouseCount;
	}
	public static void setRemainingHotels(int newHotelCount) {
		remainingHotels = newHotelCount;
	}
	
	
	//This function will check the winner of the game by looping through the player list and checking who has the most money
	//Will return the player object that is the winner and then the main class will finish the game
	//Check winner will be called when 
	
	public static void main(String[] args){
		//We will loop through the board arraylist which is listed at 
		//BoardReader.board
		playerList = Player.createListPlayers();
		
		while(numPlayersBankrupt < 2) {
			//This is the loop that consistently loop through the players of the game
			for(Player currentPlayer:playerList) {
				
				
				Scanner input = new Scanner(System.in);
				int choiceInput = 0;
				
				//This is here for doubles being rolled so you can do as much things as you want
				while(true) {
					//This is where to get a correct input from the user
					while(true) {
						System.out.println("Please enter in Numeric form what you would like to do!");
						System.out.println("1: Roll Dice / 2: Build Houses on Square / 3: Build Hotel on Square / 4: Buy a Property / 5: Sell a Property"
								+ " / 6: Mortgage a property");
						choiceInput = input.nextInt();
						if(choiceInput > 0 && choiceInput < 6) {
							break;
						}
						else {
							System.out.println("Please enter a choice of a number between 1 and 4");
							continue;
						}
					}
					switch(choiceInput) {
						case 1:
							//Rolling the dice instantly. No other choice
							//Dice.rollDice();
							//currentPlayer.movePlayer(Dice.getDieVals());
							break;
						case 2:
							//This is for choosing to build house on a square
							Property.buildHouse()
							break;
						case 3:
							//This is for building hotel on square
							Property.buildHotels();
							break;
						case 4:
							//This is for buying a property
							currentPlayer.buy(); //????? FIXME
							break;
						case 5:
							//This is for selling a property
							currentPlayer.sell(); //FIXME ?? need to sell
							break;
						case 6: 
							//This is for mortgaging a property
							//Need to search for the index and then mortgage it using the below
							CanOwn.mortgage(currentPlayer);
						default:
							break;
					}
					//Roll the dice regardless after they have done all of their things#
					//This will hopefully update the dice roll and allow it to see if a double has been rolled
					Dice.rollDice();
					currentPlayer.movePlayer(Dice.getDieVals());
					//Checks will implement everything in there that is needed such as working on special squares etc or going to jail
					//It needs to see what square it has to know what to do next 
					//FIXME We could have a switch statement and the checksquare returns a value to the main
					Checks.checkSquare(currentPlayer.getLocation());
					
					//Need to implement an input function which will take a parameter of whether they are allowed to rollDice again or not and then the switch statement
					// will change as a result
					System.out.println("Are you done with your turn?[Y/N]");
					String finishedInput = input.next();
					//If asked to finish and didnt rol double, break
					if(finishedInput.toLowerCase() == "y" && Dice.doubleRolled() == false) {
						break;
					}
					//else if((finishedInput.toLowerCase() == "n") || (finishedInput.toLowerCase() == "y" && Dice.doubleRolled() == true) ) {
					else {
						continue;
					}
				}	
			}
			//You can build at any time on whatever square
			//Do you want to Roll dice? 
			//IF it was a roll, i need to move player
			//Check the square
			//Double -Roll again after the decision has been made -> Cannot roll again if it is jail
			//At end of turn, do you want to mortgage properties, buy and sell hotels/houses
			//Check square needs to check if they are on a chance card and them implementSpecialSquare will be able to implement what is happening
			//Ask them if they are finished with their turn
			//increment the playerlist and then do it all again
			//have at bottom of for loop that 
			/*for{
				if (numPlayersBankrupt)
				break;
			}*/
			
		}
		Checks.checkWinner();
		//Will check the winner and finish the game
		
	}
}