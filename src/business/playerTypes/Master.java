package business.playerTypes;

/**
 * Define un tipo de jugador Master, que hereda de Engineer
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class Master extends Player {
    private String name;
    private int PI;

    /**
     * Constructor que crea un nuevo Master
     * @param name nombre del Master
     * @param PI PI del master
     */
    public Master (String name, int PI) {
        this.name = name;
        this.PI = PI;
    }

    /**
     * Incrementa el PI de un Master, conociendo sus particularidades
     * @param change cantidad en la que debe cambiar el PI (en genérico)
     */
    @Override
    public void incrementPI (int change) {
        PI = PI + change;
    }

    /**
     * Decrementa el PI de un Master
     * @param change cantidad de puntos a retirar
     */
    @Override
    public void decrementPI (int change) {
        if (PI - change/2 >= 0) {
            PI = PI - change/2;
        }else{
            PI = 0;
        }
    }

    /**
     * Comprueba, según su PI, si le toca evolucionar al siguiente tipo de jugador
     * @return booleano que indica si debe evolucionar o no
     */
    @Override
    public boolean checkUpdateStatus() {
        if ( PI >= 10) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * Obtiene el PI del jugador
     * @return PI del jugador
     */
    @Override
    public int getPI () {
        return PI;
    }

    /**
     * Obtiene el nombre del jugador
     * @return nombre del jugador
     */
    @Override
    public String getName () {
        return name;
    }
}
