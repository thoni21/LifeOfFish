package worldofzuul;

public abstract class GameObjects {

    //Attributes
    private String name;
    private int turnValue;
    private String symbol;
    private double pollutionValue;

    //Constructor
    public GameObjects(String name, int turnValue, String symbol, double pollutionValue){
        this.name = name;
        this.turnValue = turnValue;
        this.symbol = symbol;
        this.pollutionValue = pollutionValue;
    }

    //Methods
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

    public void addTurns(int gain){
        this.turnValue += gain;
    }

    public void removeTurns(int loss){
        this.turnValue -= loss;
    }

    public int getTurns(){
        return this.turnValue;
    }

    public String getSymbol(){return symbol;}

}
