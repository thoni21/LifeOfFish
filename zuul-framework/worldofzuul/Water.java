package worldofzuul;

public class Water extends GameObjects{
    String name;
    String symbol;
    public Water(){
        this.name = "water";
        this.symbol = "≋";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSymbol(){return symbol;}

    @Override
    public String toString(){
        return "this is empty Water";
    }
}
