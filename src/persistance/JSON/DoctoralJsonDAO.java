package persistance.JSON;

import business.typeTrials.DoctoralThesis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.DoctoralDAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class DoctoralJsonDAO implements DoctoralDAO {

    private static final String filename = "files/team.json";
    private final Gson gson;
    private final DoctoralThesis[] doctorals;

    public DoctoralJsonDAO () throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        doctorals = gson.fromJson(gson.newJsonReader(new FileReader(filename)), DoctoralThesis[].class);
    }

    @Override
    public boolean create(DoctoralThesis doctoralThesis) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<DoctoralThesis> doctoralsList = new LinkedList<>();
        if (doctorals != null) { // Sólo leeremos elementos si el json no está vacío
            doctoralsList = new LinkedList<>(Arrays.asList(doctorals));
        }

        doctoralsList.add(doctoralThesis);
        gson.toJson(doctoralsList, writer);
        writer.close();
        return false;
    }

    @Override
    public LinkedList<DoctoralThesis> readAll() {
        // Nunca va estar vacia (comprobamos antes de llamar)
        return new LinkedList<>(Arrays.asList(doctorals));
    }

    @Override
    public DoctoralThesis findByIndex(int index) {
        return doctorals[index];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<DoctoralThesis> doctoralsList = new LinkedList<>(Arrays.asList(doctorals));
        doctoralsList.remove(index);

        gson.toJson(doctoralsList, writer);
        writer.close();

        return false;
    }
}
