package persistance.JSON;

import business.Edition;
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
    private final Player[] team;

    public TeamJsonDAO () throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        team = gson.fromJson(gson.newJsonReader(new FileReader(filename)), Player.class);
    }

    @Override
    public boolean create (String name, int PI) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<Player> playersList = new LinkedList<>();
        if (team != null) {// Sólo leeremos elementos si el json no está vacío
            playersList = new LinkedList<>(Arrays.asList(team));
        }

        playersList.add(new Player(name, PI));
        gson.toJson(playersList, writer);
        writer.close();
        return false;

    }

    @Override
    public LinkedList<Player> readAll () throws FileNotFoundException {
        // Nunca va estar vacia (comprobamos antes de llamar)
        return new LinkedList<>(Arrays.asList(team));
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
    public boolean changeLine (int index, String name, int PI) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<Player> playersList = new LinkedList<>(Arrays.asList(team));

        playersList.remove(index);
        playersList.add(index, new Player(name, PI));

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
