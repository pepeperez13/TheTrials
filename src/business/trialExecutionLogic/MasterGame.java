package business.trialExecutionLogic;

import business.PlayerTypeOptions;
import business.playerTypes.Player;
import business.typeTrials.MasterStudies;

import java.util.Random;

public class MasterGame {

    public int checkAndUpdatePI(MasterStudies masterStudies, Player player) {
        Random random = new Random();
        int randomNumber = random.nextInt(101);
        int pass = 0, deny = 0;

        //Comprobar uno a uno cúantos créditos pasa
        for (int i = 0; i <= masterStudies.getNumberCredits() ; i++) {
            if (randomNumber <= masterStudies.getProbability()) {
                pass++;
            }
            else {
                deny++;
            }
        }
        return pass;
    }


    public void checkPass (int pass, int deny, Player player) {
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
