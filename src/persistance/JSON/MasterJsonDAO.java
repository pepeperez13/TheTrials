package persistance.JSON;


import business.typeTrials.MasterStudies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistance.MasterDAO;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class MasterJsonDAO implements MasterDAO {
    private final String filename = "masters.json";
    private static final String route = "files/masters.json";
    private static final Path path = Path.of(route);
    private File file = new File("files", filename);

    public MasterJsonDAO () {
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
    public boolean create(MasterStudies masterStudies) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String lines = Files.readString(path);
            LinkedList<MasterStudies> mastersList = new LinkedList<>();
            // Solo leeremos elementos si el json no está vacío
            if (gson.fromJson(lines, LinkedList.class) != null) {
                mastersList = gson.fromJson(lines, LinkedList.class);
            }
            mastersList.add(masterStudies);
            String jsonData = gson.toJson(mastersList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<MasterStudies> readAll() {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<MasterStudies>>(){}.getType();
            List<MasterStudies> mastersList = new LinkedList<>();
            if (gson.fromJson(lines, listType) != null) {
                mastersList = gson.fromJson(lines, listType);
            }
            return new LinkedList<>(mastersList);
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public MasterStudies findByIndex(int index) {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<MasterStudies>>(){}.getType();
            List<MasterStudies> mastersList = gson.fromJson(lines, listType);
            return mastersList.get(index - 1);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<MasterStudies> masters = readAll();
            masters.remove(index);
            String jsonData = gson.toJson(masters, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeLine(int index, MasterStudies master) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<MasterStudies> masters = readAll();
            masters.remove(index);
            masters.add(index, master);
            String jsonData = gson.toJson(masters, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
