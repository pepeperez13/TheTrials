package persistance.CSV;

import business.typeTrials.DoctoralThesis;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class DoctoralCsvDAO implements persistance.DoctoralDAO {
    private static String separator = ",";
    private static File file = new File ("files/doctoral.csv");



    private String doctoralToCsv(DoctoralThesis doctoralThesis) {
        return doctoralThesis.getName() + separator + doctoralThesis.getFieldOfStudy() + separator + doctoralThesis.getDifficulty();
    }

    private DoctoralThesis doctoralFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new DoctoralThesis(parts[0], parts[1], Integer.parseInt(parts[2]), Boolean.valueOf(parts[3]));
    }

    @Override
    public boolean create(DoctoralThesis doctoralThesis) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            list.add(doctoralToCsv(doctoralThesis));
            Files.write(file.toPath(), list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<DoctoralThesis> readAll() {
        try{
            LinkedList<DoctoralThesis> doctorals = new LinkedList<>();
            List<String> list = Files.readAllLines(file.toPath());
            for (String line: list) {
                doctorals.add(doctoralFromCsv(line));
            }
            return doctorals;
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public DoctoralThesis findByIndex(int index) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            return doctoralFromCsv(list.get(index-1));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int index) {
        try {
            List<String> doctorals = Files.readAllLines(file.toPath());
            doctorals.remove(index);
            Files.write(file.toPath(), doctorals);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
