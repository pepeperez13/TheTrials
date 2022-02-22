package business;

import business.playerTypes.Player;
import business.typeTrials.MasterStudies;

import java.util.Random;

public class MasterGame {
    public void passCredits (MasterStudies masterStudies, Player player) {
        Random random = new Random();
        int randomNumber = random.nextInt(101);
        int pass = 0, deny = 0;

        //Comprobar cuántos creditos pasa
        for (int i = 0; i <= masterStudies.getNumberCredits() ; i++) {
            if (randomNumber <= 100) {
                pass++;
            }
            else {
                deny++;
            }
        }
        checkPass(pass, deny, player);
    }

    private void checkPass (int pass, int deny, Player player) {
        if (pass > deny) {
            if (player.getPlayerType() == PlayerTypeOptions.ENGINEER) {
                player.changePlayerType(PlayerTypeOptions.MASTERS);
            } else {
                player.incrementPI(3);
                player.checkUpdateStatus();
            }
        } else {
            player.decrementPI(3);
        }
    }
}
