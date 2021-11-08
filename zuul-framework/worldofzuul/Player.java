package worldofzuul;

public class Player extends GameObjects{
    private double score;
    private int speed;
    private boolean alive;
    private int totalTurns;

    public Player(String name, int turnValue, int speed, double pollutionValue, String symbol){
        super(name,turnValue,symbol,pollutionValue);
        this.speed = speed;
        this.alive = true;
        this.totalTurns = 0;
        this.score = 0;
    }

    public int getTurns(){
        return this.turnValue;
    }

    public double getScore(){
        return this.score;
    }

    public void addTurns(int gain){
        this.turnValue += gain;
    }

    public void removeTurns(int loss){
        this.turnValue -= loss;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public void addTotalTurns(int i) {
        this.totalTurns += i;
    }
    public void calculateScore(){
        this.score = totalTurns * 10 - pollutionValue;
    }

    public void triggerDeath(){
        this.alive = false;
    }
    public boolean status(){
        boolean placeholder = this.alive;
        return placeholder;
    }

    @Override
    public String toString(){
        return "Name: " + getName() + "    Current score: " + getScore() +
                "    Current turn: " + getTurns() + "    Your current pollution level: "
                + getPollutionValue();
    }

}
