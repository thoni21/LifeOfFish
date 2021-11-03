package worldofzuul;

public class Water extends GameObjects{
    String name;
    public Water(){
        this.name = "water";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "this is empty Water";
    }
}
