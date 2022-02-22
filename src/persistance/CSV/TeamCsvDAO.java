package persistance.CSV;

import business.playerTypes.Player;
import persistance.TeamDAO;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que gestiona la lectura y escritura del fichero team
 * @author Jose Perez
 * @author Abraham Cedeño
 */
public class TeamCsvDAO implements TeamDAO {
    private static final File file = new File("files/team.csv");
    private static final String separator = ",";

    /**
     * Este método transforma un String y un entero
     * para darle el formato requerido
     * @param player jugador
     * @return String Cadena de caracteres a guardar
     */
    private String playerToCsv (Player player) {
        return player.getName() + separator + player.getPI();
    }


    private Player playerFromCsv(String csv) { //Una linea del archivo Aritulos
        String[] parts = csv.split(separator);
        return new Player(parts[0], Integer.parseInt(parts[1]));
    }

    /**
     * Método que ll
     * @param player jugador
     * @return booleano
     */
    @Override
    public boolean create(Player player) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            list.add(playerToCsv(player));
            Files.write(file.toPath(), list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            List<String> players = Files.readAllLines(file.toPath());
            players.remove(index);
            Files.write(file.toPath(), players);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<Player> readAll() { //Todo el fichero articulos
        try{
            LinkedList<Player> players = new LinkedList<>();
            List<String> list = Files.readAllLines(file.toPath());
            for (String line: list) {
                players.add(playerFromCsv(line));
            }
            return players;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean changeLine(int index, Player player) {
        try {
            List<String> players = Files.readAllLines(file.toPath());
            players.remove(index);
            players.add(index, playerToCsv(player));
            Files.write(file.toPath(), players);
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean emptyFile() {
        try {
            LinkedList<String> players = new LinkedList<>();
            players.add("");
            Files.write(file.toPath(), players);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}

