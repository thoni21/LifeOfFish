package worldofzuul;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    //Attributes
    private Parser parser;
    private Room currentRoom;

    //Constructor
    public Game() {
        createRooms();
        parser = new Parser();
    }

    //Main method
    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.play();

    }

    //Method to create rooms, and set keyword for next room
    public void createRooms() {
        Room level1, level2, level3, level4, level5, level6;

        level1 = new Room("at the first level", 60);
        level2 = new Room("now at level 2", 140);
        level3 = new Room("now at level 3", 240);
        level4 = new Room("now at level 4", 340);
        level5 = new Room("now at level 5", 440);
        level6 = new Room("now at the final level", 580);

        level1.setExit("", level2);
        level2.setExit("", level3);
        level3.setExit("", level4);
        level4.setExit("", level5);
        level5.setExit("", level6);

        currentRoom = level1;
    }

    //Play method, flow of the game
    public void play() {

        //Uses printWelcome method
        printWelcome();

        //Boolean finished set to false, game runs while finished is not true
        boolean finished = false;
        while (!finished) {

            //Asks the player for a command
            Command command = parser.getCommand();

            //runs the players command
            finished = processCommand(command);

            //the enemies move if the player does not intend to go to the next level
            CommandWord commandWord = command.getCommandWord();
            if (command.getCommandWord() != commandWord.NEXT) {
                enemyTurn();
            }

            //checks if the player died that turn
            if (currentRoom.getMap().findPlayer().getTurns() <= 0 || !currentRoom.getMap().findPlayer().status()) {
                finished = true;
            } else { //if the player did not die, then it updates and prints the map and stats for next turn
                currentRoom.getMap().maintenance();
                currentRoom.getMap().printGrid();
                System.out.println("Score: " + currentRoom.getMap().findPlayer().getScore() +
                        "    Energy: " + currentRoom.getMap().findPlayer().getTurns() +
                        "    Turns used: " + currentRoom.getMap().findPlayer().getTotalTurns());

                //Checks if score is larger than or equal to scoreToNextLevel
                if (currentRoom.getMap().findPlayer().getScore() >= currentRoom.scoreToNextLevel()) {
                    System.out.println("You have reached a high enough score to go to the next level!");
                    System.out.println("Type 'next' to go to the next level! \nHowever you don't have to.");
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
        System.out.println("Score: " + currentRoom.getMap().findPlayer().getScore() +
                "    Energy: " + currentRoom.getMap().findPlayer().getTurns() +
                "    Turns used: " + currentRoom.getMap().findPlayer().getTotalTurns());

    }

    //Method to process commands
    //Checks what commandword has been inserted, and acts on that
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == commandWord.GO) {
            goInGrid(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == commandWord.NEXT) {
            if (currentRoom.getMap().findPlayer().getScore() >= currentRoom.scoreToNextLevel()) {
                goRoom(command);
            } else {
                System.out.println("You cannot go to the next level right now, try getting a some more score.");
            }
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
        if (!command.hasSecondWord()) {
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

        //Switches room
        currentRoom = currentRoom.getExit("");
        System.out.println(currentRoom.getLongDescription());

        //Makes player from placholder grid, equal to player from new grid
        currentRoom.getMap().movePlayerToNextLevel(placeholder);
    }


    //Sets quit to true, unless player wrote secondword with quit
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    //method that makes all enemies move
    public void enemyTurn() {
        //finds all enemies
        ArrayList<Enemies> enemies = currentRoom.getMap().getAllEnemies();
        int tries = 0;

        //loops though the list making each move
        for (int i = 0; i < enemies.size(); ) {
            try {//tries moving
                currentRoom.getMap().gridMovement(enemies.get(i), enemyAI(enemies.get(i)));
                i++;
            } catch (IllegalMoveException ex) {// if that fails it tries again but only limited times
                tries++;
            } finally { //controls if the one enemy has tried to many times
                if (tries > 9) {
                    i++;
                    tries = 0;
                }
            }
        }
    }

    private Command enemyAI(Enemies enemy) {
        Random choiceMaker = new Random();
        //finds the players position
        ArrayList<Integer> playerPosition = currentRoom.getMap().getPosition(currentRoom.getMap().findPlayer());
        //used to create a command
        CommandWords commands = new CommandWords();
        Command enemyMove = null;
        int move = 0;

        //gets the position of the enemy
        ArrayList<Integer> enemyPosition = currentRoom.getMap().getPosition(enemy);

        //2 variables sh
        int moveableX = playerPosition.get(0) - enemyPosition.get(0);
        int moveableY = playerPosition.get(1) - enemyPosition.get(1);
        if (choiceMaker.nextBoolean()) {// 50/50 chance to make a good move or random
            if (moveableY != 0) { //moves enemy on the y-axis if it's not equal to players y-value
                if (moveableY > 0) {
                    move = 0;
                } else {
                    move = 1;
                }
            } else if (moveableX != 0) {//moves enemy on the x-axis if it's not equal to players x-value
                if (moveableX > 0) {
                    move = 2;
                } else {
                    move = 3;
                }
            } else { // moves randomly
                move = choiceMaker.nextInt(3);
            }
        }
        //creates the command
        switch (move) {
            case 0:
                enemyMove = new Command(commands.getCommandWord("go"), "down");
                break;
            case 1:
                enemyMove = new Command(commands.getCommandWord("go"), "up");
                break;
            case 2:
                enemyMove = new Command(commands.getCommandWord("go"), "right");
                break;
            case 3:
                enemyMove = new Command(commands.getCommandWord("go"), "left");
                break;
        }
        return enemyMove;
    }
}