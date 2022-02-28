package persistance.JSON;

import business.Edition;

import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.EditionDAO;


import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class EditionJsonDAO implements EditionDAO {

    private static final String filename = "files/editions.json";
    private static final File file = new File("files/editions.json");
    //private String filename = "editions.json";
    //private String filePath = "C:\\Users\\Ashlyn Abraham\\Documents\\GitHub\\TheTrials\\files";
    //private final File file = new File(filePath, filename);
    private final Gson gson;
    private final Edition[] editions;

    public EditionJsonDAO () throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        editions = gson.fromJson(gson.newJsonReader(new FileReader(filename)), Edition[].class);
    }

    @Override
    public boolean create(Edition edition) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<Edition> editionsList = new LinkedList<>();
        if (editions != null) {// Sólo leeremos elementos si el json no está vacío
            editionsList = new LinkedList<>(Arrays.asList(editions));
        }

        editionsList.add(edition);
        gson.toJson(editionsList, writer);
        writer.close();
        return false;
    }

    @Override
    public LinkedList<Edition> readAll() throws FileNotFoundException {
        // Nunca va estar vacia (comprobamos antes de llamar)
         return new LinkedList<>(Arrays.asList(editions));
    }


    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<Edition> editionsList = new LinkedList<>(Arrays.asList(editions));
        editionsList.remove(index);

        gson.toJson(editionsList, writer);
        writer.close();
        return false;
    }
    /**
     * Este método nos permite obtener una edicion en concreto
     * @param index Indice que nos indica la posicion del objeto deseado
     * @return Edition Retorna la edicion deseada
     */
    @Override
    public Edition findByIndex(int index) throws FileNotFoundException {
        return editions[index];
    }
}
