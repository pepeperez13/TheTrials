package persistance.CSV;

import business.typeTrials.PaperPublication;
import persistance.PaperDAO;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class PaperCsvDAO implements PaperDAO {
    private static File file = new File("files/trials.csv");
    private static String separator = ",";

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
    public boolean create(PaperPublication article) {
        return write(article);
    }

    @Override
    public LinkedList<PaperPublication> readAll() { //Todo el fichero articulos
        return read();
    }

    @Override
    public boolean delete(int index) throws IOException {
        return deleteLine(index);
    }

    @Override
    public boolean changeLine(int index, PaperPublication paperPublication) {
        return updateLine(index, paperPublication);
    }

    private boolean write (PaperPublication article) {
        FileReader fr = null;
        BufferedReader bf;
        PrintWriter pw;
        FileWriter fw = null;
        LinkedList<String> lines = new LinkedList<>();
        try {
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            fr.close();
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            for (String line:lines) {
                pw.println(line);
            }
            pw.println(trialToCsv(article));
        } catch (IOException e) {
            return false;
        }
        finally {
            try{
                if( null != fr ){
                    fr.close();
                }
                if (null != fw){
                    fw.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return true;
    }

    private LinkedList<PaperPublication> read () {
        FileReader fr;
        BufferedReader bf;
        LinkedList<PaperPublication> articles = new LinkedList<>();
        try {
            List<String> lines = new LinkedList<>();
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine()) != null) {
                lines.add(linea);
            }
            for (String line : lines) {
                articles.add(trialFromCsv(line));
            }
        } catch (IOException e) {
            return articles;
        }
        return articles;
    }

    private boolean deleteLine (int index) {
        // Inicializar
        FileReader fileReader = null;
        BufferedReader bufferedReader;
        PrintWriter printWriter;
        FileWriter fileWriter = null;
        LinkedList<String> lines = new LinkedList<>();

        // Leer y actualizar
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            // Mientras no se acabe el fichero, leemos
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            lines.remove(index);
            fileReader.close();
            // Volvemos a escribir toda la info en el fichero
            fileWriter = new FileWriter(file);
            printWriter = new PrintWriter(fileWriter);
            for (String linea : lines) {
                printWriter.println(linea);
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (null != fileReader) {
                    fileReader.close();
                }
                if (null != fileReader) {
                    fileWriter.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    private boolean updateLine (int index, PaperPublication article) {
        // Inicializar
        FileReader fileReader = null;
        BufferedReader bufferedReader;
        PrintWriter printWriter;
        FileWriter fileWriter = null;
        LinkedList<String> lines = new LinkedList<>();

        // Leer y actualizar
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            // Mientras no se acabe el fichero, leemos
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            // Eliminamos artículo y volvemos a guardar el nuevo con el inUse activado
            lines.remove(index);
            lines.add(index, trialToCsv(article));
            fileReader.close();
            // Volvemos a escribir toda la info en el fichero
            fileWriter = new FileWriter(file);
            printWriter = new PrintWriter(fileWriter);
            for (String linea : lines) {
                printWriter.println(linea);
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (null != fileReader) {
                    fileReader.close();
                }
                if (null != fileWriter) {
                    fileWriter.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }
}
