package worldofzuul;

public abstract class GameObjects {
    String name;
    int turnValue;
    String symbol;
    double pollutionValue;

    public GameObjects(String name, int turnValue, String symbol){
        this.name = name;
        this.turnValue = turnValue;
        this.symbol = symbol;
        this.pollutionValue = pollutionValue;
    }

    public String getName(){
        return name;
    }

    public int getTurnValue() {
        return turnValue;
    }

    public double getPollutionValue() {
        return pollutionValue;
    }

    public void addPollutionValue(double pollutionValue) {
        this.pollutionValue += pollutionValue;
    }

    public String getSymbol(){return symbol;}

}
