# Tetris
Tetris game made in JavaFX.
This implementation uses a 2d array to logically track the location of the tetris blocks and uses a javafx gridpane to display the tetris blocks.
The shape class is an abstract class that all of the different tetris shapes inherit from. The TetrisBlock class is a wrapper of a javafx rectangle and also provides move legality.

Clearing one line is worth 100 points, while clearing 4 lines at once, which is called a Tetris, is worth 400 extra points in addition to the base 400 points.
Every 500 points the game speed is doubled.

Keyboard Interactions:
    Left - move active shape left
    Right - move active shape right
    Down - move active shape down one block
    Up - Rotates active shape 90 degrees counterclockwise
    Space - Move active shape as far as it will go downwards
    P - pause/unpause the game
