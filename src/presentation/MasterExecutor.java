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
        int i = 0;

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                // Falta configurar una especie de gameLogic para cada tipo de prueba
                // (uno para doctoral, otro para budget y otro para master. El de paper ya es el de la primera fase)
                // O creo que ya lo tenemos hecho, solo faltaria conectar la siguiente linea con las clases
                // correspondientes que hay en la carpeta trialExecutionLogic, que vendrian a ser el gameLogic
                masterGame.passCredits(masterStudies, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
