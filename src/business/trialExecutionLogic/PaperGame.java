package business.trialExecutionLogic;

import business.playerTypes.Player;
import business.typeTrials.PaperPublication;
import presentation.ViewController;

import java.util.Random;

/**
 * Controla la lógica al ejecutar una prueba (cómo los jugadores publican articulos y cómo varía su puntuación)
 * @author Abraham Cedeño
 * @author José Pérez
 */

public class PaperGame {
    /**
     * Método que llama a los métodos que obtienen qué se hace con el artículo enviado y que calcula la puntuación
     * final del jugador
     * @param article Artículo que se está ejecutando (prueba)
     * @param player Jugador que está pasando la prueba
     * @return Nuevo jugador, con la PI actualizada según su desempeño
     */
    public Player publishArticle (PaperPublication article, Player player) {

        // Calculamos de forma aleatoria si se acepta, revisa o rechaza
        int response;
        do {
            response = calculateResponse(article);
        }while (response != 1 && response != 3);
        // Aumentamos, mantenemos o reducimos puntuación segon el cuartil
        manageScore(response, article, player);

        return player;
    }

    /**
     * Método que nos dirá de forma aleatoria si el artículo se acepta, revisa o rechaza
     * @param article Artículo que se está presentando
     * @return Entero que puede valer: 1 (si se acepta el artículo), 2 (si se revisa el artículo), 3 (si se rechaza el artículo)
     */
    private int calculateResponse (PaperPublication article) {
        ViewController view = null;
        // Generamos un número aleatorio que estará entre 0 y 100
        Random rand = new Random();
        int response = 0;
        int randomNumber = rand.nextInt(101);

        //Creamos los rangos de aceptado, revisado y rechazado
        int acceptedRange = article.getAcceptedProbability();
        int revisedRange = acceptedRange + article.getRevisedProbability();
        int rejectedRange = revisedRange + article.getRejectedProbability();

        if (randomNumber  <= acceptedRange) {
            view.showMessageLine("Accepted!");
            response = 1;
        }else if(randomNumber < revisedRange) {
            view.showMessageLine("Revisions...");
            response = 2;
        }else if (randomNumber < rejectedRange){
            view.showMessageLine("Rejected.");
            response = 3;
        }
        return response;
    }

    /**
     * Método que, según si se ha aceptado o rechazado el artíuclo, modifica los puntos del jugador, dependiendo del
     * Quartil de la prueba ejecutada. En caso de haber sido revisado (2), la puntuación del jugador se mantiene
     * @param response Indica si el artículo ha sido aceptado (1), revisado (2) o rechazado (3)
     * @param article Artículo que se está presentando (prueba)
     * @param player Jugador que está pasando la prueba
     */
    private void manageScore (int response, PaperPublication article, Player player) {
        String quartile = article.getQuartile();

        if (response == 1) {
            switch (quartile) {
                case "Q1" -> player.incrementPI(4);
                case "Q2" -> player.incrementPI(3);
                case "Q3" -> player.incrementPI(2);
                case "Q4" -> player.incrementPI(1);
            }
        }
        if (response == 3) {
            switch (quartile) {
                case "Q1" -> player.decrementPI(5);
                case "Q2" -> player.decrementPI(4);
                case "Q3" -> player.decrementPI(3);
                case "Q4" -> player.decrementPI(2);
            }
        }
    }

    public boolean checkUpdateStatus (Player player) {
        return player.checkUpdateStatus();
    }


}
