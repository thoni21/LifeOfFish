package worldofzuul;

public class Food extends GameObjects{

    public Food(String name, int turnValue, double pollutionValue, String symbol) {
        super(name, turnValue, symbol);
    }

    @Override
    public String toString(){
        return "Food: " + getName() + "    "+getName()+"'s pollution level is: " + getPollutionValue() +
                "    Consuming this will make you lose " + getTurnValue() + " turns.";
    }
}
