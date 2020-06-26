package tetris;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * This is the class that contains the code for the RightLShape. It inherits from the abstract Shape class.
 * This is one of 7 different shapes in the tetris game.
 */
public class SquareShape extends Shape{

    /*
     * Constructor for the specific shape. It creates the shape, gives it a color, and adds it to the array and gridpane
     * when called
     */
    public SquareShape(GridPane Pane, TetrisBlock[][] Array) {
        //Abstract constructor is called
        super();

        gridPane = Pane;
        gridArray = Array;

        //The 4 tetris blocks that make up this shape are created, colored, and put into both the 2d array and gridpane
        block1 = new TetrisBlock(gridPane, gridArray,1, 5, Color.PURPLE);
        block2 = new TetrisBlock(gridPane, gridArray, 1 ,6, Color.PURPLE);
        block3 = new TetrisBlock(gridPane, gridArray, 2, 5, Color.PURPLE);
        block4 = new TetrisBlock(gridPane, gridArray, 2, 6, Color.PURPLE);
        gridArray[1][5] = block1;
        gridArray[1][6] = block2;
        gridArray[2][5] = block3;
        gridArray[2][6] = block4;
    }

    /*
     * Since the square shape does not rotate, there is no code for the rotate command, but it still needs to be made
     * a concrete method so it can still be compiled/run
     */
    @Override
    public void rotate() {
    }

}
