package persistance.JSON;

import business.typeTrials.GenericTrial;
import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistance.PaperDAO;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PaperJsonDAO implements PaperDAO {
    private String filename = "papers.json";
    private static String route = "files/papers.json";
    private static Path path = Path.of(route);
    private File file = new File("files", filename);

    public PaperJsonDAO() throws FileNotFoundException {
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
    public boolean create (PaperPublication article){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String lines = Files.readString(path);
            LinkedList<PaperPublication> articlesList = gson.fromJson(lines, LinkedList.class);
            articlesList.add(article);
            String jsonData = gson.toJson(articlesList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<PaperPublication> readAll () {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<GenericTrial>>(){}.getType();
            List<PaperPublication> paperPublications = gson.fromJson(lines, listType);
            LinkedList<PaperPublication> paperLinked = new LinkedList<>(paperPublications);
            return paperLinked;
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<PaperPublication> paperPublications = readAll();
            paperPublications.remove(index);
            String jsonData = gson.toJson(paperPublications, List.class);
            Files.write(path, jsonData.getBytes());return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeLine(int index, PaperPublication article) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            LinkedList<PaperPublication> articlesList = readAll();
            articlesList.remove(index);
            articlesList.add(index, article);
            String jsonData = gson.toJson(articlesList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}