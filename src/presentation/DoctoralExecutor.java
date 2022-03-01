package presentation;

import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.DoctoralGame;
import business.typeTrials.DoctoralThesis;

import java.io.IOException;
import java.util.LinkedList;

public class DoctoralExecutor {
    /**
     * En las clases executor, hace falta ir llamando individualmente a pequeños metodos
     * publicos de las clases game. No podemos actualizar toda la info del jugador a la vez
     * ya que como se deben in mostrando cosas (y el Game no debería), es necesario ir
     * actualizando los datos poco a poco para poder ir mostrando desde el executor
     */
    public void playTrial (DoctoralThesis doctoralThesis) throws IOException {
        ViewController view = new ViewController();
        DoctoralGame doctoralGame = null;
        TeamManager teamManager = null;
        boolean passed;
        int i = 0;
        LinkedList<String> namesUpdatedType = new LinkedList<>();

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                passed = doctoralGame.checkPassed(doctoralThesis, player);
                player = doctoralGame.updatePI(player, passed);
                if (passed) {
                    view.showMessageLine(player.getPlayerType() + " " + player.getName() + "was successful. Congrats! PI count: " + player.getPI());
                } else {
                    view.showMessageLine(player.getPlayerType() + " " + player.getName() + "was not successful. Sorry... PI count: " + player.getPI());
                }
                player = doctoralGame.checkUpdateStatus(player, passed);
                teamManager.updatePlayer(i, player);
                if (passed) {
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
