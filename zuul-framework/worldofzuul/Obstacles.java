package worldofzuul;

public class Obstacles extends GameObjects{

    public Obstacles(String name, int turnValue, double pollutionValue, String symbol) {
        super(name, turnValue, symbol, pollutionValue);
    }

    @Override
    public String toString(){
        return "Obstacle: " + getName() + "    "+getName()+"'s pollution level is:" + getPollutionValue() + "    Eating this will make you gain " + getTurnValue() + " turns.";
    }
}
