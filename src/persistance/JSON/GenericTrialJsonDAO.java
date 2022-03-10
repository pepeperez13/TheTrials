package persistance.JSON;

import business.typeTrials.GenericTrial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.GenericTrialDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class GenericTrialJsonDAO implements GenericTrialDAO {
    private String filename = "generics.json";
    private String filePath = "files";
    private final File file = new File(filePath, filename);
    private final Gson gson;
    private final GenericTrial[] genericTrials;

    public GenericTrialJsonDAO() throws FileNotFoundException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        genericTrials = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), GenericTrial[].class);
    }

    @Override
    public boolean create(GenericTrial name) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<GenericTrial> namesList = new LinkedList<>();
        if (genericTrials != null) { // Sólo leeremos elementos si el json no está vacío
            namesList = new LinkedList<>(Arrays.asList(genericTrials));
        }

        namesList.add(name);
        gson.toJson(namesList, writer);
        writer.close();

        return false;
    }

    @Override
    public LinkedList<GenericTrial> readAll() {
        // Nunca va estar vacia (comprobamos antes de llamar)
        try {
            return new LinkedList<>(Arrays.asList(genericTrials));
        } catch (NullPointerException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public GenericTrial findByIndex(int index) {
        return genericTrials[index];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<GenericTrial> namesList = new LinkedList<>(Arrays.asList(genericTrials));
        namesList.remove(index);

        gson.toJson(namesList, writer);
        writer.close();

        return false;
    }
}
