package presentation;

import business.GameLogic;
import business.TeamManager;
import business.playerTypes.Player;
import business.typeTrials.PaperPublication;

import java.io.IOException;

public class PaperExecutor {

    public void playTrial (Object o, TeamManager teamManager, ViewController view, GameLogic gameLogic) throws IOException { //Este método falla
        int i = 0;

        PaperPublication paper = PaperPublication.class.cast(o);

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                player = gameLogic.publishArticle(paper, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }
}
