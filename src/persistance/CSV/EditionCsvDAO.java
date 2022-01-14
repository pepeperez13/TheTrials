package persistance.CSV;

import business.Edition;
import persistance.EditionDAO;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class EditionCsvDAO implements EditionDAO {
    private static String separator = ",";
    private static File file = new File("files/editions.csv");

    /**
     * Este método transforma un elemento de la clase Edition
     * para darle el formato requerido
     * @param edition
     * @return String
     */
    private String editionToCsv (Edition edition) {
        return (edition.getYear() + separator + edition.getNumPlayers() + separator + edition.getNumTrials() + separator + String.join("-", edition.getTrialNames()));
    }

    /**
     * Este método divide una cadena de caracteres
     * y llama al constructor Edition() para asi obtener un
     * nuevo objeto
     * @param csv Cadena a separar
     * @return Edition Objeto obtenido despues de separa la cadena
     */
    private Edition editionFromCsv (String csv) {
        String[] parts = csv.split(separator);
        return new Edition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3].split("-"));
    }

    /**
     * Método, público, que se encarga de llamar al método  para guardar un objeto de la clase Edition
     * en un fichero .CSV
     * @param edition Objeto a guardar
     * @return boolean Retorna si se ha podido o no guardar en el fichero
     */
    @Override
    public boolean create(Edition edition) {
        return writeFile(edition);
    }

    /**
     * Método, público, que se encarga de llamar al método que se encarga de leer todos las lineas del .csv
     * @return Retorna una lista que contiene todas las ediciones
     */
    @Override
    public LinkedList<Edition> readAll() { //Todo el fichero articulos
        return readFile();
    }

    /**
     * Este método nos permite obtener una edicion en concreto
     * @param index Indice que nos indica la posicion del objeto deseado
     * @return Edition Retorna la edicion deseada
     */
    @Override
    public Edition findByIndex(int index) {
        try {
            List<String> list = Files.readAllLines(file.toPath());
            return editionFromCsv(list.get(index-1));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Método que elimina una edición según el año
     * @param "Index" Entero que nos indica la edición a eliminar
     * @return boolean Indica si se ha podido o no eliminar una edición
     */
    @Override
    public boolean delete(int index) {
        return updateLine(index);
    }

    /**
     * Método, privado, que se encarga de guardar la edicion en un .CSV
     * @param edition Edición a guardar
     * @return boolean Indica si se ha podido o no guardar un elemento
     */
    private boolean writeFile (Edition edition) {
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
            pw.println(editionToCsv(edition));
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

    /**
     * Método, privado, que se encarga de .CSV
     * @return LinkedList<Edition> Lista que contiene todas las ediciones leídas del
     */

    /**
     *
     * @return
     */
    private LinkedList<Edition> readFile () {
        FileReader fr = null;
        BufferedReader bf = null;
        LinkedList<Edition> editions = new LinkedList<>();
        try {
            List<String> lines = new LinkedList<>();
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String linea;
            while ((linea=bf.readLine())!=null) {
                lines.add(linea);
            }
            for (String line : lines) {
                editions.add(editionFromCsv(line));
            }
        } catch (IOException e) {
            return editions;
        }
        return editions;
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
