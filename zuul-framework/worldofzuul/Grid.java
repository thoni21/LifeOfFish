package worldofzuul;

import java.util.ArrayList;
import java.util.Collections;

public class Grid {

    int column;
    int row;
    int playerQuantity = 1;
    int enemiesQuantity;
    int foodQuantity;
    int obstaclesQuantity;
    GameObjects[][] grid;

    public Grid(int column, int row, int enemiesQuantity, int foodQuantity, int obstaclesQuantity) {
        this.column = column;
        this.row = row;
        this.enemiesQuantity = enemiesQuantity;
        this.foodQuantity = foodQuantity;
        this.obstaclesQuantity = obstaclesQuantity;
        createGrid();
    }

    private void createGrid() {

        //a list of the quantity of different entities
        int totalQuantity = playerQuantity + enemiesQuantity +
                foodQuantity + obstaclesQuantity;


        //a number that shows where en the Array there's room
        int placeCounter = 0;

        //Array where all entities are going in
        GameObjects[] objectList = new GameObjects[totalQuantity];

        //creating the player
        objectList[placeCounter] = new Player("Tuna", 0.0, 20, 1,
                0.0, "+");
        placeCounter++;


        //loop that creates enemies and places them in the array
        for (int i = 0; i < enemiesQuantity; i++) {
            objectList[placeCounter] = new Enemies("Shark", -100, 1, "*");
            placeCounter++;
        }
        //loop that creates food and places them in the array
        for (int i = 0; i < foodQuantity; i++) {
            objectList[placeCounter] = new Food("Crab", placeCounter, 3.2, "\uD83E\uDD80");
            placeCounter++;
        }
        //loop that creates obstacles and places them in the array
        for (int i = 0; i < obstaclesQuantity; i++) {
            objectList[placeCounter] = new Obstacles("Hard plastic", -i, i + 2, "#");
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
                grid[i][j] = gridCopy.get(count);
                count++;
            }
        }

