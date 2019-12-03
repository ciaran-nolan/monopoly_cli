package ie.ucd.game;

public class Jail {

    //FIXME fix jail condition with immediate exit
    public static void sendToJail(Player jailedPlayer){
        System.out.println("You have been sent to Jail.");
        //set location to 10, they will not pass go with this method
        jailedPlayer.setInJail(true);
        jailedPlayer.setLocation(10);
    }

    public static void removeFromJail(Player jailedPlayer){
        jailedPlayer.setInJail(false);
        jailedPlayer.setJailMoves(0);
        jailedPlayer.movePlayer(Dice.getDieVals());
        Checks.checkSquare(jailedPlayer.getLocation(),jailedPlayer);
    }

    private static void handleJailFreeCardUsage(Player jailedPlayer){
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
        removeFromJail(jailedPlayer);
    }

    private static void handleFinalRollAttempt(Player jailedPlayer){
        int jailExitChoice = 0;
        if(jailedPlayer.getJailCard().size()==0){
            System.out.println("You have rolled for the third time without getting doubles, you must pay the £50 fine");
            if(!Checks.enoughFunds(jailedPlayer, 50)){
                System.out.println("You do not have enough funds to pay the jail fee");
                jailedPlayer.bankrupt(null);
            }
            else {
                jailedPlayer.reduceMoney(50,null);
                removeFromJail(jailedPlayer);
            }
        }
        else{
            System.out.println("You have rolled for the third time without getting doubles.");
            if(!Checks.enoughFunds(jailedPlayer, 50)) {
                System.out.println("You do not have enough funds to pay the fine, so you must use a get out of jail free card");
            }
            else{
                System.out.println("You have rolled for the third time without getting doubles. Please Select an option:\n[0]Use get out of jail free card\n[1]Pay £50 fine");
                jailExitChoice = InputOutput.integerMenu(0,1);
            }
            if(jailExitChoice==0){
                handleJailFreeCardUsage(jailedPlayer);
            }
            else{
                jailedPlayer.reduceMoney(50,null);
                removeFromJail(jailedPlayer);
            }
        }
    }
    public static void handleJailMove(Player jailedPlayer){
        //print jail status
        boolean doubleRoll;
        int jailExitChoice = 0;
        System.out.println("You are in jail.\nYou have "+jailedPlayer.getJailCard().size()+" get out of jail free cards"
        +"\nYou have rolled "+jailedPlayer.getJailMoves()+" times, without rolling a double.");

        //check if user wants to try roll for doubles or exit jail immediately via fine or card
        InputOutput.handleUserOption(jailedPlayer,false);
        System.out.println("Would you like to:\n[0] attempt to roll the dice\n[1] exit jail by paying a fine");
        //for choosing what action from the integer menu

        //check if they have jail free card, if not, dont display the option to choose
        if(jailedPlayer.getJailCard().size()>0){
            System.out.println("[2]Use Get out of jail free card");
            jailExitChoice = InputOutput.integerMenu(0,2);
        }
        else{
            jailExitChoice = InputOutput.integerMenu(0,1);
        }

        if(jailExitChoice==0){
            //they have decided to roll the dice
            Dice.rollDice();
            doubleRoll = Dice.isDoubleRoll();
            //check if they have rolled a double
            if(doubleRoll){
                System.out.println("You have rolled doubles, you now exit jail and move "+Dice.getDieVals()+" places");
                //take out of jail
                removeFromJail(jailedPlayer);
            }
            else{
                jailedPlayer.setJailMoves(jailedPlayer.getJailMoves()+1);
                if(jailedPlayer.getJailMoves()==3){
                    handleFinalRollAttempt(jailedPlayer);
                }
            }
        }
        else if(jailExitChoice == 1){
            if(!Checks.enoughFunds(jailedPlayer, 50)){
                System.out.println("You do not have enough funds to pay the fine, you must roll");
                Dice.rollDice();
                doubleRoll=Dice.isDoubleRoll();
                jailedPlayer.setJailMoves(jailedPlayer.getJailMoves()+1);
                if(doubleRoll){
                    System.out.println("You have rolled doubles, you now exit jail and move "+Dice.getDieVals()+" places");
                    removeFromJail(jailedPlayer);
                }
                else if(jailedPlayer.getJailMoves()==3){
                    handleFinalRollAttempt(jailedPlayer);
                }
            }
            else {
            	jailedPlayer.reduceMoney(50,null);
            	removeFromJail(jailedPlayer);
            }
        }
        else{
            handleJailFreeCardUsage(jailedPlayer);
        }
    }
}
