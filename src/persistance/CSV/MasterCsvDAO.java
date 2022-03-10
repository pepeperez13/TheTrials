package persistance.CSV;

import business.typeTrials.MasterStudies;
import business.typeTrials.PaperPublication;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class MasterCsvDAO implements persistance.MasterDAO {
    private static String separator = ",";
    private String fileName = "masters.csv";
    private String filePath = "files";
    private File file = new File(filePath, fileName);

    public MasterCsvDAO () throws IOException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String masterToCsv(MasterStudies masterStudies) {
        return masterStudies.getName() + separator + masterStudies.getNom() + separator + masterStudies.getNumberCredits() +
                separator + masterStudies.getProbability() + separator + masterStudies.isInUse();
    }

    private MasterStudies masterFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new MasterStudies(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Boolean.valueOf(parts[4]));
    }

    @Override
    public boolean create(MasterStudies masterStudies) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            list.add(masterToCsv(masterStudies));
            Files.write(file.toPath(), list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<MasterStudies> readAll() {
        try{
            LinkedList<MasterStudies> masters = new LinkedList<>();
            List<String> list = Files.readAllLines(file.toPath());
            for (String line: list) {
                masters.add(masterFromCsv(line));
            }
            return masters;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MasterStudies findByIndex(int index) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            return masterFromCsv(list.get(index-1));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            List<String> budgets = Files.readAllLines(file.toPath());
            budgets.remove(index);
            Files.write(file.toPath(), budgets);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean changeLine (int index, MasterStudies masterStudies) {
        try {
            List<String> masters = Files.readAllLines(file.toPath());
            masters.remove(index);
            masters.add(index, masterToCsv(masterStudies));
            Files.write(file.toPath(), masters);
            return true;
        }catch (IOException e) {
            return false;
        }
    }


}
