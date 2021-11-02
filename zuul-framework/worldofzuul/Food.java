package worldofzuul;

public class Food extends Items{

    public Food(String name, int turnValue, double pollutionValue) {
        super(name, turnValue, pollutionValue, ItemType.FOOD);
    }
}
