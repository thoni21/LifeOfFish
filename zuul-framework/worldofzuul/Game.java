package worldofzuul;

import java.util.ArrayList;

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

        level1 = new Room("at the first level",60);
        level2 = new Room("now at level 2",140);
        level3 = new Room("now at level 3",240);
        level4 = new Room("now at level 4",340);
        level5 = new Room("now at level 5",440);
        level6 = new Room("now at the final level",580);

        level1.setExit("next", level2);
        level2.setExit("next", level3);
        level3.setExit("next", level4);
        level4.setExit("next", level5);
        level5.setExit("next", level6);

        currentRoom = level1;
    }


    public void play() {
        printWelcome();
        currentRoom.getMap().printGrid();
        currentRoom.getMap().findPlayer().calculateScore();
        System.out.println("Score: "+currentRoom.getMap().findPlayer().getScore()+
                "    Energy: "+currentRoom.getMap().findPlayer().getTurns()+
                "    Turns used: "+currentRoom.getMap().findPlayer().getTotalTurns());

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command) || !currentRoom.getMap().findPlayer().status();

            if(currentRoom.getMap().findPlayer().getTurns() == 0){
                finished = true;
            }
            if(!finished){
                currentRoom.getMap().printGrid();
                System.out.println("Score: "+currentRoom.getMap().findPlayer().getScore()+
                        "    Energy: "+currentRoom.getMap().findPlayer().getTurns()+
                        "    Turns used: "+currentRoom.getMap().findPlayer().getTotalTurns());
                if(currentRoom.getMap().findPlayer().getScore() >= currentRoom.getUnlockNextLevel()){
                    System.out.println("You have reached a high enough score to go to the next level!");
                    System.out.println("Type 'go next' to go to the next level! \nHowever you don't have to.");
                }
            }
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

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO && command.getSecondWord().equals("next")) {
            if(currentRoom.getMap().findPlayer().getScore() >= currentRoom.getUnlockNextLevel()){
                goRoom(command);
            } else{
                System.out.println("You cannot go to the next level right now, try getting a some more score.");
            }
        }else if(commandWord == commandWord.GO){
            goInGrid(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are a fish swimming at sea.");
        System.out.println("You must move around to gain score and consume food as to not lose energy.");
        System.out.println("Avoid obstacles to not lose score.");
        System.out.println("Avoid enemies at all costs!");
        System.out.println("\uD83D\uDC1F = You!                     \uD83E\uDD80 = Food, yum!");
        System.out.println("\uD83D\uDC19 = Enemy, steer clear!      \uD83D\uDDD1 = Obstacle, annoying!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goInGrid(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        try {
            currentRoom.getMap().gridMovement(currentRoom.getMap().findPlayer(), command);
        } catch (IllegalMoveException ex) {
            System.out.println(ex);
        }
    }
    private void goRoom(Command command) {

        Grid placeholder = currentRoom.getMap();

        String direction = command.getSecondWord();

        currentRoom = currentRoom.getExit(direction);
        System.out.println(currentRoom.getLongDescription());

        currentRoom.getMap().movePlayerToNextLevel(placeholder);
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
