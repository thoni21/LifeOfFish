package worldofzuul;

public class Player extends GameObjects{
    double score;
    int speed;
    double pollutionLevel;

    public Player(String name, double score, int turnValue, int speed, double pollutionLevel, String symbol){
        super(name,turnValue,symbol);
        this.speed = speed;
        this.score = score;
        this.pollutionLevel = score;
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

    public double getPollutionLevel() {
        return pollutionLevel;
    }

    public void movement(){

    }

    public void death(){

    }

    @Override
    public String toString(){
        return "Name: " + getName() + "    Current score: " + getScore() +
                "    Current turn: " + getTurns() + "    Your current pollution level: "
                + getPollutionLevel();
    }

}
