package presentation;

import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.PaperGame;
import business.typeTrials.PaperPublication;

import java.io.IOException;

public class PaperExecutor {
    /**
     *
     * Falta arreglar cosas (no mostramos al final los jugadores que han evolucionado de tipo
     */

    public void playTrial (PaperPublication paperPublication) throws IOException { //Este método falla
        TeamManager teamManager = null;
        ViewController view = null;
        PaperGame paperGame = null;
        int i = 0;

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                player = paperGame.publishArticle(paperPublication, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
