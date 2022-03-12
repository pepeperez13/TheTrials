package persistance.CSV;

import business.ManagersTrials.TrialTypeOptions;
import business.PlayerTypeOptions;
import business.typeTrials.GenericTrial;
import business.typeTrials.PaperPublication;
import persistance.GenericTrialDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class GenericTrialCsvDAO implements GenericTrialDAO {

    private static String separator = ",";
    private String fileName = "generics.csv";
    private String filePath = "files";
    private File file = new File(filePath, fileName);

    public GenericTrialCsvDAO () {
        if (!file.exists() ) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nThe file already exist.");
        }
    }

    private String genericTrialToCsv (GenericTrial name) {
        return name.getName() + separator + name.getType();
    }

    private GenericTrial genericFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new GenericTrial(parts[0], TrialTypeOptions.valueOf(parts[1]));
    }

    @Override
    public boolean create(GenericTrial generic)  {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            list.add(genericTrialToCsv(generic));
            Files.write(file.toPath(), list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<GenericTrial> readAll() {
        try{
            LinkedList<GenericTrial> genericTrials = new LinkedList<>();
            List<String> list = Files.readAllLines(file.toPath());
            for (String line: list) {
                genericTrials.add(genericFromCsv(line));
            }
            return genericTrials;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public GenericTrial findByIndex(int index) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            return genericFromCsv(list.get(index - 1));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            List<String> genericTrials = Files.readAllLines(file.toPath());
            genericTrials.remove(index);
            Files.write(file.toPath(), genericTrials);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
