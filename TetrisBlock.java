package tetris;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the TetrisBlock class. It is a a wrapper class for a rectangle. Here, we just create a rectangle and add
 * some methods. Tetrisblocks are used by the different shapes
 */
public class TetrisBlock extends Rectangle {

    private boolean _isActive;
    private Rectangle _rect;
    private TetrisBlock[][] _gridArray;
    private GridPane _gridPane;

    /*
     * This is the constructor for TetrisBlock. Tetrisblock takes in the pane, array, location, and color of where
     * the tetrisblock should be on the board.
     */
    public TetrisBlock(GridPane Pane, TetrisBlock[][] Array,  int i, int j, Color color) {
        _gridPane = Pane;
        _gridArray = Array;
        _isActive = true;
        _rect = new Rectangle(Constants.TETRISBLOCK_SIZELENGTH, Constants.TETRISBLOCK_SIZELENGTH);
        //rectangle is added to the gridpane
        _gridPane.add(_rect, j, i);

        //graphics of the tetrisblock are set
        _rect.setFill(color);
        _rect.setStroke(Color.BLACK);
        _rect.setStrokeWidth(5);
    }

    /*
     * This is a setter method for the isActive boolean. isActive lets us know if the block is currently falling
     */
    public void setActive(Boolean active) {
        _isActive = active;
    }

    /*
     * This is the getter method for the isActive method
     */
    public boolean getActive() {
        return _isActive;
    }

    /*
     * This method simply moves the block down on the gridPane
     */
    public void moveDown() {
        _gridPane.setRowIndex(_rect, GridPane.getRowIndex(_rect) + 1);
    }

    /*
     * This method removes the tetrisblock from the gridpane
     */
    public void removeTetrisBlock() {
        _gridPane.getChildren().remove(_rect);
    }

    /*
     * This method is called to move a tetrisblock a certin amount of rows and columns. It both handles
     * moving the block graphically and logically
     */
    public void updateArray(int ioffset, int joffset) {
        _gridArray[_gridPane.getRowIndex(_rect) + ioffset][_gridPane.getColumnIndex(_rect) + joffset] = _gridArray[_gridPane.getRowIndex(_rect)][_gridPane.getColumnIndex(_rect)];
        _gridArray[_gridPane.getRowIndex(_rect)][_gridPane.getColumnIndex(_rect)] = null;

        _gridPane.setRowIndex(_rect,_gridPane.getRowIndex(_rect) + ioffset);
        _gridPane.setColumnIndex(_rect, _gridPane.getColumnIndex(_rect) + joffset);

    }

    /*
     * This method is very important as it is able to check if a movement of a tetrisblock is valid. If the proposed
     * row and column movement of the tetris block is valid, it returns true.
     */
    public boolean validMove(int ioffset, int joffset) {
        if (_gridArray[_gridPane.getRowIndex(_rect) + ioffset][_gridPane.getColumnIndex(_rect) + joffset] == null) {
            return true;
        }
        else if (_gridArray[_gridPane.getRowIndex(_rect) + ioffset][_gridPane.getColumnIndex(_rect) + joffset].getActive()) {
            return true;
        }
        else {
            return false;
        }
    }
}
