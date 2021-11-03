package worldofzuul;

public class Enemies extends GameObjects{
    int speed;

    public Enemies(String name,int turnValue, int speed){
        super(name, turnValue);
        this.speed = speed;
    }

    public void movement(){

    }

    @Override
    public String toString(){
        return "Enemy: " + getName() + "    You must avoid " + getName() + " at all costs!";
    }
}
