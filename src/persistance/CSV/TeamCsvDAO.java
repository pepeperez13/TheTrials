package persistance.CSV;

import business.PlayerTypeOptions;
import business.playerTypes.Doctor;
import business.playerTypes.Engineer;
import business.playerTypes.Master;
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
    private static final String separator = ",";
    private String fileName = "team.csv";
    private String filePath = "files";
    private File file = new File(filePath, fileName);

    public TeamCsvDAO () throws IOException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nThe file already exist.");
        }
    }

    /**
     * Este método transforma un String y un entero
     * para darle el formato requerido
     * @param player jugador
     * @return String Cadena de caracteres a guardar
     */
    private String playerToCsv (Player player) {
        if (player instanceof Engineer) {
            return player.getName() + separator + player.getPI() + separator + PlayerTypeOptions.ENGINEER;
        } else if (player instanceof Doctor) {
            return player.getName() + separator + player.getPI() + separator + PlayerTypeOptions.DOCTOR;
        } else {
            return player.getName() + separator + player.getPI() + separator + PlayerTypeOptions.MASTER;
        }
    }


    private Player playerFromCsv(String csv) {
        String[] parts = csv.split(separator);
        if (parts[2].equals("ENGINEER")) {
            return new Engineer(parts[0], Integer.parseInt(parts[1]));
        } else if (parts[2].equals("DOCTOR")) {
            return new Doctor(parts[0], Integer.parseInt(parts[1]));
        } else {
            return new Master(parts[0], Integer.parseInt(parts[1]));
        }
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
            //players.add("");
            Files.write(file.toPath(), players);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}

