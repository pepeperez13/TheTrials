package presentation;

import business.ManagersTrials.BudgetManager;
import business.ManagersTrials.DoctoralManager;
import business.ManagersTrials.MasterManager;
import business.ManagersTrials.PaperPublicationManager;
import business.TeamManager;
import business.trialExecutionLogic.BudgetGame;
import business.trialExecutionLogic.DoctoralGame;
import business.trialExecutionLogic.MasterGame;
import business.trialExecutionLogic.PaperGame;
import business.typeTrials.GenericTrial;

import java.io.IOException;

public class GameExecutor {

    public void play (GenericTrial genericTrial) throws IOException {
        TeamManager teamManager = null;
        PaperPublicationManager paperPublicationManager = null;
        MasterManager masterManager = null;
        DoctoralManager doctoralManager = null;
        BudgetManager budgetManager = null;
        PaperExecutor paperExecutor = null;
        BudgetExecutor budgetExecutor = null;
        MasterExecutor masterExecutor = null;
        DoctoralExecutor doctoralExecutor = null;

        switch (genericTrial.getType()) {
            case PAPER -> {
                paperExecutor.playTrial(paperPublicationManager.getTrialByName(genericTrial.getName()));
                //paperExecutor.publishArticle(paperPublicationManager.getTrialByName(genericTrial.getName()), teamManager.getPlayers().get(i));
            }
            case BUDGET -> {
                budgetExecutor.playTrial(budgetManager.getBudgetByNameTrial(genericTrial.getName()));
                //budgetGame.checkTeam(teamManager, genericTrial.getName());
            }
            case MASTER -> {
                masterExecutor.playTrial(masterManager.getMasterByName(genericTrial.getName()));
            }
            case DOCTORAL -> {
                doctoralExecutor.playTrial(doctoralManager.getDoctoralByName(genericTrial.getName()));
            }
        }
    }

}
