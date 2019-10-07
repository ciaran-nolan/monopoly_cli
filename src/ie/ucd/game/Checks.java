package ie.ucd.game;
import ie.ucd.game.*;

public class Checks {

	public void canBuy(CanOwn ownableCard) {
		if(ownableCard.owner == null) {
			ownableCard.buy(player, listPlayers);
		}
		else {
			
		}
	}
}
