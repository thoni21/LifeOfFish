package worldofzuul;

public class Enemies extends GameObjects{
    int speed;

    public Enemies(String name,int turnValue, int speed){
        super(name, turnValue);
        this.speed = speed;
    }

    public String getName(){
        return this.name;
    }

    public void movement(){

    }
}
