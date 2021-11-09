package worldofzuul;

public class Obstacles extends GameObjects{

    //Constructor
    public Obstacles(String name, int turnValue, double pollutionValue, String symbol) {
        super(name, turnValue, symbol, pollutionValue);
    }

    //Methods
    @Override
    public String toString(){
        return "Obstacle: " + getName() + "    "+getName()+"'s pollution level is:" + getPollutionValue() + "    Eating this will make you gain " + getTurnValue() + " turns.";
    }
}
