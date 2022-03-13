package business.playerTypes;

/**
 * Define un tipo de jugador Engineer, que hereda del genérico Player
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class Engineer extends Player{
    private String name;
    private int PI;

    /**
     * Constructor que crea un nuevo Engineer
     * @param name nombre del Enginner
     * @param PI PI del ingeniero
     */
    public Engineer (String name, int PI){
        this.name = name;
        this.PI = PI;
    }

    /**
     * Incrementa el PI de un ingeniero, conociendo sus particularidades
     * @param change cantidad en la que debe cambiar el PI (en genérico)
     */
    @Override
    public void incrementPI (int change) {
        this.PI = this.PI + change;
    }

    /**
     * Decrementa el PI de un Ingeniero
     * @param change cantidad de puntos a retirar
     */
    @Override
    public void decrementPI (int change) {
        if (this.PI - change >= 0) {
            this.PI = this.PI - change;
        }else{
            PI = 0;
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
     * Coloca el PI del jugador en un valor específico
     * @param PI PI al que se debe colocar
     */
    @Override
    public void setPi (int PI) {
        this.PI = PI;
    }

    /**
     * Obtiene el nombre del jugador
     * @return nombre del jugador
     */
    @Override
    public String getName () {
        return name;
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
}
