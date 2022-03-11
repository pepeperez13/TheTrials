package persistance.JSON;

import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.PaperDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class PaperJsonDAO implements PaperDAO {
    private String filename = "papers.json";
    private String filePath = "files";
    private final File file = new File(filePath, filename);
    private Gson gson;
    private PaperPublication[] articles;

    public PaperJsonDAO() throws FileNotFoundException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson = new GsonBuilder().setPrettyPrinting().create();
            articles = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), PaperPublication[].class);
        } else {
            System.out.println("\nThe file already exist.");
        }
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
        try {
            return new LinkedList<>(Arrays.asList(articles));
        } catch (NullPointerException e) {
            //No hay nada que leer
            return new LinkedList<>();
        }
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