package presentation;

import business.ManagersTrials.BudgetManager;
import business.ManagersTrials.DoctoralManager;
import business.ManagersTrials.MasterManager;
import business.ManagersTrials.PaperPublicationManager;
import business.PlayerTypeOptions;
import business.TeamManager;
import business.playerTypes.Player;
import business.trialExecutionLogic.BudgetGame;
import business.trialExecutionLogic.DoctoralGame;
import business.trialExecutionLogic.MasterGame;
import business.trialExecutionLogic.PaperGame;
import business.typeTrials.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class GameExecutor {
    public void play (GenericTrial genericTrial) throws IOException {
        PaperPublicationManager paperPublicationManager = null;
        MasterManager masterManager = null;
        DoctoralManager doctoralManager = null;
        BudgetManager budgetManager = null;

        switch (genericTrial.getType()) {
            case PAPER -> {
                //paperExecutor.playTrial(paperPublicationManager.getPaperByName(genericTrial.getName()));
                playPaper(paperPublicationManager.getPaperByName(genericTrial.getName()));
            }
            case BUDGET -> {
                //budgetExecutor.playTrial(budgetManager.getBudgetByNameTrial(genericTrial.getName()));
                playBudget(budgetManager.getBudgetByNameTrial(genericTrial.getName()));
            }
            case MASTER -> {
                //masterExecutor.playTrial(masterManager.getMasterByName(genericTrial.getName()));
                playMaster(masterManager.getMasterByName(genericTrial.getName()));
            }
            case DOCTORAL -> {
                //doctoralExecutor.playTrial(doctoralManager.getDoctoralByName(genericTrial.getName()));
                playDoctoral(doctoralManager.getDoctoralByName(genericTrial.getName()));
            }
        }
    }

    private void playPaper (PaperPublication paperPublication) throws IOException {
        TeamManager teamManager = null;
        ViewController view = null;
        PaperGame paperGame = null;
        LinkedList<String> namesUpdatedType = new LinkedList<>();
        int i = 0;

        for (Player player: teamManager.getPlayers()) {
            if (player.getPI() != 0) {
                view.showMessageLine(player.getName() + " is submitting... ");
                player = paperGame.publishArticle(paperPublication, player); // Publicamos articulo
                //teamManager.updatePlayer(i, player); // Actualizamos la info del jugador
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

    private void playBudget (Budget budget) throws FileNotFoundException {
        TeamManager teamManager = null;
        ViewController view = null;
        BudgetGame budgetGame = null;
        LinkedList<String> namesUpdatedType = new LinkedList<>();

        int i = 0;

        boolean accepted = budgetGame.checkPassed(budget);
        if (accepted){
            view.showMessage("The research group got the budget!");
        }else{
            view.showMessage("Research group did not get the budget! Sorry!");
        }
        for (Player player: teamManager.getPlayers()) {
            player = budgetGame.updatePI(accepted, player);
            switch (player.getPlayerType()) {
                case ENGINEER -> view.showMessage(player.getName() + ". PI count: " + player.getPI());
                case MASTERS -> view.showMessage("Master " + player.getName() + ". PI count: " + player.getPI());
                case DOCTORS -> view.showMessage(player.getName() + ",PhD. PI count: " + player.getPI());
            }
        }
        namesUpdatedType = budgetGame.changePlayersStatus(teamManager);

        // Mostramos todos los jugadores que hayan cambiado de tipo
        for (String name : namesUpdatedType){
            String[] parts = name.split(" ");
            view.showMessage(parts[0] + " is now a " + parts[1] + "(with 5 PI)");
        }
    }

    private void playMaster (MasterStudies masterStudies) throws IOException {
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
                if (passed > masterStudies.getNumberCredits() / 2) {
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

    private void playDoctoral (DoctoralThesis doctoralThesis) throws IOException {
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
