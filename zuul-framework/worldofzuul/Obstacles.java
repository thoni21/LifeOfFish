package worldofzuul;

public class Obstacles extends GameObjects{

    //Constructor
    public Obstacles() {
        super("plastic bottle", -5, "\uD83D\uDDD1", 20);
    }

    //Methods
    @Override
    public String toString(){
        return "Obstacle: " + getName() + "    "+getName()+"'s pollution level is:" + getPollutionValue() + "    Eating this will make you gain " + getTurnValue() + " turns.";
    }
}
