package business.playerTypes;

/**
 * Define un tipo de jugador Doctor, que hereda del genérico Player
 * @author Abraham Cedeño
 * @author José Pérez
 */
public class Doctor extends Player{
    private String name;
    private int PI;

    /**
     * Constructor que crea un nuevo Doctor
     * @param name nombre del Doctor
     * @param PI PI del doctor
     */
    public Doctor (String name, int PI) {
        this.name = name;
        this.PI = PI;
    }

    /**
     * Incrementa el PI de un Doctor, conociendo sus particularidades
     * @param change cantidad en la que debe cambiar el PI (en genérico)
     */
    @Override
    public void incrementPI (int change) {
        PI = PI + change*2;
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
     * Obtiene el nombre del jugador
     * @return nombre del jugador
     */
    @Override
    public String getName () {
        return name;
    }

    /**
     * Obtiene el PI del jugador
     * @return PI del jugador
     */
    @Override
    public int getPI () {
        return PI;
    }
}
