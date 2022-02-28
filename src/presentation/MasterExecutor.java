package presentation;

import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.MasterGame;
import business.typeTrials.MasterStudies;

import java.io.IOException;

public class MasterExecutor {

    public void playTrial(MasterStudies masterStudies) throws IOException {
        TeamManager teamManager = null;
        ViewController view = null;
        MasterGame masterGame = null;
        int passed;
        int i = 0;

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                passed = masterGame.checkAndUpdatePI(masterStudies, player); // Publicamos articulo
                view.showMessageLine(player.getName() + " passed " + passed + "/" + masterStudies.getNumberCredits() + " ECTS");
                if (passed > masterStudies.getNumberCredits()) {
                    view.showMessageLine("Congrats! PI count: " + player.getPI() + "\n");
                }else{
                    view.showMessageLine("Sorry... PI count: " + player.getPI() + "\n");
                }
                // Estas dos lineas faltan cambiarlas
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