        this.grid = grid;

    }

    public void printGrid() {
        for (int column = 0; column < this.grid.length; column++) {
            for (int row = 0; row < this.grid[column].length; row++) {
                System.out.print(" " + this.grid[column][row].getSymbol() + " ");
            }
            System.out.println("");
        }
    }

    public ArrayList<Integer> getPosition(GameObjects entity) {

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the entity
        for (int y = 0; y < this.column; y++) {
            for (int x = 0; x < this.row; x++) {
                if (this.grid[y][x] == entity) {
                    position.add(y);
                    position.add(x);
                    break;
                }
            }
        }
        return position;
    }

    public GameObjects findPlayer() {

        //list that's going to get returned
        ArrayList<Integer> position = new ArrayList<>();

        //checks all positions in the 2D array for the player
        for (int y = 0; y < this.column; y++) {
            for (int x = 0; x < this.row; x++) {
                if (this.grid[y][x] instanceof Player) {
                    position.add(y);
                    position.add(x);
                    break;
                }
            }
        }
        return this.grid[position.get(0)][position.get(1)];
    }

    private boolean positionCheck(GameObjects entity, int positionCode) {
        // 1 = upper side, 2 = Right side, 3 = lower side, 4 = left side and 5 = the middle

        //boolean that's gets manipulated and returned
        boolean checker = false;

        //5 cases where each possible placement excluding the courses are checked
        switch (positionCode) {
            case 1:
                for (int i = 1; i < this.column - 1; i++) {
                    if (this.grid[i][0] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 2:
                for (int i = 1; i < this.row - 1; i++) {
                    if (this.grid[this.column-1][i] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 3:
                for (int i = 1; i < this.column - 1; i++) {
                    if (this.grid[i][this.row-1] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 4:
                for (int i = 1; i < this.row - 1; i++) {
                    if (this.grid[0][i] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 5:
                for (int i = 1; i < this.column - 1; i++) {
                    for (int j = 1; j < this.row - 1; j++) {
                        if (this.grid[i][j] == entity) {
                            checker = true;
                            break;
                        }
                    }
                }
        }
        return checker;

    }

    private boolean isLegalMove(GameObjects entity, Command direction) {
        //boolean that's gets manipulated and returned
        boolean isLegalMove = false;

        //9 different if-else statements that cover each position and available move options

        //checks if the player stands up in the left corner, and that's he inputs the right second word
        if (this.grid[0][0] == entity && (direction.getSecondWord().equals("left") ||
                direction.getSecondWord().equals("up"))) {
            isLegalMove = true;
        }
        //checks if the player stands up in the right corner, and that's he inputs the right second word
        else if (this.grid[this.column-1][0] == entity && (direction.getSecondWord().equals("right") ||
                direction.getSecondWord().equals("up"))) {
            isLegalMove = true;
        }
        //checks if the player stands in the bottom left corner, and that's he inputs the right second word
        else if (this.grid[0][this.row-1] == entity && (direction.getSecondWord().equals("left") ||
                direction.getSecondWord().equals("down"))) {
            isLegalMove = true;
        }
        //checks if the player stands in the bottom right corner, and that's he inputs the right second word
        else if (this.grid[this.column-1][this.row-1] == entity && (direction.getSecondWord().equals("right") ||
                direction.getSecondWord().equals("down"))) {
            isLegalMove = true;
        }
        //with help from the method positionCheck it checks if the player is on the upper side of the map
        // and that's he inputs the right second word
        else if (positionCheck(entity, 1) && direction.getSecondWord().equals("up")){
            isLegalMove = true;
        }
        //with help from the method positionCheck it checks if the player is on the right side of the map
        // and that's he inputs the right second word
        else if (positionCheck(entity, 2) && direction.getSecondWord().equals("right")){
            isLegalMove = true;
        }
        //with help from the method positionCheck it checks if the player is on the lower side of the map
        // and that's he inputs the right second word
        else if (positionCheck(entity, 3) && direction.getSecondWord().equals("down")){
            isLegalMove = true;
        }
        //with help from the method positionCheck it checks if the player is on the left side of the map
        // and that's he inputs the right second word
        else if (positionCheck(entity, 4) && direction.getSecondWord().equals("left")) {
            isLegalMove = true;
        }
        //with help from the method positionCheck it checks if the player is in the middle
        // and that's he inputs the right second word
        else if (positionCheck(entity, 5)) {
            isLegalMove = true;
        }
        return isLegalMove;
    }

    public void gridMovement(GameObjects entity, Command direction) throws IllegalMoveException {

        //finds out if the action is legal if not it throws an exception
        if (!isLegalMove(entity, direction)) {
            throw new IllegalMoveException("this is a illegal move, try something else.");
        }

        //2 values that represent where the entity is going
        int i = 0;
        int j = 0;

        //manipulation of the 2 values according to the action
        if (direction.getSecondWord().equals("right")) {
            i = 1;
        } else if (direction.getSecondWord().equals("left")) {
            i = -1;
        } else if (direction.getSecondWord().equals("up")) {
            j = -1;
        } else if (direction.getSecondWord().equals("down")) {
            j = 1;
        }

        //method that finds the entity position in the grid
        ArrayList<Integer> entityPosition = getPosition(entity);

        //takes whatever there is on the position the entity is about to go to at stores it
        GameObjects placeholder = this.grid[entityPosition.get(0) + j][entityPosition.get(1) + i];

        //overrides that position with the entity
        this.grid[entityPosition.get(0) + j][entityPosition.get(1) + i] = entity;

        //places water where original the entity was
        this.grid[entityPosition.get(0)][entityPosition.get(1)] = new Water();

        //checks if the entity is the player
        if (entity instanceof Player) {
            if (placeholder instanceof Food) { //checks if the player collided with a food
                ((Player) entity).addTurns(placeholder.turnValue);
                ((Player) entity).addPollutionValue(placeholder.pollutionValue);

                System.out.print("congratulation.");
                System.out.println("You have eating a " + placeholder.getName() + ".");
                System.out.println("For this action you will gain a few more turns.");
                System.out.println("...and maybe something more.");
            } else if (placeholder instanceof Obstacles) { //checks if the player collided with an obstacle
                ((Player) entity).addTurns(placeholder.turnValue);
                ((Player) entity).addPollutionValue(placeholder.pollutionValue);

                System.out.print("Too bad.");
                System.out.println("You have accidentally eating a " + placeholder.getName() + ".");
                System.out.println("For this action you will lose a few turns.");
                System.out.println("...and maybe something more.");
            } else if (placeholder instanceof Water) { //checks if the player collided with some water
                ((Player) entity).addPollutionValue(placeholder.pollutionValue);

                System.out.print("There is nothing.");
                System.out.println("this action will yield you nothing.");
                System.out.println("... or maybe it will.");
            } else if (placeholder instanceof Enemies) { //checks if the player collided with an enemy
                ((Player) entity).triggerDeath();

                System.out.print("Too bad.");
                System.out.println("You have confronted a " + placeholder.getName() + ".");
                System.out.println("This action have resulted in your death.");
            }
        } else if (entity instanceof Enemies) { //checks if the entity is an enemy
            if (placeholder instanceof Player) { //checks if the enemy collided with the player
                ((Player) entity).triggerDeath();

                System.out.print("Too bad.");
                System.out.println("You have confronted a " + placeholder.getName() + ".");
                System.out.println("This action have resulted in your death.");
            }
        }

    }
}