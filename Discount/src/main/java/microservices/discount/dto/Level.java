package microservices.discount.dto;

public enum Level {
    BRONZE(5,0),
    SILVER(10,20),
    GOLD(15,50);

    private int percentage;
    private int washGroomCutGoal;

    Level(int percentage, int washGroomCutGoal) {
        this.percentage = percentage;
        this.washGroomCutGoal=washGroomCutGoal;
    }

    public int getPercentage() {
        return percentage;
    }
    public int getGoal() {
        return washGroomCutGoal;
    }
}
