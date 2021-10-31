package worldofzuul;

public abstract class Items {
    String name;
    int turnValue;
    double pollutionValue;
    ItemType type;

    public Items(String name, int turnValue, double pollutionValue, ItemType type){
        this.name = name;
        this.turnValue = turnValue;
        this.pollutionValue = pollutionValue;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getTurnValue() {
        return turnValue;
    }

    public double getPollutionValue() {
        return pollutionValue;
    }
}
