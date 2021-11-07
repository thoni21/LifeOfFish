package worldofzuul;

public class Player extends GameObjects{
    double score;
    int speed;
    boolean alive;

    public Player(String name, double score, int turnValue, int speed, double pollutionLevel, String symbol){
        super(name,turnValue,symbol,pollutionLevel);
        this.speed = speed;
        this.score = score;
        this.alive = true;
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


    public void triggerDeath(){
        this.alive = false;
    }

    @Override
    public String toString(){
        return "Name: " + getName() + "    Current score: " + getScore() +
                "    Current turn: " + getTurns() + "    Your current pollution level: "
                + getPollutionValue();
    }

}
