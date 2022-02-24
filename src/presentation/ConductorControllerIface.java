package presentation;

import business.GameLogic;
import business.TeamManager;

import java.io.IOException;

public interface ConductorControllerIface {

    void playTrial (Object o, TeamManager teamManager, ViewController view, GameLogic gameLogic) throws IOException;
}
