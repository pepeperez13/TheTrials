package persistance.JSON;

import business.typeTrials.DoctoralThesis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistance.DoctoralDAO;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class DoctoralJsonDAO implements DoctoralDAO {
    private final String filename = "doctorals.json";
    private static final String route = "files/doctorals.json";
    private static final Path path = Path.of(route);
    private File file = new File("files", filename);

    public DoctoralJsonDAO ()  {
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
    public boolean create (DoctoralThesis doctoralThesis) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String lines = Files.readString(path);
            LinkedList<DoctoralThesis> doctoralsList = new LinkedList<>();
            // Solo leeremos elementos si el json no está vacío
            if (gson.fromJson(lines, LinkedList.class) != null) {
                doctoralsList = gson.fromJson(lines, LinkedList.class);
            }
            doctoralsList.add(doctoralThesis);
            String jsonData = gson.toJson(doctoralsList, LinkedList.class);
            Files.write(path, jsonData.getBytes());
            return true;
        }catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<DoctoralThesis> readAll() {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<DoctoralThesis>>(){}.getType();
            List<DoctoralThesis> doctoralsList = new LinkedList<>();
            if (gson.fromJson(lines, listType) != null) {
                doctoralsList = gson.fromJson(lines, listType);
            }
            return new LinkedList<>(doctoralsList);
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public DoctoralThesis findByIndex(int index) {
        try{
            Gson gson = new Gson();
            String lines = Files.readString(path);
            Type listType = new TypeToken<List<DoctoralThesis>>(){}.getType();
            List<DoctoralThesis> doctoralsList = gson.fromJson(lines, listType);
            return doctoralsList.get(index - 1);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<DoctoralThesis> doctorals = readAll();
            String jsonData = gson.toJson(doctorals, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean changeLine (int index, DoctoralThesis doctoral) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<DoctoralThesis> doctorals = readAll();
            doctorals.remove(index);
            doctorals.add(index, doctoral);
            String jsonData = gson.toJson(doctorals, List.class);
            Files.write(path, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
