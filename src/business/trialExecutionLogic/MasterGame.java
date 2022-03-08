package business.trialExecutionLogic;

import business.PlayerTypeOptions;
import business.playerTypes.Doctor;
import business.playerTypes.Engineer;
import business.playerTypes.Master;
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
            if (player instanceof Engineer) {
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
        Player newPlayer = null;
        if (pass > master.getNumberCredits() / 2) {
            if (player instanceof Engineer) {
                //player.changePlayerType(PlayerTypeOptions.MASTERS);
                newPlayer = new Master(player.getName(), 5);
            }else{
                if (player.checkUpdateStatus()){
                    if (player instanceof Master) {
                        newPlayer = new Doctor(player.getName(), 5);
                    }
                }
            }
        }
        return newPlayer;
    }

}
