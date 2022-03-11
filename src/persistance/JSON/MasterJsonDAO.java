package persistance.JSON;


import business.Edition;
import business.typeTrials.MasterStudies;
import business.typeTrials.PaperPublication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.MasterDAO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class MasterJsonDAO implements MasterDAO {
    private String filename = "masters.json";
    private String filePath = "files";
    private final File file = new File(filePath, filename);
    private Gson gson;
    private MasterStudies[] masters;

    public MasterJsonDAO () throws FileNotFoundException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gson = new GsonBuilder().setPrettyPrinting().create();
            masters = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), MasterStudies[].class);
        } else {
            System.out.println("\nThe file already exist.");
            gson = new GsonBuilder().setPrettyPrinting().create();
            masters = gson.fromJson(gson.newJsonReader(new FileReader("files/"+filename)), MasterStudies[].class);
        }
    }

    @Override
    public boolean create(MasterStudies masterStudies) throws IOException {
        FileWriter writer = new FileWriter("files/"+filename);

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
        try {
            return new LinkedList<>(Arrays.asList(masters));
        } catch (NullPointerException e) {
            return new LinkedList<>();
        }
    }

    @Override
    public MasterStudies findByIndex(int index) {
        return masters[index - 1];
    }

    @Override
    public boolean delete(int index) throws IOException {
        FileWriter writer = new FileWriter("files/"+filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<MasterStudies> mastersList = new LinkedList<>(Arrays.asList(masters));
        mastersList.remove(index - 1);

        gson.toJson(mastersList, writer);
        writer.close();
        return false;
    }

    @Override
    public boolean changeLine(int index, MasterStudies master) throws IOException {
        FileWriter writer = new FileWriter("files/"+filename);

        // Nunca va estar vacia (comprobamos antes de llamar)
        LinkedList<MasterStudies> mastersList = new LinkedList<>(Arrays.asList(masters));

        mastersList.remove(index - 1);
        mastersList.add(index - 1, master);

        gson.toJson(mastersList, writer);
        writer.close();
        return false;
    }
}
