package worldofzuul;

public class Enemies extends GameObjects{

    //Attributes
    private int speed;

    //Constructor
    public Enemies(){
        super("Octopus", -1000,"\uD83D\uDC19", 0);
        this.speed = speed;
    }

    //Methods
    public void movement(){

    }

    @Override
    public String toString(){
        return "Enemy: " + getName() + "    You must avoid " + getName() + " at all costs!";
    }
}
