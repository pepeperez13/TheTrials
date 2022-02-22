package business;

import java.util.ArrayList;
import java.util.LinkedList;


public class TrialManager {
    private final TrialDAO trialDAO;

    /**
     * Método constructor que crea un DAO de pruebas nuevo
     * @param trialDAO DAO de pruebas
     */
    public TrialManager (TrialDAO trialDAO) {
        this.trialDAO = trialDAO;
    }

    /**
     * Método que permite guardar una nueva prueba
     * @param name Nombre del artículo a publicar
     * @param magazine Nombre de la revista donde se publica
     * @param quartile Quartil de la revista
     * @param accepted Probabilidad de que el artículo sea aceptado
     * @param revised Probabilidad de que el artículo sea revisado
     * @param rejected Probabilidad de que el artículo sea rechazado
     */
    public void addTrial (String name, String magazine, String quartile, int accepted, int revised, int rejected, boolean used) {
        Article article = new Article(name, magazine, quartile, accepted, revised, rejected, used);
        trialDAO.create(article);
    }

    /**
     * Método que retorna en forma de lista toda la información completa de cada prueba (artículo)
     * @return Lista con todos los artículos (pruebas)
     */
    public LinkedList<Article> getTrials() {
        return trialDAO.readAll();
    }

    /**
     * Método que retorna toda la información de un artículo concreto según su nombre
     * @param name Nombre del artículo solicitado
     * @return Información del artículo solicitado
     */
    public Article getTrialByName (String name) {
        int i;
        boolean found = false;
        LinkedList<Article> articles = trialDAO.readAll();
        for (i = 0; i < articles.size() && !found; i++) {
            if (articles.get(i).getArticleName().equals(name)) {
                found = true;
            }
        }
        return articles.get(i - 1);
    }

    /**
     * Nos permite saber la posición de guardado de una prueba según su nombre
     * @param name Nombre de la prueba que queremos buscar
     * @return Posición de la prueba solicitada
     */
    public int getIndexByName (String name) {
        int i;
        boolean found = false;
        LinkedList<Article> articles = trialDAO.readAll();
        for (i = 0; i < articles.size() && !found; i++) {
            if (articles.get(i).getArticleName().equals(name)) {
                found = true;
            }
        }
        return i - 1;
    }

    /**
     * Mñetodo que retorna en forma de lista los nombres de todos los artículos
     * @return Lista con los nombres de todos los artículos
     */
    public LinkedList<String> getTrialsNames() {
        LinkedList<Article> articles =  trialDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (Article article : articles) {
            nombres.add(article.getArticleName());
        }
        return nombres;
    }

    /**
     * Método que obtiene los nombres de los artículos según sus posiciones en el fichero
     * @param indexes Posiciones de los artículos de los que se quiere obtener los nombres
     * @return String con los nombres de los artículos
     */
    public String[] getTrialsNamesByIndexes (ArrayList<Integer> indexes) {
        LinkedList<String> allNames = getTrialsNames(); // Obtenemos los nombres de todas las pruebas disponibles
        LinkedList<String> names = new LinkedList<>();  // Array de strings donde se guardaran los nombres que necesitemos

        for (Integer index : indexes) {
            names.add(allNames.get(index));
        }
        String[] stringNames = new String[names.size()];
        for (int i = 0; i < names.size(); i++) {
            stringNames[i] = names.get(i);
        }
        return stringNames;
    }

    /**
     * Método que elimina un artículo según su posición en el fichero
     * @param index Posición del artículo a eliminar
     */
    public void deleteTrial (int index) {
        trialDAO.delete(index);
    }

    /**
     * Obtiene toda la información de un artículo según su posición
     * @param index Posición del artículo que queremos
     * @return Articulo solicitado
     */
    public Article getTrial (int index) {
        return trialDAO.readAll().get(index);
    }

    /**
     * Este método permite modificar el estado de varias pruebas a "en uso"
     * @param indexes Lista con las posiciones de las pruebas que queremos poner en uso
     */
    public void setInUseByIndexes (ArrayList<Integer> indexes) {
        int j = 0;

        for (int i = 0; i < getTrials().size() && j < indexes.size(); i++) {
            if (i == indexes.get(j)) {
                // Creamos un nuevo artículo con los mismos datos, pero con el "inUse" a true
                Article updatedArticle = new Article(getTrial(i).getArticleName(),
                        getTrial(i).getMagazineName(), getTrial(i).getQuartile(),
                        getTrial(i).getAcceptedProbability(), getTrial(i).getRevisedProbability(),
                        getTrial(i).getRejectedProbability(), true);
                trialDAO.changeLine(i, updatedArticle);
                j++;
            }
        }
    }

    /**
     * Nos permite modificar el estado de una prueba a "no en uso"
     * @param index Posición de guardado de la prueba a cambiar
     */
    public void setInUnusedByIndex (int index) {
        Article article = new Article(getTrial(index).getArticleName(),
                getTrial(index).getMagazineName(), getTrial(index).getQuartile(),
                getTrial(index).getAcceptedProbability(), getTrial(index).getRevisedProbability(),
                getTrial(index).getRejectedProbability(), false);
        trialDAO.changeLine(index, article);
    }

    /**
     * Comprueba la existencia de una prueba según un nombre
     * @param name Nombre de la prueba que queremos buscar
     * @return Indicará si existe la prueba con ese nombre o no
     */
    public boolean checkExistence (String name) {
        return getTrialsNames().contains(name);
    }
    */
}
