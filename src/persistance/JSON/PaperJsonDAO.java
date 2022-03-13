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
import java.util.LinkedList;
import java.util.List;

public class PaperJsonDAO implements PaperDAO {
    private final String filename = "papers.json";
    private static final String route = "files/papers.json";
    private static final Path path = Path.of(route);
    private File file = new File("files", filename);

    public PaperJsonDAO() {
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
            LinkedList<PaperPublication> articlesList = new LinkedList<>();
            // Solo leeremos elementos si el json no está vacío
            if (gson.fromJson(lines, LinkedList.class) != null) {
                articlesList = gson.fromJson(lines, LinkedList.class);
            }
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
            Type listType = new TypeToken<List<PaperPublication>>(){}.getType();
            List<PaperPublication> paperPublications = new LinkedList<>();
            if (gson.fromJson(lines, listType) != null) {
                paperPublications= gson.fromJson(lines, listType);
            }
            return new LinkedList<>(paperPublications);
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