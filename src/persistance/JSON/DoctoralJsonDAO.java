package persistance.JSON;

import business.typeTrials.Budget;
import business.typeTrials.DoctoralThesis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.DoctoralDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class DoctoralJsonDAO implements DoctoralDAO {
    private String filename = "doctorals.json";
    private String filePath = "files";
    private final File file = new File(filePath, filename);
    private Gson gson;
    private DoctoralThesis[] doctorals;

    public DoctoralJsonDAO () throws FileNotFoundException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson = new GsonBuilder().setPrettyPrinting().create();
            doctorals = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), DoctoralThesis[].class);
        } else {
            System.out.println("\nThe file already exist.");
            gson = new GsonBuilder().setPrettyPrinting().create();
            doctorals = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), DoctoralThesis[].class);
        }
    }

    @Override
    public boolean create(DoctoralThesis doctoralThesis) throws IOException {
        FileWriter writer = new FileWriter("files/"+filename);

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
        try {
            return new LinkedList<>(Arrays.asList(doctorals));
        } catch (NullPointerException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public DoctoralThesis findByIndex(int index) {
        return doctorals[index - 1];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter("files/"+filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<DoctoralThesis> doctoralsList = new LinkedList<>(Arrays.asList(doctorals));
        doctoralsList.remove(index - 1);

        gson.toJson(doctoralsList, writer);
        writer.close();

        return false;
    }
    @Override
    public boolean changeLine(int index, DoctoralThesis doctoral) throws IOException {
        FileWriter writer = new FileWriter("files/"+filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<DoctoralThesis> doctoralsList = new LinkedList<>(Arrays.asList(doctorals));

        doctoralsList.remove(index - 1);
        doctoralsList.add(index - 1, doctoral);

        gson.toJson(doctoralsList, writer);
        writer.close();
        return false;
    }

}
