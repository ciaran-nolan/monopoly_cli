package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cards.Chance;
import cards.CommunityChest;
import operations.Checks;
import operations.InputOutput;

/**
 * This is the Jail class and handles all of the interactions of a player with the jail square such as sending to Jail, removing from jail,
 * using a get out of jail free card, handling a jail move such as when you are leaving
 * It has no class variables and is purely an implementation class of methods
 * @author Robert Keenan & Ciaran Nolan
 *
 */
public class Jail {

	/**
	 * The method which sends a player to jail in the argument. It prints that the player has been sent to jail.
	 * It then uses the player method setInJail to change their jail status and set their location to square number 10(Jail)
	 * @param jailedPlayer The player object to be jailed
	 */
    public static void sendToJail(Player jailedPlayer){
        System.out.println("You have been sent to Jail.");
        //set location to 10, they will not pass go with this method
        jailedPlayer.setInJail(true);
        jailedPlayer.setLocation(10);
    }

    /**
     * Removing a player from jail. If they didn't roll a double, they can roll the dice and move to that square. Then we perform CheckSquare
     * @param jailedPlayer The player that is jailed and will now be freed from jail
     * @param rolledDouble Whether or not the player has rolled a double to free them from jail
     * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
     */
    public static void removeFromJail(Player jailedPlayer, boolean rolledDouble, BufferedReader userInput){
        System.out.println("Test");
        if(userInput==null) userInput = new BufferedReader(new InputStreamReader(System.in));
        jailedPlayer.setInJail(false);
        jailedPlayer.setJailMoves(0);
        if(!rolledDouble) {
        	Dice.rollDice();
        }
        jailedPlayer.movePlayer(Dice.getDieVals());
        Checks.checkSquare(jailedPlayer.getLocation(),jailedPlayer, userInput);
    }


    /**
     * This handles using a get out of jail free card to leave jail. It will check what pile of cards it came from, either community chest or chance, add it back to
     * back to the bottom of the pile of cards and then remove it from the Arraylist of jail free cards that the jailedPlayer object possesses.
     * It then calls removeFromJail() to remove them from jail
     * @param jailedPlayer The player who is jailed who is using the get out of jail free card
     * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
     */
    private static void handleJailFreeCardUsage(Player jailedPlayer,  BufferedReader userInput){
        if(userInput==null) userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You have used a Get Out of Jail Free card to exit jail");
        //I now need to add the card back into the relevant array. I can see that by the Array that is less than 16
        if(jailedPlayer.getJailCard().get(0) instanceof CommunityChest) {
        	Board.communityChests.add((CommunityChest)jailedPlayer.getJailCard().get(0));
        	jailedPlayer.getJailCard().remove(0);
        }
        else{
            jailedPlayer.getJailCard().remove(0);
            Board.chances.add((Chance)jailedPlayer.getJailCard().get(0));
        }
        removeFromJail(jailedPlayer,false, userInput);
    }
    /**
     * This handles the rolls a player makes when in jail. They can either pay the fine (if they have enough money), use a get out of jail free
     * card if they have one or if they do not have enough for a fine, they will go bankrupt
     * @param jailedPlayer The Player object who is jailed
     * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
     */

    private static void handleFinalRollAttempt(Player jailedPlayer,  BufferedReader userInput){
        if(userInput==null) userInput = new BufferedReader(new InputStreamReader(System.in));
        int jailExitChoice = 0;
        //If they don't have get out of jail free card
        //Check if they can afford the fine and if not, deem them bankrupt and try raise money as they owe the bank
        if(jailedPlayer.getJailCard().size()==0){
            System.out.println("You have rolled for the third time without getting doubles, you must pay the £50 fine");
            if(!Checks.enoughFunds(jailedPlayer, 50)){
                System.out.println("You do not have enough funds to pay the jail fee");
                jailedPlayer.bankrupt(null);
            }
            else {
                jailedPlayer.reduceMoney(50,null);
                removeFromJail(jailedPlayer,false,userInput);
            }
        }
        //They have a get out of jail free card and are given the option of a fine or using a get out of jail free card
        else{
            System.out.println("You have rolled for the third time without getting doubles.");
            if(!Checks.enoughFunds(jailedPlayer, 50)) {
                System.out.println("You do not have enough funds to pay the fine, so you must use a get out of jail free card");
            }
            else{
                System.out.println("Please Select an option:\n[0]Use get out of jail free card\n[1]Pay €50 fine");
                jailExitChoice = InputOutput.integerMenu(0,1, userInput);
            }
            if(jailExitChoice==0){
                //If they choose to use their get out of jail free card
                handleJailFreeCardUsage(jailedPlayer,userInput);
            }
            else{
            	//If they choose to pay the fine
                jailedPlayer.reduceMoney(50,null);
                removeFromJail(jailedPlayer,false,userInput);
            }
        }
    }

