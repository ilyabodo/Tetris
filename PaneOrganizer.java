package tetris;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * this is the paneorganizer class which sets up graphically the pane that is used my App class to display the stage.
 * It also contains the event handlers that trigger from button presses, key inputs, or the timeline.
 */
public class PaneOrganizer{

    private BorderPane _root;
    private Tetris _tetris;

    public PaneOrganizer() {
        //all the initial variables and objects are instanciated here
        _root = new BorderPane();
        _tetris = new Tetris();
        _root.setCenter(_tetris.getGrid());

        //Quit button and label are set up
        this.setUpButtons();
    }

    /*
     * This method is a getter method so the root pane can be passed to the App class.
     */
    public Pane getRoot() {
        return _root;
    }

    /*
     * This method is only used internally and is responsible for setting up the quit button and the label at the
     * bottom of the screen
     */
    private void setUpButtons() {
        VBox buttonPane = new VBox();
        //the new pane is set to the bottom of the root borderpane
        _root.setBottom(buttonPane);

        //Label is created from the Tetris class and added to the pane with the button.
        _tetris.setUpLabel(buttonPane);

        //button is created and added to the pane
        Button button = new Button("Quit");
        buttonPane.setAlignment(Pos.CENTER);
        //adds the button to the Vbox
        buttonPane.getChildren().add(button);
        //this eventhandler exits the application if the button is clicked
        button.setOnAction(e -> Platform.exit());
        //This removes focus so that the keyboard inputs work correctly.
        button.setFocusTraversable(false);
    }

}
