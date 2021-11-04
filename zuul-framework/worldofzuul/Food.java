package worldofzuul;

public class Food extends Items{

    public Food(String name, int turnValue, double pollutionValue, String symbol) {
        super(name, turnValue, pollutionValue, ItemType.FOOD,symbol);
    }

    @Override
    public String toString(){
        return "Food: " + getName() + "    "+getName()+"'s pollution level is: " + getPollutionValue() + "    Consuming this will make you lose " + getTurnValue() + " turns.";
    }
}