    /**
     * This method handles the moves they make while the jailedPlayer object is in jail such as rolling to see can they roll a double
     * ,paying their fine or using a get out of jail free card
     * @param jailedPlayer The player who is currently in jail who is asked to either roll a dice, pay a fine or use a get out of jail free card if they have one
     * @param userInput BufferedReader used for simulating user input for much more complex tests in JUnit
     */
    public static void handleJailMove(Player jailedPlayer){
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        //print jail status
        boolean doubleRoll;
        int jailExitChoice = 0;
        System.out.println("You are in jail.\nYou have "+jailedPlayer.getJailCard().size()+" get out of jail free cards"
        +"\nYou have rolled "+jailedPlayer.getJailMoves()+" times, without rolling a double.");

        //check if user wants to try roll for doubles or exit jail immediately via fine or card
        InputOutput.handleUserOption(jailedPlayer,false, userInput);
        System.out.println("Would you like to:\n[0] attempt to roll the dice\n[1] exit jail by paying a fine");
        //for choosing what action from the integer menu

        //check if they have jail free card, if not, dont display the option to choose
        if(jailedPlayer.getJailCard().size()>0){
            System.out.println("[2]Use Get out of jail free card");
            jailExitChoice = InputOutput.integerMenu(0,2, userInput);
        }
        else{
            jailExitChoice = InputOutput.integerMenu(0,1, userInput);
        }
        //Decide to roll the dice
        if(jailExitChoice==0){
            //they have decided to roll the dice
            Dice.rollDice();
            doubleRoll = Dice.isDoubleRoll();
            //check if they have rolled a double
            if(doubleRoll){
                System.out.println("You have rolled doubles, you now exit jail and move "+Dice.getDieVals()+" places");
                //take out of jail
                removeFromJail(jailedPlayer,true,userInput);
            }
            else{
                jailedPlayer.setJailMoves(jailedPlayer.getJailMoves()+1);
                if(jailedPlayer.getJailMoves()==3){
                    handleFinalRollAttempt(jailedPlayer,userInput);
                }
            }
        }
        //Pay the fine if they can
        else if(jailExitChoice == 1){
            if(!Checks.enoughFunds(jailedPlayer, 50)){
                System.out.println("You do not have enough funds to pay the fine, you must roll");
                Dice.rollDice();
                doubleRoll=Dice.isDoubleRoll();
                jailedPlayer.setJailMoves(jailedPlayer.getJailMoves()+1);
                //If they roll a double they can leave jail
                if(doubleRoll){
                    System.out.println("You have rolled doubles, you now exit jail and move "+Dice.getDieVals()+" places");
                    removeFromJail(jailedPlayer,true,userInput);
                }
                //If they have stayed in jail for 3 rounds
                else if(jailedPlayer.getJailMoves()==3){
                    handleFinalRollAttempt(jailedPlayer,userInput);
                }
            }
            //They decide to pay the fine and are removed from jail
            else {
            	jailedPlayer.reduceMoney(50,null);
            	removeFromJail(jailedPlayer,false,userInput);
            }
        }
        //Use their get out of jail free card if they can
        else{
            handleJailFreeCardUsage(jailedPlayer,userInput);
        }
    }
}
