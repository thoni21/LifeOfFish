package worldofzuul;

import java.io.OptionalDataException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Game
{
    private Parser parser;
    private Room currentRoom;

    public Game() 
    {
        createGrid(5,5);
        createRooms();
        parser = new Parser();
    }

    public static void main(String[] args){
        Game myGame = new Game ();
        myGame.play();
    }

    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;

        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;
    }

    private void createGrid(int l, int b){

        //a list of the quantity of different entities
        int playerQuantity = 1;
        int enemiesQuantity = 1;
        int foodQuantity = 3;
        int obstaclesQuantity = 2;
        int totalQuantity = playerQuantity+enemiesQuantity+
                foodQuantity+obstaclesQuantity;


        //a number that shows where en the Array there's room
        int placeCounter = 0;

        //Array where all entities are going in
        GameObjects[] objectList = new GameObjects[totalQuantity];

        //creating the player
        objectList[placeCounter] = new Player("Tuna",0.0,20,1,0.0,"\uD83D\uDC1F");
        placeCounter++;


        //loop that creates enemies and places them in the array
        for(int i = 0; i < enemiesQuantity; i++){
            objectList[placeCounter] = new Enemies("Shark",-100,1,"\uD83D\uDC0A");
            placeCounter++;
        }
        //loop that creates food and places them in the array
        for(int i = 0; i < foodQuantity; i++){
            objectList[placeCounter] = new Food("Crab",placeCounter,3.2,"\uD83E\uDD80");
            placeCounter++;
        }
        //loop that creates obstacles and places them in the array
        for(int i = 0; i < obstaclesQuantity; i++){
            objectList[placeCounter] = new Obstacles("Hard plastic",-i,i+2,"O");
            placeCounter++;
        }

        //creates the 2D grid
        GameObjects[][] grid = new GameObjects[l][b];

        //creates a 1D grad copy with a ArrayList
        ArrayList<GameObjects>  gridCopy = new ArrayList<>();

        //places all entities in the gridCopy
        for(int i = 0; i < l*b; i++){
            try{
                gridCopy.add(objectList[i]);
            }catch (ArrayIndexOutOfBoundsException ex){
                gridCopy.add(new Water());
            }
        }
        //shuffles all entities in the ArrayList
        Collections.shuffle(gridCopy);

        //marge 1D grid with 2D grid
        int count = 0;
        for(int i = 0; i < l; i++){
            for(int j = 0; j < b; j++){
             grid[j][i] = gridCopy.get(count);
             count++;
            }
        }

        //prints its out
        for (int y=0 ; y<grid.length ; y++) {
            for (int x=0 ; x< grid[y].length ; x++) {
                System.out.print(" "+grid[x][y].getSymbol()+" ");
            }
            System.out.println("");
        }

    }

    public void play() 
    {            
        printWelcome();

                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

}
