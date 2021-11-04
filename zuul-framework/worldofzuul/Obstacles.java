package worldofzuul;

public class Obstacles extends Items{

    public Obstacles(String name, int turnValue, double pollutionValue, String symbol) {
        super(name, turnValue, pollutionValue, ItemType.OBSTACLE,symbol);
    }

    @Override
    public String toString(){
        return "Obstacle: " + getName() + "    "+getName()+"'s pollution level is:" + getPollutionValue() + "    Eating this will make you gain " + getTurnValue() + " turns.";
    }
}
