package tetris;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


/**
 * This is the class that contains the code for the RightLShape. It inherits from the abstract Shape class.
 * This is one of 7 different shapes in the tetris game.
 */
public class LineShape extends Shape{

    /*
     * Constructor for the specific shape. It creates the shape, gives it a color, and adds it to the array and gridpane
     * when called
     */
    public LineShape(GridPane Pane, TetrisBlock[][] Array) {
        //Abstract constructor is called
        super();

        gridPane = Pane;
        gridArray = Array;

        //The 4 tetris blocks that make up this shape are created, colored, and put into both the 2d array and gridpane
        block1 = new TetrisBlock(gridPane, gridArray,1, 4, Color.RED);
        block2 = new TetrisBlock(gridPane, gridArray, 1 ,6, Color.RED);
        block3 = new TetrisBlock(gridPane, gridArray, 1, 5, Color.RED);
        block4 = new TetrisBlock(gridPane, gridArray, 1, 7, Color.RED);
        gridArray[1][4] = block1;
        gridArray[1][6] = block2;
        gridArray[1][5] = block3;
        gridArray[1][7] = block4;
    }

    /*
     * This method is overridden from the abstract Shape class. It contains the specific instructions on how the individual
     * tetrisblocks that make up this shape should be moved so that the entire shape rotates correctly. The current state
     * of the shape is stored so we know how to next move the shape.
     */
    @Override
    public void rotate() {
        switch (_state) {
            case 0:
                //The rotation is first checked to make sure its valid
                if (block1.validMove(-1, 1) && block2.validMove(1, -1) && block4.validMove(2, -2)) {
                    //The necessary movements to the location of the tetrisblocks is made by updating the array and gridpane
                    block1.updateArray(-1, 1);
                    block2.updateArray(1, -1);
                    block4.updateArray(2, -2);
                    //the state is updated to reflect the current orientation of the shape
                    _state = 1;
                    break;
                }
                else {
                    break;
                }
            case 1:
                if (block1.validMove(1, -1) && block2.validMove(-1, 1) && block4.validMove(-2, 2)) {
                    block1.updateArray(1, -1);
                    block2.updateArray(-1, 1);
                    block4.updateArray(-2, 2);
                    _state = 0;
                    break;
                }
                else {
                    break;
                }
            default:
                break;


        }
    }







}
