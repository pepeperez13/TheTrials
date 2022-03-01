package presentation;

import business.PlayerTypeOptions;
import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.MasterGame;
import business.typeTrials.MasterStudies;

import java.io.IOException;
import java.util.LinkedList;

public class MasterExecutor {
    /**
     * En las clases executor, hace falta ir llamando individualmente a pequeños metodos
     * publicos de las clases game. No podemos actualizar toda la info del jugador a la vez
     * ya que como se deben in mostrando cosas (y el Game no debería), es necesario ir
     * actualizando los datos poco a poco para poder ir mostrando desde el executor
     */
    public void playTrial (MasterStudies masterStudies) throws IOException {
        TeamManager teamManager = null;
        ViewController view = null;
        MasterGame masterGame = null;
        int passed;
        int i = 0;
        LinkedList<String> namesUpdatedType = new LinkedList<>();

        for (Player player : teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                passed = masterGame.checkPassed(masterStudies, player); // Publicamos articulo
                view.showMessageLine(player.getName() + " passed " + passed + "/" + masterStudies.getNumberCredits() + " ECTS"); // Mostramos si se aprueba o no
                player = masterGame.updatePI(passed, player, masterStudies); // Actualizamos la puntuación del jugador (no su tipo)
                if (passed > masterStudies.getNumberCredits() / 2) {
                    view.showMessageLine("Congrats! PI count: " + player.getPI() + "\n");
                } else {
                    view.showMessageLine("Sorry... PI count: " + player.getPI() + "\n");
                }
                player = masterGame.checkUpdateStatus(passed, player, masterStudies);
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                if (passed > masterStudies.getNumberCredits() / 2 && player.getPlayerType() == PlayerTypeOptions.ENGINEER) {
                    namesUpdatedType.add(player.getName() + " " + player.getPlayerType());
                }
            }
            i++;
        }

        for (String name : namesUpdatedType){
            String[] parts = name.split(" ");
            view.showMessage(parts[0] + " is now a " + parts[1] + "(with 5 PI)");
        }

    }
}
