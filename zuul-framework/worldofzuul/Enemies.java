package worldofzuul;

public class Enemies extends GameObjects{
    int speed;

    public Enemies(String name,int turnValue, int speed,String symbol){
        super(name, turnValue, symbol);
        this.speed = speed;
    }

    public void movement(){

    }

    @Override
    public String toString(){
        return "Enemy: " + getName() + "    You must avoid " + getName() + " at all costs!";
    }
}
