package business.typeTrials;

public class MasterStudies {
    private String name;
    private String nom;
    private int numberCredits;
    private int probability;

    public MasterStudies(String name, String nom, int numberCredits, int probability) {
        this.name = name;
        this.nom = nom;
        this.numberCredits = numberCredits;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public String getNom() {
        return nom;
    }

    public int getNumberCredits() {
        return numberCredits;
    }

    public int getProbability() {
        return probability;
    }
}
