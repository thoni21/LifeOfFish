package worldofzuul;

public abstract class GameObjects {
    String name;
    int turnValue;
    String symbol;

    public GameObjects(String name, int turnValue, String symbol){
        this.name = name;
        this.turnValue = turnValue;
        this.symbol = symbol;
    }
    public GameObjects(){
    }

    public String getName(){
        return name;
    }

    public String getSymbol(){return symbol;}
}
