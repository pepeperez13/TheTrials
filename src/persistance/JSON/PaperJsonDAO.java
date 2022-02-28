package persistance.JSON;

import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.PaperDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class PaperJsonDAO implements PaperDAO {

    private static final File file = new File("files/papers.json");
    private static final String filename = "files/papers.json";
    //private String filename = "papers.json";
    //private String filePath = "C:\\Users\\Ashlyn Abraham\\Documents\\GitHub\\TheTrials\\files";
    //private final File file = new File(filePath, filename);
    private final Gson gson;
    private final PaperPublication[] articles;

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