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

    public Game() {
        createRooms();
        parser = new Parser();
    }

    public static void main(String[] args){
        Game myGame = new Game ();
        myGame.play();

    }

    public void createRooms() {
        Room level1, level2, level3, level4, level5, level6;

        level1 = new Room("at the first level");
        level2 = new Room("now at level 2");
        level3 = new Room("now at level level 3");
        level4 = new Room("now at level level 4");
        level5 = new Room("now at level level 5");
        level6 = new Room("now at the final level");

        level1.getGridMap().printGrid();


        level1.setExit("next", level2);
        level2.setExit("next", level3);
        level3.setExit("next", level4);
        level4.setExit("next", level5);
        level5.setExit("next", level6);

        currentRoom = level1;
    }


    public void play() {
        printWelcome();
        System.out.println("This is your score: " +currentRoom.getGridMap().findPlayer().getScore());
        System.out.println("you have swum a total of: " +currentRoom.getGridMap().findPlayer().getTotalTurns());

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command, currentRoom.getGridMap());
            currentRoom.getGridMap().printGrid();
            if((currentRoom.getGridMap().findPlayer().getTotalTurns() >= 5)){
                System.out.println("You are now able to move on");
            }
            System.out.println("This is your score: " +currentRoom.getGridMap().findPlayer().getScore());
            System.out.println("you have swum a total of: " +currentRoom.getGridMap().findPlayer().getTotalTurns());
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Life of Fish!");
        System.out.println("You are a fish, trying to survive at sea.");
        System.out.println("Your goal is to survive without becoming polluted.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command, Grid grid) {
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
            if(currentRoom.getGridMap().findPlayer().getTotalTurns() >= 5){
                if(command.getSecondWord().equals("next")){
                    goRoom(command);
            }
            } else {
                goInGrid(command, grid);
            }
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are a fish swimming at sea.");
        System.out.println("\uD83D\uDC1F = You!");
        System.out.println("\uD83E\uDD80 = Food, yum!");
        System.out.println("☠ = Enemy, steer clear!");
        System.out.println("# = Obstacle, annoying!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goInGrid(Command command, Grid grid) {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        try {
            grid.gridMovement(grid.findPlayer(),command);
        } catch (IllegalMoveException ex) {
            System.out.println(ex);;
        }

    }
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        GameObjects[][] placeholder = currentRoom.getGridMap().cloneGrid();

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }

        currentRoom.getGridMap().movePlayerToNextLevel(placeholder);
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
