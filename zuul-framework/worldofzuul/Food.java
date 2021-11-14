package worldofzuul;

public class Food extends GameObjects{

    //Constructor
    public Food() {
        super("Shrimp", 6, "\uD83E\uDD90", 3);
    }

    //Methods
    @Override
    public String toString(){
        return "Food: " + getName() + "    "+getName()+"'s pollution level is: " + getPollutionValue() +
                "    Consuming this will make you lose " + getTurnValue() + " turns.";
    }
}
