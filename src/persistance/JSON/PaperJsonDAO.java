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
    private final PaperPublication[] articles;

    public static void main (String[] args) throws IOException {
        PaperJsonDAO paper = new PaperJsonDAO();

        //paper.create(new PaperPublication("Article1", "Magazine1", "Q2", 70, 20, 10));
        //LinkedList<PaperPublication> papers = paper.readAll();
        //paper.delete(0);
        //paper.changeLine(1, new PaperPublication("pito", "Pita", "Q1", 20, 20, 60 ));

    }

    public PaperJsonDAO() throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        articles = gson.fromJson(gson.newJsonReader(new FileReader(filename)), PaperPublication[].class);
    }

    @Override
    public boolean create (PaperPublication article) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<PaperPublication> articlesList = new LinkedList<>();
        if (articles != null) { // Sólo leeremos elementos si el json no está vacío
            articlesList = new LinkedList<>(Arrays.asList(articles));
        }

        articlesList.add(article);
        gson.toJson(articlesList, writer);
        writer.close();
        return false;
    }

    @Override
    public LinkedList<PaperPublication> readAll () throws FileNotFoundException {
        // Nunca va estar vacia (comprobamos antes de llamar)
        return new LinkedList<>(Arrays.asList(articles));
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<PaperPublication> articlesList = new LinkedList<>(Arrays.asList(articles));
        articlesList.remove(index);

        gson.toJson(articlesList, writer);
        writer.close();
        return false;
    }

    @Override
    public boolean changeLine(int index, PaperPublication article) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<PaperPublication> articlesList = new LinkedList<>(Arrays.asList(articles));

        articlesList.remove(index);
        articlesList.add(index, article);

        gson.toJson(articlesList, writer);
        writer.close();
        return false;
    }
}