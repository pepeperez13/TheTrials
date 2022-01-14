package persistance.JSON;

import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.PaperDAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class PaperJsonDAO implements PaperDAO {

    private static final String filename = "files/papers.json";
    private final Gson gson;

    public PaperJsonDAO() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean create (PaperPublication article) throws IOException {
        FileReader reader = new FileReader(filename);
        FileWriter writer = new FileWriter(filename);

        PaperPublication[] articles = gson.fromJson(reader, PaperPublication[].class);
        LinkedList<PaperPublication> articlesList = new LinkedList<>(Arrays.asList(articles));

        gson.toJson(articlesList.add(article), writer);
        writer.close();
        return false;
    }

    @Override
    public LinkedList<PaperPublication> readAll () throws FileNotFoundException {
        FileReader reader = new FileReader(filename);

        PaperPublication[] articles = gson.fromJson(reader, PaperPublication[].class);

        return new LinkedList<>(Arrays.asList(articles));
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileReader reader = new FileReader(filename);
        FileWriter writer = new FileWriter(filename);

        PaperPublication[] articles = gson.fromJson(reader, PaperPublication[].class);
        LinkedList<PaperPublication> articlesList = new LinkedList<>(Arrays.asList(articles));

        gson.toJson(articlesList.remove(index), writer);
        return false;
    }

    @Override
    public boolean changeLine(int index, PaperPublication article) throws IOException {
        FileReader reader = new FileReader(filename);
        FileWriter writer = new FileWriter(filename);

        PaperPublication[] articles = gson.fromJson(reader, PaperPublication[].class);
        LinkedList<PaperPublication> articlesList = new LinkedList<>(Arrays.asList(articles));

        articlesList.remove(index);
        articlesList.add(index, article);

        gson.toJson(articlesList, writer);
        return false;
    }
}