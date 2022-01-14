package persistance.CSV;

import business.typeTrials.DoctoralThesis;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class DoctoralCsvDAO implements persistance.DoctoralDAO {
    private static String separator = ",";
    private static File file = new File ("files/doctoral.csv");

    @Override
    public boolean create(DoctoralThesis doctoralThesis) {
        return writeFile(doctoralThesis);
    }

    private String doctoralToCsv(DoctoralThesis doctoralThesis) {
        return doctoralThesis.getName() + separator + doctoralThesis.getFieldOfStudy() + separator + doctoralThesis.getDifficulty();
    }

    private boolean writeFile (DoctoralThesis doctoralThesis) {
        FileReader fr = null;
        BufferedReader bf = null;
        PrintWriter pw = null;
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
            pw.println(doctoralToCsv(doctoralThesis));
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

    @Override
    public LinkedList<DoctoralThesis> readAll() {
        return readFile ();
    }

    private DoctoralThesis doctoralFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new DoctoralThesis(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    private LinkedList<DoctoralThesis> readFile () {
        FileReader fr = null;
        BufferedReader bf = null;
        LinkedList<DoctoralThesis> doctoralTheses = new LinkedList<>();
        try {
            List<String> lines = new LinkedList<>();
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            for (String line : lines) {
                doctoralTheses.add(doctoralFromCsv(line));
            }
        } catch (IOException e) {
            return doctoralTheses;
        }
        return doctoralTheses;
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
        return updateLine(index);
    }

    private boolean updateLine (int index) {
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
}
