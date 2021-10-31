package worldofzuul;

public class Player {
    double score;
    int turns;
    int speed;
    double pollutionLevel;

    public Player(double score, int turns, int speed, double pollutionLevel){

    }

    public int getTurns(){
        return this.turns;
    }

    public double getScore(){
        return this.score;
    }

    public void addTurns(int gain){
        this.turns += gain;
    }

    public void removeTurns(int loss){
        this.turns -= loss;
    }

    public double getPollutionLevel() {
        return pollutionLevel;
    }

    public void movement(){

    }

    public void death(){

    }
}
