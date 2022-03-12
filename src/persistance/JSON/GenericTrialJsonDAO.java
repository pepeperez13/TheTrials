package persistance.JSON;

import business.ManagersTrials.TrialTypeOptions;
import business.typeTrials.GenericTrial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.GenericTrialDAO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;

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

        for (int i = 0; i < 5; i++){
            create(new GenericTrial("Prueba " + Integer.valueOf(i), TrialTypeOptions.PAPER));
        }

        LinkedList<GenericTrial> generics = readAll();
        System.out.println(generics.get(3).getName());

        //System.out.println(findByIndex(3).getName() + findByIndex(3).getType());

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

    public static LinkedList<GenericTrial> readAll () {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            LinkedList<GenericTrial> genericList = gson.fromJson(lines, LinkedList.class);
            return genericList;
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    public static GenericTrial findByIndex (int index) {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            LinkedList<GenericTrial> genericList = gson.fromJson(lines, LinkedList.class);
            System.out.println(genericList.get(index));
            return genericList.get(index - 1);
        } catch (IOException e) {
            return null;
        }
    }


}
