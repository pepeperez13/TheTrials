package persistance.CSV;

import business.typeTrials.PaperPublication;
import persistance.PaperDAO;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class PaperCsvDAO implements PaperDAO {
    //private static final File file = new File("files/trials.csv");
    private static final String separator = ",";
    private String fileName = "paper.csv";
    private String filePath = "files";
    private File file = new File(filePath, fileName);

    public PaperCsvDAO () throws IOException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String trialToCsv (PaperPublication article) {
        return article.getArticleName() + separator + article.getMagazineName() + separator + article.getQuartile() +
                separator + article.getAcceptedProbability() + separator + article.getRevisedProbability() +
                separator + article.getRejectedProbability() + separator + article.InUse();
    }

    private PaperPublication trialFromCsv(String csv) { //Una linea del archivo Aritulos
        String[] parts = csv.split(separator);

        return new PaperPublication(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Boolean.valueOf(parts[6]));
    }

    @Override
    public boolean create (PaperPublication article) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            list.add(trialToCsv(article));
            Files.write(file.toPath(), list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public LinkedList<PaperPublication> readAll ()  {
        try{
            LinkedList<PaperPublication> articles = new LinkedList<>();
            List<String> list = Files.readAllLines(file.toPath());
            for (String line: list) {
                articles.add(trialFromCsv(line));
            }
            return articles;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete (int index) {
        try {
            List<String> articles = Files.readAllLines(file.toPath());
            articles.remove(index);
            Files.write(file.toPath(), articles);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean changeLine (int index, PaperPublication article) {
        try {
            List<String> articles = Files.readAllLines(file.toPath());
            articles.remove(index);
            articles.add(index, trialToCsv(article));
            Files.write(file.toPath(), articles);
            return true;
        }catch (IOException e) {
            return false;
        }
    }

}
