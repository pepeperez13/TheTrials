package business.trialExecutionLogic;

import business.PlayerTypeOptions;
import business.playerTypes.Player;
import business.typeTrials.MasterStudies;

import java.util.Random;

public class MasterGame {

    public int checkPassed (MasterStudies masterStudies, Player player) {
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

    // Necesitamos que este sea un metodo separado del primero, ya que ambos necesitan retornar cosas diferentes
    public Player updatePI (int pass, Player player, MasterStudies master){
        if (pass > master.getNumberCredits()/2) {
            if (player.getPlayerType() == PlayerTypeOptions.ENGINEER) {
                player.setPi(10);
            }else {
                player.incrementPI(3);
            }
        } else {
            player.decrementPI(3);
        }
        return player;
    }


    public Player checkUpdateStatus (int pass, Player player, MasterStudies master) {
        if (pass > master.getNumberCredits() / 2) {
            if (player.getPlayerType() == PlayerTypeOptions.ENGINEER) {
                player.changePlayerType(PlayerTypeOptions.MASTERS);
            }
        }
        return player;
    }

}
