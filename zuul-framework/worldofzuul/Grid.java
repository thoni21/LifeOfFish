package worldofzuul;

import java.util.ArrayList;
import java.util.Collections;

public class Grid {

    //Attributes
    private int column;
    private int row;
    private int playerQuantity = 1;
    private int enemiesQuantity;
    private int foodQuantity;
    private int obstaclesQuantity;
    private GameObjects[][] grid;

    //Constructor
    public Grid(int column, int row, int enemiesQuantity, int foodQuantity, int obstaclesQuantity) {
        this.column = column;
        this.row = row;
        this.enemiesQuantity = enemiesQuantity;
        this.foodQuantity = foodQuantity;
        this.obstaclesQuantity = obstaclesQuantity;
        createGrid();
    }

    //Methods
    private void createGrid() {

        //a list of the quantity of different entities
        int totalQuantity = playerQuantity + enemiesQuantity +
                foodQuantity + obstaclesQuantity;


        //a number that shows where en the Array there's room
        int placeCounter = 0;

        //Array where all entities are going in
        GameObjects[] objectList = new GameObjects[totalQuantity];

        //creating the player
        objectList[placeCounter] = new Player("Tuna", 10, 1, 0.0, "\uD83D\uDC1F");
        placeCounter++;


        //loop that creates enemies and places them in the array
        for (int i = 0; i < enemiesQuantity; i++) {
            objectList[placeCounter] = new Enemies("Octopus", -1000, 1, "\uD83D\uDC19");
            placeCounter++;
        }
        //loop that creates food and places them in the array
        for (int i = 0; i < foodQuantity; i++) {
            objectList[placeCounter] = new Food("Crab", 6, 3, "\uD83E\uDD90");
            placeCounter++;
        }
        //loop that creates obstacles and places them in the array
        for (int i = 0; i < obstaclesQuantity; i++) {
            objectList[placeCounter] = new Obstacles("hard plastic", -5, 20, "\uD83D\uDDD1");
            placeCounter++;
        }

        //creates the 2D grid
        GameObjects[][] grid = new GameObjects[column][row];

        //creates a 1D grad copy with a ArrayList
        ArrayList<GameObjects> gridCopy = new ArrayList<>();

        //places all entities in the gridCopy
        for (int i = 0; i < column * row; i++) {
            try {
                gridCopy.add(objectList[i]);
            } catch (ArrayIndexOutOfBoundsException ex) {
                gridCopy.add(new Water());
            }
        }
        //shuffles all entities in the ArrayList
        Collections.shuffle(gridCopy);

        //marge 1D grid with 2D grid
        int count = 0;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                grid[j][i] = gridCopy.get(count);
                count++;
            }
        }

        this.grid = grid;

    }

    //Method to print grid
    public void printGrid() {
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                System.out.print(" " + this.grid[row][column].getSymbol() + " ");
            }
            System.out.println("");
        }
    }

    public ArrayList<Integer> getPosition(GameObjects entity) {

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the entity
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] == entity) {
                    position.add(row);
                    position.add(column);
                    break;
                }
            }
        }
        return position;
    }

    //Method to find player object
    public Player findPlayer() {

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the player
        for (int column = 0; column < this.column; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Player) {
                    position.add(row);
                    position.add(column);
                    break;
                }
            }
        }
        return (Player) this.grid[position.get(0)][position.get(1)];
    }

    //Method that finds all Enemies and returns them in an Arraylist
    public ArrayList<Enemies> getAllEnemies(){
        //crates the arraylist
        ArrayList<Enemies> enemies = new ArrayList<>();

        //loops through the grid, looking for enemies
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                if (this.grid[row][column] instanceof Enemies) {
                    enemies.add((Enemies) this.grid[row][column]);
                }
            }
        }
        return enemies;
    }

    //Movement in grid
    public void gridMovement(GameObjects entity, Command direction) throws IllegalMoveException {

        //2 values that represent where the entity is going
        int row = 0;
        int column = 0;

        //manipulation of the 2 values according to the action
        if (direction.getSecondWord().equals("right")) {
            row = 1;
        } else if (direction.getSecondWord().equals("left")) {
            row = -1;
        } else if (direction.getSecondWord().equals("up")) {
            column = -1;
        } else if (direction.getSecondWord().equals("down")) {
            column = 1;
        }

        //method that finds the entity position in the grid
        ArrayList<Integer> entityPosition = getPosition(entity);

        //takes whatever there is on the position the entity is about to go to at stores it
        GameObjects placeholder = null;
        try {
           placeholder = this.grid[entityPosition.get(0) + row][entityPosition.get(1) + column];
       } catch (IndexOutOfBoundsException ex){
           throw new IllegalMoveException("This is a illegal move, try something else.");
       }

        //overrides that position with the entity
        this.grid[entityPosition.get(0) + row][entityPosition.get(1) + column] = entity;

        //places water where original the entity was
        this.grid[entityPosition.get(0)][entityPosition.get(1)] = new Water();

        findPlayer().addTotalTurns(1);
        findPlayer().removeTurns(1);

        //checks if the entity is the player
        if (entity instanceof Player) {
            if (placeholder instanceof Food) { //checks if the player collided with a food
                ((Player) entity).addTurns(placeholder.getTurnValue());
                ((Player) entity).addPollutionValue(placeholder.getPollutionValue());

                System.out.println("You ate a " + placeholder.getName() + ".");
                System.out.println("For this action you have gained some energy.");
                System.out.println("...and maybe something more.");

            } else if (placeholder instanceof Obstacles) { //checks if the player collided with an obstacle
                ((Player) entity).addTurns(placeholder.getTurnValue());
                ((Player) entity).addPollutionValue(placeholder.getPollutionValue());

                System.out.println("You accidentally ate a " + placeholder.getName() + ".");
                if(((Player) entity).getTurnValue() <= 0){
                    ((Player) entity).triggerDeath();

                    System.out.println("You have eaten too much waste and as such you have run out of energy.");
                } else {
                    System.out.println("For this action you will lose some energy.");
                    System.out.println("...and maybe something more.");
                }
            } else if (placeholder instanceof Water) { //checks if the player collided with some water
                ((Player) entity).addPollutionValue(placeholder.getPollutionValue());

                System.out.println("There is nothing.");
                if(((Player) entity).getTurnValue() <= 0){
                    ((Player) entity).triggerDeath();

                    System.out.println("You have not eaten enough food and have thus run out of energy.");
                } else {
                    System.out.println("This action will yield you nothing.");
                    System.out.println("... or maybe it will.");
                }

            } else if (placeholder instanceof Enemies) { //checks if the player collided with an enemy
                ((Player) entity).triggerDeath();

                System.out.println("Too bad.");
                System.out.println("You have confronted a " + placeholder.getName() + ".");
                System.out.println("This action has resulted in your death.");
            }
        } else if (entity instanceof Enemies) { //checks if the entity is an enemy
            if (placeholder instanceof Player) { //checks if the enemy collided with the player
                ((Player) entity).triggerDeath();

                System.out.print("Too bad.");
                System.out.println("an " + placeholder.getName() + " has caught you.");
                System.out.println("This action have resulted in your death.");
            } else if(placeholder instanceof Enemies){//checks if the enemy collided with another enemy
                this.grid[entityPosition.get(0)][entityPosition.get(1)] = placeholder;

            }
        }

        findPlayer().calculateScore();
    }

    //Moves player to next level
    public void movePlayerToNextLevel(Grid grid){

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the player
        for (int column = 0; column < grid.getGrid().length; column++) {
            for (int row = 0; row < grid.getGrid()[column].length; row++) {
                if (grid.getGrid()[row][column] instanceof Player) {
                    position.add(row);
                    position.add(column);
                    break;
                }
            }
        }

        this.grid[getPosition(findPlayer()).get(0)][getPosition(findPlayer()).get(1)]
                = (Player) grid.getGrid()[position.get(0)][position.get(1)];
    }

    public GameObjects[][] getGrid() {
        return grid;
    }
}