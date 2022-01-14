package persistance.CSV;

import business.Player;
import persistance.TeamDAO;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que gestiona la lectura y escritura del fichero team
 * @author Jose Perez
 * @author Abraham Cedeño
 */
public class TeamCsvDAO implements TeamDAO {
    private static final File file = new File("files/team.csv");
    private static final String separator = ",";

    /**
     * Este método transforma un String y un entero
     * para darle el formato requerido
     * @param name Nombre del jugador
     * @param PI Puntuacion del jugador
     * @return String Cadena de caracteres a guardar
     */
    private String playerToCsv (String name, int PI) {
        return name + separator +  PI;
    }

    @Override
    public Player playerFromCsv(String csv) { //Una linea del archivo Aritulos
        String[] parts = csv.split(separator);
        return new Player(parts[0], Integer.parseInt(parts[1]));
    }

    /**
     * Método que ll
     * @param name Nombre del jugador
     * @param PI Puntuacion del jugador
     * @return booleano
     */
    @Override
    public boolean create(String name, int PI) {
        return writeLine(name, PI);
    }

    @Override
    public boolean delete(int index) {
        return deleteLine(index);
    }

    @Override
    public LinkedList<Player> readAll() { //Todo el fichero articulos
        return readFile();
    }

    @Override
    public boolean changeLine(int index, String name, int PI) {
        return updateLine(index,name, PI);
    }

    @Override
    public boolean emptyFile() {
        FileReader fr = null;
        BufferedReader bf;
        PrintWriter pw;
        FileWriter fw;
        LinkedList<String> lines = new LinkedList<>();
        try {
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
                lines.remove(linea);
            }
            fr.close();
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            for (String line:lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            return false;
        }
        finally {
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return false;
    }

    private boolean writeLine (String player, int PI) {
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
            pw.println(playerToCsv(player, PI));
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
                if (null != fileWriter) {
                    fileWriter.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    private LinkedList<Player> readFile () {
        FileReader fr;
        BufferedReader bf;
        LinkedList<Player> player = new LinkedList<>();
        try {
            List<String> lines = new LinkedList<>();
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            for (String line : lines) {
                player.add(playerFromCsv(line));
            }
        } catch (IOException e) {
            return player;
        }
        return player;
    }

    private boolean updateLine (int index, String name, int PI) {
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
            lines.add(index, playerToCsv(name, PI));
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

