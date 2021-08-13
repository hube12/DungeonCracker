package kinomora.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class dungeonCrackerGUI extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        stage.setTitle("Dungeon Cracker");
        stage.setResizable(false);

        Scene scene = new Scene(pane, 1280, 720);

        stage.setScene(scene);
        stage.show();
    }
}
