package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DungeonCracker extends Application {

	public static Floor floor = new Floor();
	public static SpawnerCoords spawnerCoords = new SpawnerCoords();
	public static Generate generate = new Generate();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		StackPane pane = new StackPane();
		stage.setTitle("Dungeon Cracker");
		stage.setResizable(false);

		Scene scene = new Scene(pane, 1280, 720);

		floor.addToPane(pane);
		spawnerCoords.addToPane(pane);
		generate.addToPane(pane);

		stage.setScene(scene);
		stage.show();

		stage.setOnCloseRequest(event -> {
			Bruteforce.instance.close();
		});
	}

}
