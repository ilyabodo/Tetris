package tetris;

import javafx.scene.layout.GridPane;

/**
 * This is the abstract class Shape. It is inherited by all the different tetris shapes. It contains the common code
 * like moving left and right and all the instance variables that all of the shapes use.
 */
public abstract class Shape {

    //All of the instance variables. They are all public since they need to be inherited.
    public int _state;
    public TetrisBlock block1;
    public TetrisBlock block2;
    public TetrisBlock block3;
    public TetrisBlock block4;
    public TetrisBlock[][] gridArray;
    public GridPane gridPane;

    /*
     * The abstract shape constructor is empty.
     */
    public Shape(){

    }

    /*
     * This is an abstract method. The exact rotations of each shape are different, therefore the is no common code to
     * factor out.
     */
    public abstract void rotate();

    /*
     * This method moves each tetrisblock in a shape to the right
     */
    public void moveRight() {
        //First the movement is checked to make sure that is legal
        if (block1.validMove(0, 1) && block2.validMove(0, 1) && block3.validMove(0, 1) && block4.validMove(0, 1)) {
            //Blocks are moved in the correct order so no two are ontop of eachother at a given time
            block4.updateArray(0, 1);
            block2.updateArray(0, 1);
            block3.updateArray( 0, 1);
            block1.updateArray(0, 1);
        }
    }

    /*
     * This method moves each tetrisblock in a shape to the left.
     */
    public void moveLeft() {
        //First the movement is checked to make sure that is legal
        if (block1.validMove(0, -1) && block2.validMove(0, -1) && block3.validMove(0, -1) && block4.validMove(0, -1)) {
            //Blocks are moved in the correct order so no two are ontop of eachother at a given time
            block1.updateArray(0, -1);
            block3.updateArray(0, -1);
            block2.updateArray( 0, -1);
            block4.updateArray(0, -1);
        }
    }

}
