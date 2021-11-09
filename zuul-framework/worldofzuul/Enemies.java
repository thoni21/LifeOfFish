package worldofzuul;

public class Enemies extends GameObjects{

    //Attributes
    private int speed;

    //Constructor
    public Enemies(String name, int turnValue, int speed, String symbol){
        super(name, turnValue, symbol, 0);
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
