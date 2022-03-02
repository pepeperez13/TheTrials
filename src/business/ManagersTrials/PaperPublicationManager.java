package business.ManagersTrials;

import business.DataSourceOptions;
import business.typeTrials.PaperPublication;
import persistance.CSV.PaperCsvDAO;
import persistance.JSON.PaperJsonDAO;
import persistance.PaperDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PaperPublicationManager {
    private PaperDAO paperDAO;
    private GenericTrialManager genericTrialManager;

    public PaperPublicationManager(DataSourceOptions options, GenericTrialManager genericTrialManager) throws IOException {
        switch (options) {
            case JSON -> paperDAO = new PaperJsonDAO();
            case CSV -> paperDAO = new PaperCsvDAO();
        }
        this.genericTrialManager = genericTrialManager;
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
    public void addPaper (String name, String magazine, String quartile, int accepted, int revised, int rejected, boolean bool) throws IOException {
        PaperPublication article = new PaperPublication(name, magazine, quartile, accepted, revised, rejected,bool);
        paperDAO.create(article);
    }

    /**
     * Método que retorna en forma de lista toda la información completa de cada prueba (artículo)
     * @return Lista con todos los artículos (pruebas)
     */
    public LinkedList<PaperPublication> getPapers () throws FileNotFoundException {
        return paperDAO.readAll();
    }

    /**
     * Método que retorna toda la información de un artículo concreto según su nombre
     * @param name Nombre del artículo solicitado
     * @return Información del artículo solicitado
     */
    public PaperPublication getPaperByName (String name) throws FileNotFoundException {
        int i;
        boolean found = false;
        LinkedList<PaperPublication> articles = paperDAO.readAll();
        for (i = 0; i < articles.size() && !found; i++) {
            if (articles.get(i).getArticleName().equals(name)) {
                found = true;
            }
        }
        return articles.get(i - 1);
    }

    public int getIndexByName (String name) throws IOException {
        int i;
        boolean found = false;
        LinkedList<PaperPublication> articles = paperDAO.readAll();
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
    public LinkedList<String> getPapersNames () throws FileNotFoundException {
        LinkedList<PaperPublication> articles =  paperDAO.readAll();
        LinkedList<String> nombres = new LinkedList<>();
        for (int i = 0; i < articles.size(); i++) {
            nombres.add(articles.get(i).getArticleName());
        }
        return nombres;
    }

    /**
     * Método que obtiene los nombres de los artículos según sus posiciones en el fichero
     * @param indexes Posiciones de los artículos de los que se quiere obtener los nombres
     * @return String con los nombres de los artículos
     */
    public String[] getPapersNamesByIndexes (ArrayList<Integer> indexes) throws FileNotFoundException {
        LinkedList<String> allNames = getPapersNames(); // Obtenemos los nombres de todas las pruebas disponibles
        LinkedList<String> names = new LinkedList<>();  // Array de strings donde se guardaran los nombres que necesitemos

        for (int i = 0; i < indexes.size(); i++) {
            names.add(allNames.get(indexes.get(i))) ;
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
     * @return Booleano que permite saber si el artículo se ha eliminado correctamente
     */
    public boolean deletePaper (int index) throws IOException {
        return paperDAO.delete(index);
    }

    public PaperPublication getPaper (int index) throws FileNotFoundException {
        return paperDAO.readAll().get(index);
    }

    public void setInUseByIndexes (ArrayList<Integer> indexes) throws IOException {
        int j = 0;

        for(int i = 0; i < getPapers().size() && j < indexes.size(); i++) {
            if (i == indexes.get(j)) {
                // Creamos un nuevo artículo con los mismos datos, pero con el "inUse" a true
                PaperPublication updatedArticle = new PaperPublication(getPaper(i).getArticleName(),
                        getPaper(i).getMagazineName(), getPaper(i).getQuartile(),
                        getPaper(i).getAcceptedProbability(), getPaper(i).getRevisedProbability(),
                        getPaper(i).getRejectedProbability(), true);
                paperDAO.changeLine(i, updatedArticle);
                j++;
            }
        }
    }

    public void setInUnusedByIndex (int index) throws IOException {
        PaperPublication article = new PaperPublication(getPaper(index).getArticleName(),
                getPaper(index).getMagazineName(), getPaper(index).getQuartile(),
                getPaper(index).getAcceptedProbability(), getPaper(index).getRevisedProbability(),
                getPaper(index).getRejectedProbability(), false);
        paperDAO.changeLine(index, article);
    }

    public boolean checkExistence (String name) throws FileNotFoundException {
        return getPapersNames().contains(name);
    }

    public boolean isInUse (String name) throws FileNotFoundException {
        return getPaperByName(name).isInUse();
    }

    public void setInUseByName(String name) throws IOException {
        int index = getIndexByName(name);
        PaperPublication auxPaper = getPaperByName(name);
        PaperPublication paperPublication = new PaperPublication(auxPaper.getArticleName(), auxPaper.getMagazineName(),
                auxPaper.getQuartile(), auxPaper.getAcceptedProbability(),
                auxPaper.getRevisedProbability(), auxPaper.getRejectedProbability());
        paperDAO.changeLine(index, paperPublication);
    }
}