package worldofzuul;

import java.util.ArrayList;

public class Game
{
    //Attributes
    private Parser parser;
    private Room currentRoom;

    //Constructor
    public Game() {
        createRooms();
        parser = new Parser();
    }

    //Main method
    public static void main(String[] args){
        Game myGame = new Game ();
        myGame.play();

    }

    //Method to create rooms, and set keyword for next room
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

    //Play method, flow of the game
    public void play() {

        //Uses printWelcome method
        printWelcome();

        //Boolean finished set to false, game runs while finished is not true
        boolean finished = false;
        while (! finished) {

            //Asks for command
            Command command = parser.getCommand();

            //Checks if command = finished or player is dead
            finished = processCommand(command) || !currentRoom.getMap().findPlayer().status();
            if(currentRoom.getMap().findPlayer().getTurns() == 0){
                finished = true;
            }

            //Runs game, if finished is false
            if(!finished){
                currentRoom.getMap().printGrid();
                System.out.println("Score: "+currentRoom.getMap().findPlayer().getScore()+
                        "    Energy: "+currentRoom.getMap().findPlayer().getTurns()+
                        "    Turns used: "+currentRoom.getMap().findPlayer().getTotalTurns());

                //Checks if score is larger than or equal to scoreToNextLevel
                if(currentRoom.getMap().findPlayer().getScore() >= currentRoom.scoreToNextLevel()){
                    System.out.println("You have reached a high enough score to go to the next level!");
                    System.out.println("Type 'go next' to go to the next level! \nHowever you don't have to.");
                }
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    //Method to print the welcome message to the player
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Life of Fish!");
        System.out.println("You are a fish, trying to survive at sea.");
        System.out.println("Your goal is to survive without becoming polluted.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());

        currentRoom.getMap().printGrid();
        currentRoom.getMap().findPlayer().calculateScore();
        System.out.println("Score: "+currentRoom.getMap().findPlayer().getScore()+
                "    Energy: "+currentRoom.getMap().findPlayer().getTurns()+
                "    Turns used: "+currentRoom.getMap().findPlayer().getTotalTurns());

    }

    //Method to process commands
    //Checks what commandword has been inserted, and acts on that
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
            if(currentRoom.getMap().findPlayer().getScore() >= currentRoom.scoreToNextLevel()){
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

    //Guide for player
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

    //Movement for player
    private void goInGrid(Command command) {

        //Checks if player forgot to write second word wth go command
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        //Checks weather or not the player has made an illegal move
        try {
            currentRoom.getMap().gridMovement(currentRoom.getMap().findPlayer(), command);
        } catch (IllegalMoveException ex) {
            System.out.println(ex);
        }
    }

    //Method to change room
    private void goRoom(Command command) {

        //Makes placeholder of current grid
        Grid placeholder = currentRoom.getMap();

        String direction = command.getSecondWord();

        //Switches room
        currentRoom = currentRoom.getExit(direction);
        System.out.println(currentRoom.getLongDescription());

        //Makes player from placholder grid, equal to player from new grid
        currentRoom.getMap().movePlayerToNextLevel(placeholder);
    }


    //Sets quit to true, unless player wrote secondword with quit
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
