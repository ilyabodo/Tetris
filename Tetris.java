package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This is the main game class. It contains most of the game logic.
 */
public class Tetris {

    private GridPane _gridPane;
    private Timeline _timeline;
    private TetrisBlock[][] _gridArray;
    private Shape _currentBlock;
    private Label _label;
    private double _timelineDuration;
    private KeyEvent _keyHandler;
    private boolean _isStopped;
    private boolean _needNextShape;
    private int _score;
    private int _previousScore;

    /*
     * This is the constructor for Tetris. All the main game components are set up here
     */
    public Tetris() {
        //initial values of variables are set
        _timelineDuration = Constants.TIME_INITIAL_DURATION;
        _isStopped = false;
        _needNextShape = false;

        //This is the gridpane. This is how the tetrisblocks will be displayed on the screen
        _gridPane = new GridPane();
        //This 2d array handles the logical location of all the tetris blocks
        _gridArray = new TetrisBlock[Constants.GRIDPANE_I_SIZE][Constants.GRIDPANE_J_SIZE];

        //The grid is set up with the tetrisblocks on the border
        this.setUpGrid();

        //The timeline is setup and started
        this.setUpTimeline();
        //the gridpane gets the focus so the keyboard inputs work correctly
        _gridPane.setFocusTraversable(true);

        //The first block is created
        this.nextShape();

        /*
         * This is the keyhandler. There are two different switch statements. The first switch statement handles weither
         * the game is paused or not. Only if the game is not paused will the second switch statement work.
         * The second switch handles the rest of the game functions.
         */
        _gridPane.setOnKeyPressed(e -> {
            _keyHandler = e;
            switch (_keyHandler.getCode()) {
                case P:
                    if (_isStopped) {
                        _isStopped = false;
                        //starts the timeline from a pause
                        _timeline.play();
                    } else {
                        _isStopped = true;
                        //stops the timeline with paused
                        _timeline.stop();
                    }
                    break;
                default:
                    break;
            }

            if (!_isStopped) {
                switch (_keyHandler.getCode()) {
                    // if a key is pressed, the variable used for setting the positions of the cartoon will change
                    case UP:
                        //rotates the currentblock according to its specific rotation vectors
                        _currentBlock.rotate();
                        break;
                    case RIGHT:
                        //moves the active shape right
                        _currentBlock.moveRight();
                        break;
                    case LEFT:
                        //rotates the active shape left
                        _currentBlock.moveLeft();
                        break;
                    case DOWN:
                        //calls the moveDown method to check and move down the active shape
                        this.moveDown();
                        break;
                    case SPACE:
                        //this while loop does the same thing as DOWN but it repeats until the shape is as low as it can go
                        while (!_needNextShape) {
                            this.moveDown();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /*
     * This method sets up the score label by adding to the VBox that the button is also on
     */
    public void setUpLabel(VBox pane){
        //score is initially set to 0
        _score = 0;
        _previousScore = _score;
        _label = new Label("Score: " + _score);
        _label.setStyle("-fx-font: 24 arial;");
        Pane labelPane = new Pane();
        labelPane.getChildren().add(_label); //adds the label to the pane
        pane.getChildren().add(_label);
    }

    /*
     * Returns the gridpane so it can be added to the root borderpane
     */
    public GridPane getGrid() {
        return _gridPane;
    }

    /*
     * This helper method sets up the tetris grid. It first loops through and makes each of 12 columns 30 pixels wide
     * and 30 pixels tall. It also adds the border stationary tetris blocks
     */
    private void setUpGrid() {
        for (int i = 0; i < Constants.ARRAY_J_SIZE + 1; i++) {
            _gridPane.getColumnConstraints().add(new ColumnConstraints(Constants.TETRISBLOCK_SIZELENGTH));
        }
        for (int i = 0; i < Constants.ARRAY_I_SIZE + 1; i++) {
            _gridPane.getRowConstraints().add(new RowConstraints(Constants.TETRISBLOCK_SIZELENGTH));
        }

        //Adds tetris blocks to the border of the grid
        for (int i = 0; i < Constants.ARRAY_I_SIZE + 1; i++) {
            for (int j = 0; j < Constants.ARRAY_J_SIZE + 1; j++) {
                if (i == 0 || i == Constants.ARRAY_I_SIZE || j == 0 || j == Constants.ARRAY_J_SIZE) {
                    TetrisBlock borderBlock = new TetrisBlock(_gridPane, _gridArray, i, j, Color.DIMGRAY);
                    //the active status of the blocks is set to not active so they wont be moved
                    borderBlock.setActive(false);
                    //adds block to the 2d array
                    _gridArray[i][j] = borderBlock;

                }
            }
        }
    }

    /*
     * This helper method is called every time the timeline updates and checks if one of the lines needs to be cleared
     * It loops through each row, counting how many tetrisblocks are there clears them if its full
     */
    private void isRowFull() {
        int RowCounter = 0;
        int linesCleared = 0;
        for (int i = Constants.ARRAY_I_SIZE -1; i > 0; i--) {
            for (int j = 1; j < Constants.ARRAY_J_SIZE; j++) {
                if (j == 1) {
                    RowCounter = 0;
                }
                if (_gridArray[i][j] != null && !_gridArray[i][j].getActive()) {
                    RowCounter = RowCounter + 1;
                }
            }
            if (RowCounter == Constants.NUMBER_OF_GAME_COLUMNS) {
                //if the row is full, calls removerow helper method
                this.removeRow(i);
                //increases the score and updates the label
                _score = _score + Constants.SCORE_LINE;
                _label.setText("Score: " + _score);
                //keeps track of how many lines are cleared at a time
                linesCleared = linesCleared + 1;
            }
        }
        //if 4 lines are cleared at the same time, its a TETRIS and the player gets 800 points instead of 400
        if (linesCleared == Constants.NUMBER_OF_BLOCKS_PER_SHAPE) {
            _score = _score + Constants.SCORE_LINE*4;
            _label.setText("Score: " + _score);
        }
    }

    /*
     * This helper method clears the rows once they are full. It also moves down every tetrisblock above the cleared line
     */
    private void removeRow(int row) {
        //loops through every block in a row and removes it graphically and logically
        for (int j = 1; j < Constants.ARRAY_J_SIZE; j++) {
            _gridArray[row][j].removeTetrisBlock();
            _gridArray[row][j] = null;
        }
        //loops upwards to move blocks down without putting them ontop of eachother
        for (int i = row; i > 0; i--) {
            for (int j = 1; j < Constants.ARRAY_J_SIZE; j++) {
                if (_gridArray[i][j] instanceof TetrisBlock) {
                    _gridArray[i][j].moveDown();
                    _gridArray[i+1][j] = _gridArray[i][j];
                    _gridArray[i][j] = null;
                }
            }
        }
    }

    /*
     * This method is called when a new shape is needed. It generates a random number and randomly selects a new shape
     */
    private void nextShape() {
        int chooser = (int) (Math.random() * 7);
        switch (chooser) {
            case 0:
                _currentBlock = new RightLShape(_gridPane, _gridArray);
                break;
            case 1:
                _currentBlock = new LeftLShape(_gridPane, _gridArray);
                break;
            case 2:
                _currentBlock = new RightZShape(_gridPane, _gridArray);
                break;
            case 3:
                _currentBlock = new LeftZShape(_gridPane, _gridArray);
                break;
            case 4:
                _currentBlock = new TShape(_gridPane, _gridArray);
                break;
            case 5:
                _currentBlock = new SquareShape(_gridPane, _gridArray);
                break;
            case 6:
                _currentBlock = new LineShape(_gridPane, _gridArray);
                break;
            default:
                break;
        }
    }

    /*
     * This overlycompliated helper method is responsible for moveing down the active shape.
     */
    private void moveDown() {
        int blockCheck = 0;

        //loops though entire board looking for active tetrisblocks (aka the moving shape)
        for (int i = Constants.ARRAY_I_SIZE - 1; i > 0; i--) {
            for (int j = 1; j < Constants.ARRAY_J_SIZE; j++) {

                if (_gridArray[i][j] != null && _gridArray[i][j].getActive()) {

                    //If a active block is found, it checks if there is a nonmoving block directly below it.
                    if (_gridArray[i + 1][j] != null && !_gridArray[i + 1][j].getActive()){

                        //If there is a block below, it loops through all active blocks and sets their status to not active
                        for (int k = Constants.ARRAY_I_SIZE - 1; k >= 0; k--) {
                            for (int l = 0; l < Constants.ARRAY_J_SIZE + 1; l++) {
                                if (_gridArray[k][l] != null && _gridArray[k][l].getActive()) {
                                    _gridArray[k][l].setActive(false);

                                    //if a block is set to inactive at the very time, the game ends
                                    if (k == 1) {
                                        _label.setText("Score: " + _score + " GAME OVER");
                                        _timeline.stop();
                                        _isStopped = true;
                                    }
                                }
                            }
                        }
                        //if the blocks are set to inactive, the will not be moved therefore the next shape is needed.
                        _needNextShape = true;
                    }

                    /*
                     *IF there is not a block below the shape, a counter starts looking for active blocks. It there are
                     * 4 active blocks, each active block is moved down. The reason for this, that without it, a block
                     * could be allowed to move down, but the next block cannot, are the composite shape would become
                     * disfigured.
                     */
                    else {
                        blockCheck = blockCheck + 1;
                        if (blockCheck == Constants.NUMBER_OF_BLOCKS_PER_SHAPE) {
                            //moves down the 4 active blocks
                            for (int a = Constants.ARRAY_I_SIZE - 1; a > 0; a--) {
                                for (int b = 1; b < Constants.ARRAY_J_SIZE; b++) {
                                    //checks if there is a block to move down and prevent nullpointer error
                                    if (_gridArray[a][b] != null && _gridArray[a][b].getActive()) {
                                        _gridArray[a][b].moveDown();
                                        _gridArray[a+1][b] = _gridArray[a][b];
                                        _gridArray[a][b] = null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * This helper method sets up the time
     */
    private void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(_timelineDuration), new Tetris.TimeHandler());
        _timeline = new Timeline(kf);
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();

    }

    /*
     * This is the private inner class that is the timehandler.
     */
    private class TimeHandler implements EventHandler<ActionEvent> {

        public void handle(ActionEvent event) {
            //moves down the active block
            Tetris.this.moveDown();
            //checks if there is a full row and removes it if necessary
            Tetris.this.isRowFull();

            //Calls the creation of a new shape if the active shape was just turned inactive
            if (_needNextShape) {
                Tetris.this.nextShape();
                _needNextShape = false;
            }

            //This if statement restarts the timeline so it speed up to be twice as fast every 500 points
            if (_previousScore + Constants.SCORE_SPEED_THRESHOLD <= _score) {
                _timeline.stop();
                _timeline = null;
                _timelineDuration = _timelineDuration/2;
                _previousScore = _score;
                Tetris.this.setUpTimeline();
            }
        }

    }

}
