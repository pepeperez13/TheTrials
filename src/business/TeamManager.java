package business;

import persistance.CSV.TeamCsvDAO;
import persistance.JSON.TeamJsonDAO;
import persistance.TeamDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class TeamManager {
    private TeamDAO teamDAO;

    public TeamManager(DataSourceOptions options) throws IOException {
        switch (options) {
            case JSON -> teamDAO = new TeamJsonDAO();
            case CSV -> teamDAO = new TeamCsvDAO();
        }
    }

    /** Método que añade un nuevo participante
     * @param player Nombre del jugador
     */
    public void addPlayer (Player player) throws IOException {
        teamDAO.create(player);
    }

    public void updatePlayer(int index, Player player) throws IOException {
        teamDAO.changeLine(index, player);
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

    public int getPITeam () throws FileNotFoundException {
        int total = 0;
        for (int i = 0; i < getPlayers().size(); i++) {
            total += getPlayers().get(i).getPI();
        }
        return total;
    }
}