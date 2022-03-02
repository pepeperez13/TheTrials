package persistance.JSON;

import business.playerTypes.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.TeamDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class TeamJsonDAO implements TeamDAO {

    private String filename = "team.json";
    private String filePath = "files";
    private final File file = new File(filePath, filename);
    private final Gson gson;
    private final Player[] team;

    public TeamJsonDAO () throws FileNotFoundException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        team = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), Player[].class);
    }

    @Override
    public boolean create (Player player) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<Player> playersList = new LinkedList<>();
        if (team != null) {// Sólo leeremos elementos si el json no está vacío
            playersList = new LinkedList<>(Arrays.asList(team));
        }

        playersList.add(player);
        gson.toJson(playersList, writer);
        writer.close();
        return false;

    }

    @Override
    public LinkedList<Player> readAll () throws FileNotFoundException {
        // Nunca va estar vacia (comprobamos antes de llamar)
        try {
            return new LinkedList<>(Arrays.asList(team));
        } catch (NullPointerException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public boolean delete (int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<Player> playersList = new LinkedList<>(Arrays.asList(team));
        playersList.remove(index);

        gson.toJson(playersList, writer);
        writer.close();
        return false;
    }

    @Override
    public boolean changeLine (int index, Player player) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<Player> playersList = new LinkedList<>(Arrays.asList(team));

        playersList.remove(index);
        playersList.add(index, player);

        gson.toJson(playersList, writer);
        writer.close();
        return false;
    }

    @Override
    public boolean emptyFile () throws IOException {
        FileWriter writer = new FileWriter(filename);

        gson.toJson(new LinkedList<>(), writer);
        return false;
    }

}
