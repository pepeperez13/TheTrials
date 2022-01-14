package persistance.CSV;

import business.typeTrials.MasterStudies;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class MasterCsvDAO implements persistance.MasterDAO {
    private static String separator = ",";
    private static File file = new File ("files/master.csv");

    @Override
    public boolean create(MasterStudies masterStudies) {
        return writeFile(masterStudies);
    }

    private String masterToCsv(MasterStudies masterStudies) {
        return masterStudies.getName() + separator + masterStudies.getNom() + separator + masterStudies.getNumberCredits() +
                separator + masterStudies.getProbability();
    }

    private boolean writeFile (MasterStudies masterStudies) {
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
            pw.println(masterToCsv(masterStudies));
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
    public LinkedList<MasterStudies> readAll() {
        return readFile ();
    }

    private MasterStudies masterFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new MasterStudies(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }

    private LinkedList<MasterStudies> readFile () {
        FileReader fr = null;
        BufferedReader bf = null;
        LinkedList<MasterStudies> masterStudies = new LinkedList<>();
        try {
            List<String> lines = new LinkedList<>();
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            for (String line : lines) {
                masterStudies.add(masterFromCsv(line));
            }
        } catch (IOException e) {
            return masterStudies;
        }
        return masterStudies;
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
