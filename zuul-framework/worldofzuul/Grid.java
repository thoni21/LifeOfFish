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
    }

    public void createGrid() {

        //a list of the quantity of different entities
        int totalQuantity = playerQuantity + enemiesQuantity +
                foodQuantity + obstaclesQuantity;


        //a number that shows where en the Array there's room
        int placeCounter = 0;

        //Array where all entities are going in
        GameObjects[] objectList = new GameObjects[totalQuantity];

        //creating the player
        objectList[placeCounter] = new Player("Tuna", 0.0, 20, 1, 0.0, "\uD83D\uDC1F");
        placeCounter++;


        //loop that creates enemies and places them in the array
        for (int i = 0; i < enemiesQuantity; i++) {
            objectList[placeCounter] = new Enemies("Shark", -100, 1, "☠");
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
                grid[j][i] = gridCopy.get(count);
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

    private boolean positionCheck(GameObjects entity, int positionCode) {
        // 1 = upper side, 2 = Right side, 3 = lower side, 4 = left side and 5 = the middle

        //boolean that's gets manipulated and returned
        boolean checker = false;

        //5 cases where each possible placement excluding the courses are checked
        switch (positionCode) {
            case 1:
                for (int i = 1; i < this.row - 1; i++) {
                    if (this.grid[0][i] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 2:
                for (int i = 1; i < this.column - 1; i++) {
                    if (this.grid[i][this.row] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 3:
                for (int i = 1; i < this.row - 1; i++) {
                    if (this.grid[this.column][i] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 4:
                for (int i = 1; i < this.row - 1; i++) {
                    if (this.grid[i][0] == entity) {
                        checker = true;
                        break;
                    }
                }
            case 5:
                for (int i = 1; i < this.row - 1; i++) {
                    for (int j = 1; j < this.column - 1; j++) {
                        if (this.grid[i][j] == entity) {
                            checker = true;
                            break;
                        }
                    }
                }
        }
        return checker;

    }

        private boolean legalMove(GameObjects entity, Command direction){
        //boolean that's gets manipulated and returned
        boolean isLegalMove = false;

        //9 different if-else statements that cover each position and available move options
            //checks if the player stands up in the left corner, and that's he inputs the right second word
            if (this.grid[0][0] == entity && (direction.getSecondWord().equals("Right") ||
                    direction.getSecondWord().equals("Down"))) {
                isLegalMove = true;
            }
            //checks if the player stands up in the right corner, and that's he inputs the right second word
            else if (this.grid[this.row][0] == entity && (direction.getSecondWord().equals("Left") ||
                    direction.getSecondWord().equals("Down"))) {
                isLegalMove = true;
            }
            //checks if the player stands in the bottom left corner, and that's he inputs the right second word
            else if (this.grid[0][this.column] == entity && (direction.getSecondWord().equals("Right") ||
                    direction.getSecondWord().equals("Up"))) {
                isLegalMove = true;
            }
            //checks if the player stands in the bottom right corner, and that's he inputs the right second word
            else if (this.grid[this.row][this.column] == entity && (direction.getSecondWord().equals("Left") ||
                    direction.getSecondWord().equals("Up"))) {
                isLegalMove = true;
            }
            //with help from the method positionCheck it checks if the player is on the upper side of the map
            // and that's he inputs the right second word
            else if (positionCheck(entity, 1) && (direction.getSecondWord().equals("Right") ||
                    direction.getSecondWord().equals("Left") || direction.getSecondWord().equals("Down"))) {
                isLegalMove = true;
            }
            //with help from the method positionCheck it checks if the player is on the right side of the map
            // and that's he inputs the right second word
            else if (positionCheck(entity, 2) && (direction.getSecondWord().equals("Up") ||
                    direction.getSecondWord().equals("Left") || direction.getSecondWord().equals("Down"))) {
                isLegalMove = true;
            }
            //with help from the method positionCheck it checks if the player is on the lower side of the map
            // and that's he inputs the right second word
            else if (positionCheck(entity, 3) && (direction.getSecondWord().equals("Up") ||
                    direction.getSecondWord().equals("Left") || direction.getSecondWord().equals("Right"))) {
                isLegalMove = true;
            }
            //with help from the method positionCheck it checks if the player is on the left side of the map
            // and that's he inputs the right second word
            else if (positionCheck(entity, 4) && (direction.getSecondWord().equals("Up") ||
                    direction.getSecondWord().equals("Down") || direction.getSecondWord().equals("Right"))) {
                isLegalMove = true;
            }
            //with help from the method positionCheck it checks if the player is in the middle
            // and that's he inputs the right second word
            else if(positionCheck(entity, 5)){
                isLegalMove = true;
            }
            return isLegalMove;
        }
    }
