package presentation;

import business.TeamManager;
import business.trialExecutionLogic.PaperGame;
import business.typeTrials.PaperPublication;

import java.io.IOException;
import java.util.LinkedList;

public class PaperExecutor {
    /**
     *
     * Falta arreglar cosas (no mostramos al final los jugadores que han evolucionado de tipo
     */

    public void playTrial (PaperPublication paperPublication) throws IOException { //Este método falla
        TeamManager teamManager = null;
        ViewController view = null;
        PaperGame paperGame = null;
        LinkedList<String> namesUpdatedType = new LinkedList<>();
        int i = 0;

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                player = paperGame.publishArticle(paperPublication, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
                // Si se ha actualizado su status, lo guardamos para mostrarlo
                if (paperGame.checkUpdateStatus(player)) {
                    namesUpdatedType.add(player.getName() + " " + player.getPlayerType());
                }
            }
            i++;
        }

        // Mostramos los jugadores que hayan cambiado su status
        for (String name : namesUpdatedType){
            String[] parts = name.split(" ");
            view.showMessage(parts[0] + " is now a " + parts[1] + "(with 5 PI)");
        }
    }
}
