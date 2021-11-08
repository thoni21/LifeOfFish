package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    private int unlockNextLevel;
    private Grid grid;
    static int roomCount = 0;

    public Room(String description, int unlockNextLevel) {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.unlockNextLevel = unlockNextLevel;

        //The following code describes the difficulty and size of the individual levels.
        //The first two integers determine the grid size.
        //The third integer determines the quantity of enemies
        //The fourth integer determines the quantity of food
        //The fifth integer determines the quantity of obstacles
        int[][] difficulty = {{5,5,4,2,3},{7,7,8,5,6},{9,9,10,5,16},{9,9,12,5,20},{9,9,14,5,22},{11,11,16,9,35}};
        grid = new Grid(difficulty[roomCount][0],difficulty[roomCount][1],difficulty[roomCount][2],
                difficulty[roomCount][3],difficulty[roomCount][4]);
        roomCount ++;
    }

    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public Grid getMap() {
        return grid;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n";
    }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    public int getUnlockNextLevel() {
        return unlockNextLevel;
    }
}

