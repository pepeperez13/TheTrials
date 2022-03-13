package persistance.JSON;

import business.playerTypes.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistance.TeamDAO;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class TeamJsonDAO implements TeamDAO {
    private final String filename = "teams.json";
    private static final String route = "files/teams.json";
    private static final Path path = Path.of(route);
    private File file = new File("files", filename);

    public TeamJsonDAO () {
        try {
            if(!file.exists()){
                file.createNewFile();
                Files.write(Path.of(String.valueOf(path)), "[]".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create (Player player) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            /*GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Player.class, new JsonDeserializerWithInheritance<Player>());
            Gson gson = builder.setPrettyPrinting().create();*/
            String lines = Files.readString(path);
            LinkedList<Player> playersList = new LinkedList<>();
            // Solo leeremos elementos si el json no está vacío
            if (gson.fromJson(lines, LinkedList.class) != null) {
                playersList = gson.fromJson(lines, LinkedList.class);
            }
            playersList.add(player);
            String jsonData = gson.toJson(playersList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<Player> readAll () {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<Player>>(){}.getType();
            List<Player> playersList = new LinkedList<>();
            if (gson.fromJson(lines, listType) != null) {
                playersList = gson.fromJson(lines, listType);
            }
            return new LinkedList<>(playersList);
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public boolean delete (int index) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Player> players = readAll();
            players.remove(index);
            String jsonData = gson.toJson(players, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeLine (int index, Player player) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Player> players = readAll();
            players.remove(index);
            players.add(index, player);
            String jsonData = gson.toJson(players, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean emptyFile () {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<String> emptyList = new LinkedList<>();
            String jsonData = gson.toJson(emptyList, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
