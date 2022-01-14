package business.typeTrials;

public class DoctoralThesis {
    private String name;
    private String fieldOfStudy;
    private int difficulty;

    public DoctoralThesis(String name, String fieldOfStudy, int difficulty) {
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public int getDifficulty() {
        return difficulty;
    }
}