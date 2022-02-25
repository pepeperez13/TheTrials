package presentation;

import business.Edition;
import business.EditionManager;
import business.ManagersTrials.GenericTrialManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;
import business.playerTypes.Player;
import business.typeTrials.GenericTrial;

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
    private GenericTrialManager namesManager;

    public ConductorController(PaperPublicationManager paperPublicationManager, EditionManager editionManager, TeamManager teamManager, ViewController view, GameExecutor gameExecutor, GenericTrialManager genericTrialManager, GenericTrialManager namesManager) {
        this.paperPublicationManager = paperPublicationManager;
        this.editionManager = editionManager;
        this.teamManager = teamManager;
        this.view = view;
        this.gameExecutor = gameExecutor;
        this.genericTrialManager = genericTrialManager;
        this.namesManager = namesManager;
    }

    /**
     * recibe una Variable que nos permitirá saber qué prueba se ha ejecutado la última, y por tanto si la próxima
     * vez que entremos tiene que continuar la ejecución o empezar de nuevo
     * @param finalIndex
     */
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
                view.showMessage("\nTHE TRIALS 2021 HAVE ENDED - PLAYERS WON");
                teamManager.removeAllPlayers();
                finalIndex = 0;
            } else {
                if (dead) {
                    view.showMessage("\nExecution ended... Players lost!");
                    teamManager.removeAllPlayers();
                    finalIndex = 0; // SI han muerto todos los jugadores, la proxima edicion se tendrá que empezar de nuevo
                } else {
                    view.showMessage("\nSaving & shutting down...");
                }
            }
        }
        return finalIndex;
    }

    private void savePlayers () throws IOException {
        int numPlayers = editionManager.getEditionCurrentYear().getNumPlayers();

        for (int j = 0; j < numPlayers; j++) {
            String name = view.askForString("Enter the player's name" + " (" + (j+1) +"/" + numPlayers + "): ");
            Player newPlayer = new Player(name);
            teamManager.addPlayer(newPlayer);
        }

    }

    private int playTrials (int numTrials, int index) throws IOException {
        boolean continueExecution = true;
        String aux = "";
        int i;

        for (i = index; i < numTrials && continueExecution && !teamManager.checkDeadPlayers(); i++) {
            view.showMessage("\nTrial" + " #" + (i + 1) + " - " + editionManager.getEditionCurrentYear().getTrialNameByIndex(i) + "\n"); //Va mostrando los nombres de la prueba de ese año
            //playTrial(paperPublicationManager.getTrialByName(editionManager.getEditionCurrentYear().getTrialNameByIndex(i)));
            GenericTrial genericTrial = genericTrialManager.getGenericalTrial(i); //Esto me devuelve un tipo GenericalTrial de la prueba correspondiente
            gameExecutor.play(genericTrial); //Juegan los distintos jugadores
            //iface.playTrial(genericTrial, teamManager, view, gameExecutor); //Play trial debera aplicarle la logica del juego del trial que toque
            boolean dead = teamManager.checkDeadPlayers();
            if (i != numTrials - 1 && !dead) { // Si no se han ejecutado ya todos los trials, preguntamos si seguir con ejecución o no
                do {
                    aux = view.askForString("\nContinue the execution? [yes/no]: ");
                    switch (aux) {
                        case "yes":
                            continueExecution = true;
                            break;
                        case "no":
                            continueExecution = false;
                            break;
                        default:
                            view.showMessage("Expected yes/no. Try again. ");
                            break;
                    }
                } while (!aux.equals("yes") && !aux.equals("no"));
            }
        }
        return i;
    }

    /*private void playTrial (PaperPublication article) throws IOException { //Este método falla
        int i = 0;

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                player = gameExecutor.publish(article, player); // Publicamos articulo
                teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
                view.showMessageLine(" PI count: " + player.getPI() + "\n");
            }
            i++;
        }
    }*/

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
