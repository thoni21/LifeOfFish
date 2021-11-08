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

        int[][] difficulty = {{7,7,1,1,1},{9,9,1,6,1},{9,9,1,1,1},{9,9,1,1,1},{9,9,1,1,1},{9,9,1,1,1}};
        grid = new Grid(difficulty[roomCount][0],difficulty[roomCount][1],difficulty[roomCount][2],
                difficulty[roomCount][3],difficulty[roomCount][4]);
        roomCount ++;
    }

    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
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

