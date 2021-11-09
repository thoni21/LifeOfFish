package worldofzuul;

public class Player extends GameObjects{

    //Attributes
    private double score;
    private int speed;
    private boolean alive;
    private int totalTurns;

    //Constructor
    public Player(String name, int turnValue, int speed, double pollutionValue, String symbol){
        super(name,turnValue,symbol,pollutionValue);
        this.speed = speed;
        this.alive = true;
        this.totalTurns = 0;
        this.score = 0;
    }


    //Methods
    public double getScore(){
        return this.score;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    public void addTotalTurns(int i) {
        this.totalTurns += i;
    }
    public void calculateScore(){
        this.score = totalTurns * 10 - getPollutionValue();
    }

    public void triggerDeath(){
        this.alive = false;
    }

    //Checks if player is alive
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
