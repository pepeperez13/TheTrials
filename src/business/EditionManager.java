package business;

import persistance.CSV.EditionCsvDAO;
import persistance.EditionDAO;
import persistance.JSON.EditionJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class EditionManager {
    private EditionDAO editionDAO;

    public EditionManager (DataSourceOptions options) {
        switch (options) {
            case JSON -> editionDAO = new EditionJsonDAO();
            case CSV -> editionDAO = new EditionCsvDAO();
        }
    }

    /**
     * Método que permite guardar una nueva edición
     * @param year Año de la edición
     * @param numPlayers Número de jugadores que participarán en la edición
     * @param numTrials Número de pruebas que habrán en la edición
     * @param nombrePruebas Nombres de todos las pruebas (artículos) que componen la ecición
     * @return Booleano que nos permite saber si la edición se ha creado correctamente
     */
    public boolean addEdition (int year, int numPlayers, int numTrials, String[] nombrePruebas) throws IOException {
        Edition edition = new Edition(year, numPlayers, numTrials, nombrePruebas);
        return editionDAO.create(edition);
    }

    /**
     * Método que lee el fichero de ediciones y devuelve una lista con todas las líneas
     * @return Lista donde cada elemento es una línea del fichero
     */
    public LinkedList<Edition> getEditions () throws FileNotFoundException {
        return editionDAO.readAll();
    }

    /**
     * Método que permite obtener toda la información de una edición según su posición de guardado
     * @param index Indice que nos permitirá acceder a la información en la línea del fichero concreta
     * @return Información de la edición solicitada
     */
    public Edition getEditionByIndex (int index) throws FileNotFoundException {
        Edition edition = editionDAO.findByIndex(index);
        return edition;
    }

    /**
     * Método que permite obtener toda la información de la edición del año actual
     * @return Información de la edición solicitada
     */
    public Edition getEditionCurrentYear () throws FileNotFoundException {
        Calendar calendar = new GregorianCalendar();
        boolean found = false;
        LinkedList<Edition> editions = editionDAO.readAll();
        int i;
        for (i = 0; i < editions.size() && !found; i++) {
            if (editions.get(i).getYear() == calendar.get(Calendar.YEAR)) {
                found = true;
            }
        }
        return editions.get(i - 1);
    }

    /**
     * Método que permite obtener en formato lista todos los nombres de las pruebas que contiene la edición solicitada
     * @param index Indice que nos permitirá acceder a la información en la línea del fichero concreta
     * @return Lista con los nombres de las pruebas
     */
    public LinkedList<String> getEditionTrialsNames (int index) throws FileNotFoundException {
        Edition edition = findByIndex(index);
        LinkedList<String> editionTrialsNames = new LinkedList<>();
        for (int i = 0; i < edition.getNumTrials(); i++) {
            editionTrialsNames.add(edition.getTrialNameByIndex(i));
        }
        return editionTrialsNames;
    }

    private Edition findByIndex (int index) throws FileNotFoundException {
        return editionDAO.readAll().get(index);
    }

    /**
     * Método que obtiene los nombres de las ediciones en un formato determinado
     * @return Lista con los nombres de las ediciones
     */
    public LinkedList<String> getEditionsNames () throws FileNotFoundException {
        LinkedList<String> editionsNames = new LinkedList<>();
        for (Edition edition: editionDAO.readAll()) {
            editionsNames.add("The trials " + edition.getYear());
        }
        return editionsNames;
    }


    /**
     * Método que duplica una edición determinada, cambiando solo año y número de jugadores
     * @param index Posición de la edición que se quiere duplicar
     * @param year Año de la nueva edición
     * @param numPlayers Número de jugadores de la nueva edición
     * @return Booleano que nos permite saber si la edición se ha duplicado correctamente
     */
    public boolean duplicateEdition (int index, int year, int numPlayers) throws IOException {
        Edition newEdition = editionDAO.findByIndex(index); // Cargamos los datos de la edición que queremos copiar
        newEdition = new Edition(year, numPlayers, newEdition.getNumTrials(), newEdition.getTrialNames()); // Creamos nueva edición con los mismas pruebas, pero cambiando año y players
        return editionDAO.create(newEdition);
    }


    /**
     * Método que elimina la edición de un año concreto
     * @param year Año de la edición a eliminar
     * @return Bolleano que indica si la edición se ha podido eliminar
     */
    public boolean deleteEdition (int year) throws IOException {
        return editionDAO.delete(searchByYear(year));
    }

    public LinkedList<String> getAllTrialsNamesInUse () throws FileNotFoundException { //Nombres en uso
        LinkedList<String> namesTrials = new LinkedList<>();
        for (int i = 0; i < getEditions().size(); i++) {
            for (int j = 0; j < getEditions().get(i).getNumTrials(); j++) {
                if (!namesTrials.contains(getEditions().get(i).getTrialNameByIndex(j))) {
                    namesTrials.add(getEditions().get(i).getTrialNameByIndex(j));
                }
            }
        }
        return namesTrials;
    }

    private int searchByYear (int year) throws FileNotFoundException {
        int index = 0;
        for (int i = 0; i < getEditions().size(); i++) {
            if (year == getEditions().get(i).getYear()) {
                index = i;
            }
        }
        return index;
    }
}
