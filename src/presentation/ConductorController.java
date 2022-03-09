package presentation;

import business.Edition;
import business.EditionManager;
import business.ManagersTrials.GenericTrialManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;
import business.playerTypes.Engineer;
import business.playerTypes.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class ConductorController {
    private PaperPublicationManager paperPublicationManager;
    private EditionManager editionManager;
    private TeamManager teamManager;
    private ViewController view;
    private GameExecutor gameExecutor;
    private GenericTrialManager genericTrialManager;

    public ConductorController(PaperPublicationManager paperPublicationManager, EditionManager editionManager, TeamManager teamManager, ViewController view, GameExecutor gameExecutor, GenericTrialManager genericTrialManager) {
        this.paperPublicationManager = paperPublicationManager;
        this.editionManager = editionManager;
        this.teamManager = teamManager;
        this.view = view;
        this.gameExecutor = gameExecutor;
        this.genericTrialManager = genericTrialManager;
    }
    public int run (int finalIndex) throws IOException {
        // Variable que nos permitirá saber qué prueba se ha ejecutado la última, y por tanto si la próxima
        // vez que entremos tiene que continuar la ejecución o empezar de nuevo
        view.showMessage("\nEntering execution mode ...");
        if (!checkYear(editionManager.getEditions())) {
            view.showMessage("\nNo edition is defined for the current year (2021)");
            view.showMessage("\nShutting down...");
        } else {
            view.showMessage("\n--- The Trials 2021 ---\n"); //Ojo con la fecha
            // Siempre que finalIndex valga 0, se tendran que volver a pedir los jugadores
            if (finalIndex == 0) {
                savePlayers();
            }
            // Ejecutamos la edición desde la edición correspondientes
            finalIndex = playTrials(editionManager.getEditionCurrentYear().getNumTrials(), finalIndex);
            // Borramos del fichero los jugadores eliminados y obtenemos si todos los jugadores tienen PI = 0 o no (equipo pierde)
            boolean dead = teamManager.checkDeadPlayers();
            // Si se ha llegado a la última prueba y los jugadores no han perdido
            if (!dead && finalIndex == editionManager.getEditionCurrentYear().getNumTrials()) {
                view.showMessage("\nTHE TRIALS 2022 HAVE ENDED - PLAYERS WON");
                teamManager.removeAllPlayers();
                finalIndex = 0;
            } else {
                if (dead) {
                    view.showMessage("\nExecution ended... Players lost!");
                    teamManager.removeAllPlayers();
                    finalIndex = editionManager.getEditionCurrentYear().getNumTrials(); // SI han muerto todos los jugadores, la proxima edicion se tendrá que empezar de nuevo
                } else {
                    view.showMessage("\nSaving & shutting down...");
                }
            }
        }
        return finalIndex;
    }

    public int getNumberOfTrials () throws FileNotFoundException {
        return editionManager.getEditionCurrentYear().getNumTrials();
    }

    private void savePlayers () throws IOException {
        int numPlayers = editionManager.getEditionCurrentYear().getNumPlayers();

        for (int j = 0; j < numPlayers; j++) {
            String name = view.askForString("Enter the player's name" + " (" + (j+1) +"/" + numPlayers + "): ");
            Player newPlayer = new Engineer(name, 5);
            teamManager.addPlayer(newPlayer);
        }

    }

    private int playTrials (int numTrials, int index) throws IOException {
        int i;
        for (i = index; i < numTrials; i++) {
            view.showMessage("Trial #"+i+"- "+editionManager.getEditionCurrentYear().getTrialNameByIndex(i));
            //Pasamos un GenericTrial al gameExecutor
            gameExecutor.playTrial(genericTrialManager.getGenericalTrial(genericTrialManager.getIndexByName(editionManager.getEditionCurrentYear().getTrialNameByIndex(i))), teamManager);
        }
        return i;
    }

    private boolean checkYear (LinkedList<Edition> edition) {
        Calendar calendar = new GregorianCalendar();
        boolean ok = false;
        for (int i = 0; i < edition.size(); i++) {
            if (edition.get(i).getYear() == calendar.get(Calendar.YEAR)) {
                ok = true;
            }
        }
        return ok;
    }
}
