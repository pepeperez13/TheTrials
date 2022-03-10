package presentation;

import business.ManagersTrials.BudgetManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;
import business.playerTypes.Doctor;
import business.playerTypes.Engineer;
import business.playerTypes.Master;
import business.playerTypes.Player;
import business.typeTrials.Budget;
import business.typeTrials.GenericTrial;
import business.typeTrials.PaperPublication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GameExecutor {
    private GenericTrial genericTrial;
    private TeamManager teamManager;
    private BudgetManager budgetManager;
    private PaperPublicationManager paperManager;
    private ViewController view;

    public GameExecutor(GenericTrial genericTrial, TeamManager teamManager, BudgetManager budgetManager, PaperPublicationManager paperManager, ViewController view) {
        this.genericTrial = genericTrial;
        this.teamManager = teamManager;
        this.budgetManager = budgetManager;
        this.paperManager = paperManager;
        this.view = view;
    }

    public void playTrial (GenericTrial genericTrial, TeamManager teamManager) throws IOException {
        switch (genericTrial.getType()) {
            case BUDGET ->  {
                playBudget(budgetManager.getBudgetByNameTrial(genericTrial.getName()));
            }
            case DOCTORAL -> playDoctoral();
            case PAPER -> playPaper(paperManager.getPaperByName(genericTrial.getName()));
            case MASTER -> playMaster();
        }
    }

    /**Empieza gestión de budget**/
    private void playBudget (Budget budget) throws IOException {
        boolean passed;
        if (teamManager.getPITeam() > (int) Math.pow(2, budget.getAmount())) {
            view.showMessage("The research group got the budget!\n");
            passed = true;
        } else {
            view.showMessage("The research group didn't get the budget!\n");
            passed = false;
        }
        updatePiTeam(teamManager, passed);
        for (Player player : teamManager.getPlayers()) {
            if (player instanceof Engineer) {
                view.showMessage(player.getName()+", Engineer. PI count: " + player.getPI());
            } else if (player instanceof Master) {
                view.showMessage(player.getName()+", Master. PI count: " + player.getPI());
            } else if (player instanceof Doctor) {
                view.showMessage(player.getName()+", PhD. PI count: " + player.getPI());
            }
        }
        LinkedList<String> changedType = checkUpdateStatus();
        for (String line: changedType) {
            view.showMessageLine(line);
        }
    }

    private void updatePiTeam (TeamManager teamManager, boolean passed) throws IOException {
        if (passed) {
            for (int i = 0; i < teamManager.getPlayers().size(); i++) {
                //Cambios el PI por cada jugador y lo actualizamos
                teamManager.getPlayers().get(i).incrementPI(teamManager.getPlayers().get(i).getPI()/2);
                teamManager.updatePlayer(i, teamManager.getPlayers().get(i));
            }
        }
        else {
            for (int i = 0; i < teamManager.getPlayers().size(); i++) {
                //Cambios el PI por cada jugador y lo actualizamos
                teamManager.getPlayers().get(i).decrementPI(2);
                teamManager.updatePlayer(i, teamManager.getPlayers().get(i));
            }
        }
    }

    private LinkedList<String> checkUpdateStatus () throws IOException {
        int i = 0;
        LinkedList<String> changedType = new LinkedList<>();
        for (Player player : teamManager.getPlayers()) {
            if (player instanceof Engineer) {
                if (player.checkUpdateStatus()) {
                    Player master = new Master(player.getName(), 5);
                    teamManager.updatePlayer(i, master);
                    // Añadimos a la lista de jugadores evolucionados para mostrarlo
                    changedType.add(player.getName() + " is now a Master (with 5 PI). ");
                }
            } else if (player instanceof Master) {
                if (player.checkUpdateStatus()) {
                    Player doctor = new Doctor(player.getName(), 5);
                    teamManager.updatePlayer(i, doctor);
                    // Añadimos a la lista de jugadores evolucionados para mostrarlo
                    changedType.add(player.getName() + "doctor");
                }
            }
            i++;
        }
        return changedType;
    }
    /**Acaba gestión budget**/


    /**Empieza gestión doctoral**/
    private void playDoctoral (){


    }

    /**Acaba gestión doctoral**/

    /**Empieza gestion paper**/
    private void playPaper (PaperPublication paper) throws IOException {
        int i = 0;

        // Hacemos que todos los jugadores publiquen su articulo y vamos actualizando su PI
        for (Player player: teamManager.getPlayers()){
            view.showMessage(player.getName() + " is submitting...");
            player = publishArticle(paper, player);
            teamManager.updatePlayer(i, player);
            view.showMessageLine("PI count:" + player.getPI());
            i++;
        }

        LinkedList<String> changedType = checkUpdateStatus();
        for (String line: changedType) {
            view.showMessageLine(line);
        }
    }

    private Player publishArticle (PaperPublication article, Player player) {

        // Calculamos de forma aleatoria si se acepta, revisa o rechaza
        int response;
        do {
            response = calculateResponse(article);
        }while (response != 1 && response != 3);
        // Aumentamos, mantenemos o reducimos puntuación segon el cuartil
        player = manageScore(response, article, player);

        return player;
    }

    private int calculateResponse (PaperPublication article) {
        // Generamos un número aleatorio que estará entre 0 y 100
        Random rand = new Random();
        int response = 0;
        int randomNumber = rand.nextInt(101);

        //Creamos los rangos de aceptado, revisado y rechazado
        int acceptedRange = article.getAcceptedProbability();
        int revisedRange = acceptedRange + article.getRevisedProbability();
        int rejectedRange = revisedRange + article.getRejectedProbability();

        if (randomNumber  <= acceptedRange) {
            view.showMessageLine("Accepted!");
            response = 1;
        }else if(randomNumber < revisedRange) {
            view.showMessageLine("Revisions...");
            response = 2;
        }else if (randomNumber < rejectedRange){
            view.showMessageLine("Rejected.");
            response = 3;
        }
        return response;
    }

    private Player manageScore (int response, PaperPublication article, Player player) {
        String quartile = article.getQuartile();

        if (response == 1) {
            switch (quartile) {
                case "Q1" -> player.incrementPI(4);
                case "Q2" -> player.incrementPI(3);
                case "Q3" -> player.incrementPI(2);
                case "Q4" -> player.incrementPI(1);
            }
        }
        if (response == 3) {
            switch (quartile) {
                case "Q1" -> player.decrementPI(5);
                case "Q2" -> player.decrementPI(4);
                case "Q3" -> player.decrementPI(3);
                case "Q4" -> player.decrementPI(2);
            }
        }
        return player;
    }
    /**Acaba gestión paper**/

    /**Empieza gestión master**/
    private void playMaster () {

    }

    /**Acaba gestión master**/
}
