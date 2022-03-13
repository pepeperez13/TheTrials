package persistance.JSON;

import business.typeTrials.GenericTrial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistance.GenericTrialDAO;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class GenericTrialJsonDAO implements GenericTrialDAO {
    private final String filename = "generics.json";
    private static final String route = "files/generics.json";
    private static final Path path = Path.of(route);
    private File file = new File("files", filename);

    public GenericTrialJsonDAO () {
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
    public boolean create (GenericTrial generic) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String lines = Files.readString(path);
            LinkedList<GenericTrial> genericList = new LinkedList<>();
            // Solo leeremos elementos si el json no está vacío
            if (gson.fromJson(lines, LinkedList.class) != null) {
                genericList = gson.fromJson(lines, LinkedList.class);
            }
            genericList.add(generic);
            String jsonData = gson.toJson(genericList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<GenericTrial> readAll () {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<GenericTrial>>(){}.getType();
            List<GenericTrial> genericList = new LinkedList<>();
            if (gson.fromJson(lines, listType) != null) {
                genericList = gson.fromJson(lines, listType);
            }
            return new LinkedList<>(genericList);
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public GenericTrial findByIndex (int index) {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<GenericTrial>>(){}.getType();
            List<GenericTrial> genericList = gson.fromJson(lines, listType);
            return genericList.get(index - 1);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete (int index) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<GenericTrial> genericTrials = readAll();
            genericTrials.remove(index);
            String jsonData = gson.toJson(genericTrials, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
