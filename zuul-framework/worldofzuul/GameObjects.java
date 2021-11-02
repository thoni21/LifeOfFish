package worldofzuul;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class GameObjects {
    String name;
    int turnValue;
    ArrayList<Integer> position = new ArrayList<>();

    public GameObjects(String name, int turnValue){
        this.name = name;
        this.turnValue = turnValue;
        int posX = ThreadLocalRandom.current().nextInt(1, Game.getSizeXValue());
        int posY = ThreadLocalRandom.current().nextInt(1, Game.getSizeYValue());
        this.position.add(posX);
        this.position.add(posY);
    }

    public String getName(){
        return name;
    }
}
