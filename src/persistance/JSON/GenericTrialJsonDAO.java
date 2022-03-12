package persistance.JSON;

import business.ManagersTrials.TrialTypeOptions;
import business.typeTrials.GenericTrial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GenericTrialJsonDAO {
    private String filename = "generics.json";
    private static String route = "files/generics.json";
    private static Path path = Path.of(route);
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

    public static void main (String[] args) {
        GenericTrialJsonDAO generic = new GenericTrialJsonDAO();


        for (int i = 0; i < 30; i++){
            //create(new GenericTrial("Prueba " + Integer.valueOf(i), TrialTypeOptions.PAPER));
        }

        List<GenericTrial> generics = readAll();


        System.out.println(generics.get(0).getName());
        System.out.println(findByIndex(22).getName());

        delete(0);
        delete(1);
        delete(20);

        System.out.println(generics.get(0).getName());

    }


    public static boolean create (GenericTrial generic) {
        try {
            Gson gson = new Gson();
            gson = new GsonBuilder().setPrettyPrinting().create();
            String lines = Files.readString(path);
            LinkedList<GenericTrial> genericList = gson.fromJson(lines, LinkedList.class);
            genericList.add(generic);
            String jsonData = gson.toJson(genericList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    public static List<GenericTrial> readAll () {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);Type listType = new TypeToken<List<GenericTrial>>(){}.getType();
            List<GenericTrial> genericList = gson.fromJson(lines, listType);
            return genericList;
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    public static GenericTrial findByIndex (int index) {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);Type listType = new TypeToken<List<GenericTrial>>(){}.getType();
            List<GenericTrial> genericList = gson.fromJson(lines, listType);
            return genericList.get(index - 1);
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean delete (int index) {
        try {
            Gson gson = new Gson();
            gson = new GsonBuilder().setPrettyPrinting().create();
            List<GenericTrial> genericTrials = readAll();
            String jsonData = gson.toJson(genericTrials, List.class);
            Files.write(path, jsonData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
