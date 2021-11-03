package worldofzuul;

public abstract class GameObjects {
    String name;
    int turnValue;

    public GameObjects(String name, int turnValue){
        this.name = name;
        this.turnValue = turnValue;
    }
    public GameObjects(){

    }

    public String getName(){
        return name;
    }
}
