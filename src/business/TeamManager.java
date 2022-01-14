package business;

import persistance.CSV.TeamCsvDAO;
import persistance.JSON.TeamJsonDAO;
import persistance.TeamDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class TeamManager {
    private TeamDAO teamDAO;
    public TeamManager(DataSourceOptions options) {
        switch (options) {
            case JSON -> teamDAO = new TeamJsonDAO();
            case CSV -> teamDAO = new TeamCsvDAO();
        }
    }

    /** Método que añade un nuevo participante
     * @param player Nombre del jugador
     * @param PI Puntuación inicial del jugador
     */
    public void addPlayer (String player, int PI) throws IOException {
        teamDAO.create(player, PI);
    }

    public void updatePlayer(int index, String player, int PI) throws IOException {
        teamDAO.changeLine(index, player, PI);
    }

    /**
     * Método que elimina los jugadores muertos de un equipo
     * @return Booleano que indica si TODOS los jugadores han muerto o no
     */
    public boolean checkDeadPlayers () throws FileNotFoundException {
        boolean dead = true;
        LinkedList<Player> players = getPlayers();
        for (int i = 0; i < players.size() && dead; i++) {
            if (players.get(i).getPI() != 0) {
                dead = false;
            }
        }
        return dead;
    }

    public void removeAllPlayers () throws IOException {
        teamDAO.emptyFile();
    }

    public LinkedList<Player> getPlayers () throws FileNotFoundException {
        return teamDAO.readAll();
    }
}