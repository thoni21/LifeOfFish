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
    GameObjects[][] gridPrint = new GameObjects[column][row];

    public Grid(int column, int row, int enemiesQuantity, int foodQuantity, int obstaclesQuantity){
        this.column = column;
        this.row = row;
        this.enemiesQuantity = enemiesQuantity;
        this.foodQuantity = foodQuantity;
        this.obstaclesQuantity = obstaclesQuantity;
    }

    public void createGrid(){

        //a list of the quantity of different entities
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
            objectList[placeCounter] = new Enemies("Shark",-100,1,"☠");
            placeCounter++;
        }
        //loop that creates food and places them in the array
        for(int i = 0; i < foodQuantity; i++){
            objectList[placeCounter] = new Food("Crab",placeCounter,3.2,"\uD83E\uDD80");
            placeCounter++;
        }
        //loop that creates obstacles and places them in the array
        for(int i = 0; i < obstaclesQuantity; i++){
            objectList[placeCounter] = new Obstacles("Hard plastic",-i,i+2,"#");
            placeCounter++;
        }

        //creates the 2D grid
        GameObjects[][] grid = new GameObjects[column][row];

        //creates a 1D grad copy with a ArrayList
        ArrayList<GameObjects> gridCopy = new ArrayList<>();

        //places all entities in the gridCopy
        for(int i = 0; i < column*row; i++){
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
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                grid[j][i] = gridCopy.get(count);
                count++;
            }
        }

        gridPrint = grid;

    }

    public void printGrid(){
        for (int y=0 ; y<gridPrint.length ; y++) {
            for (int x=0 ; x< gridPrint[y].length ; x++) {
                System.out.print(" "+gridPrint[x][y].getSymbol()+" ");
            }
            System.out.println("");
        }
    }


}
