package persistance.JSON;

import business.Edition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistance.EditionDAO;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class EditionJsonDAO implements EditionDAO {
    private final String filename = "editions.json";
    private static final String route = "files/editions.json";
    private static final Path path = Path.of(route);
    private File file = new File("files", filename);

    public EditionJsonDAO () {
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
    public boolean create (Edition edition) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String lines = Files.readString(path);
            LinkedList<Edition> editionsList = new LinkedList<>();
            // Solo leeremos elementos si el json no está vacío
            if (gson.fromJson(lines, LinkedList.class) != null) {
                editionsList = gson.fromJson(lines, LinkedList.class);
            }
            editionsList.add(edition);
            String jsonData = gson.toJson(editionsList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<Edition> readAll() {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<Edition>>(){}.getType();
            List<Edition> editionsList = new LinkedList<>();
            if (gson.fromJson(lines, listType) != null) {
                editionsList = gson.fromJson(lines, listType);
            }
            return new LinkedList<>(editionsList);
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    /**
     * Este método nos permite obtener una edicion en concreto
     * @param index Indice que nos indica la posicion del objeto deseado
     * @return Edition Retorna la edicion deseada
     */
    @Override
    public Edition findByIndex (int index) {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<Edition>>(){}.getType();
            List<Edition> editionsList = gson.fromJson(lines, listType);
            return editionsList.get(index - 1);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete (int index) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Edition> editions = readAll();
            editions.remove(index);
            String jsonData = gson.toJson(editions, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
