package persistance.JSON;

import business.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.TeamDAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class TeamJsonDAO implements TeamDAO {

    private static final String filename = "files/team.json";
    private final Gson gson;

    public TeamJsonDAO () {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean create (String name, int PI) throws IOException {
        FileReader reader = new FileReader(filename);
        FileWriter writer = new FileWriter(filename);

        Player[] players = gson.fromJson(reader, Player[].class);
        LinkedList<Player> playersList = new LinkedList<>(Arrays.asList(players));

        gson.toJson(playersList.add(new Player(name, PI)), writer);
        writer.close();
        return false;

    }

    @Override
    public LinkedList<Player> readAll () throws FileNotFoundException {
        FileReader reader = new FileReader(filename);

        Player[] players = gson.fromJson(reader, Player[].class);

        return new LinkedList<>(Arrays.asList(players));
    }

    @Override
    public boolean delete (int index) throws IOException {
        FileReader reader = new FileReader(filename);
        FileWriter writer = new FileWriter(filename);

        Player[] players = gson.fromJson(reader, Player[].class);
        LinkedList<Player> playersList = new LinkedList<>(Arrays.asList(players));

        gson.toJson(playersList.remove(index), writer);
        return false;
    }

    @Override
    public boolean changeLine (int index, String name, int PI) throws IOException {
        FileReader reader = new FileReader(filename);
        FileWriter writer = new FileWriter(filename);

        Player[] players = gson.fromJson(reader, Player[].class);
        LinkedList<Player> playersList = new LinkedList<>(Arrays.asList(players));

        playersList.remove(index);
        playersList.add(index, new Player(name, PI));

        gson.toJson(playersList, writer);
        return false;
    }

    @Override
    public boolean emptyFile () throws IOException {
        FileWriter writer = new FileWriter(filename);

        gson.toJson(new LinkedList<>(), writer);
        return false;
    }

}
