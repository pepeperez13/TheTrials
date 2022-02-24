package persistance.JSON;

import business.typeTrials.GenericTrialInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.AllTrialsDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class AllTrialsJsonDAO implements AllTrialsDAO {

    private static final String filename = "files/budgets.json";
    private static final File file = new File("files/budgets.json");
    private final Gson gson;
    private final GenericTrialInfo[] genericTrials;

    public AllTrialsJsonDAO () throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        genericTrials = gson.fromJson(gson.newJsonReader(new FileReader(filename)), GenericTrialInfo[].class);
    }

    @Override
    public boolean create(GenericTrialInfo name) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<GenericTrialInfo> namesList = new LinkedList<>();
        if (namesList != null) { // Sólo leeremos elementos si el json no está vacío
            namesList = new LinkedList<>(Arrays.asList(genericTrials));
        }

        namesList.add(name);
        gson.toJson(namesList, writer);
        writer.close();

        return false;
    }

    @Override
    public LinkedList<GenericTrialInfo> readAll() {
        // Nunca va estar vacia (comprobamos antes de llamar)
        return new LinkedList<>(Arrays.asList(genericTrials));
    }

    @Override
    public GenericTrialInfo findByIndex(int index) {
        return genericTrials[index];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<GenericTrialInfo> namesList = new LinkedList<>(Arrays.asList(genericTrials));
        namesList.remove(index);

        gson.toJson(namesList, writer);
        writer.close();

        return false;
    }
}
