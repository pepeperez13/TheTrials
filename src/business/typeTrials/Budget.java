package business.typeTrials;

public class Budget {
    private String nameTrial;
    private String nameEntity;
    private int amount;

    public Budget(String nameTrial, String nameEntity, int amount) {
        this.nameTrial = nameTrial;
        this.nameEntity = nameEntity;
        this.amount = amount;
    }

    public String getNameTrial() {
        return nameTrial;
    }

    public String getNameEntity() {
        return nameEntity;
    }

    public int getAmount() {
        return amount;
    }
}
