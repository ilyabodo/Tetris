package tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the App class which sets up the stage and uses the pane from paneorganizer to display all the elements
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        PaneOrganizer paneOrganizer = new PaneOrganizer();
        primaryStage.setScene(new Scene(paneOrganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT));
        primaryStage.setTitle("Tetris");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
