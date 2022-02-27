package persistance.JSON;


import business.typeTrials.MasterStudies;
import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.MasterDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class MasterJsonDAO implements MasterDAO {

    private static final File file = new File("files/masters.json");
    private static final String filename = "files/masters.json";
    private final Gson gson;
    private final MasterStudies[] masters;

    public MasterJsonDAO () throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        masters = gson.fromJson(gson.newJsonReader(new FileReader(filename)), MasterStudies[].class);
    }

    @Override
    public boolean create(MasterStudies masterStudies) throws IOException {
        FileWriter writer = new FileWriter(filename);

        LinkedList<MasterStudies> mastersList = new LinkedList<>();
        if (masters != null) {
            mastersList = new LinkedList<>(Arrays.asList(masters));
        }

        mastersList.add(masterStudies);
        gson.toJson(mastersList, writer);
        writer.close();

        return false;
    }

    @Override
    public LinkedList<MasterStudies> readAll() {
        return new LinkedList<>(Arrays.asList(masters));
    }

    @Override
    public MasterStudies findByIndex(int index) {
        return masters[index];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<MasterStudies> mastersList = new LinkedList<>(Arrays.asList(masters));
        mastersList.remove(index);

        gson.toJson(mastersList, writer);
        writer.close();
        return false;
    }

    @Override
    public boolean changeLine(int index, MasterStudies master) throws IOException {
        FileWriter writer = new FileWriter(filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<MasterStudies> mastersList = new LinkedList<>(Arrays.asList(masters));

        mastersList.remove(index);
        mastersList.add(index, master);

        gson.toJson(mastersList, writer);
        writer.close();
        return false;
    }
}
