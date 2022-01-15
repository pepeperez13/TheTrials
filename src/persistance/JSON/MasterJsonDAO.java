package persistance.JSON;


import business.typeTrials.MasterStudies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.MasterDAO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class MasterJsonDAO implements MasterDAO {

    private static final String filename = "files/team.json";
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
}
