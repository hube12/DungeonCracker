package gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class SpawnerCoords {

	public int x;
	public int y;
	public int z;

	private Label[] labels = new Label[3];
	private TextField[] textFields = new TextField[3];

	public SpawnerCoords() {
		this.labels[0] = new Label("Spawner X Coord: ");
		this.labels[1] = new Label("Spawner Y Coord: ");
		this.labels[2] = new Label("Spawner Z Coord: ");

		for(int i = 0; i < this.labels.length; i++) {
			Label label = this.labels[i];
			label.setFont(Font.font(30.0D));
			label.setTranslateY(i * 60.0D - 128.0D);
			label.setTranslateX(240.0D);
		}

		for(int i = 0; i < this.textFields.length; i++) {
			TextField textField = new TextField();
			this.textFields[i] = textField;
			textField.setScaleX(2.0D);
			textField.setScaleY(2.0D);
			textField.setTranslateX(470.0D);
			textField.setTranslateY(i * 60.0D - 128.0D);
			textField.setMaxWidth(100.0D);

			textField.setOnKeyTyped(event -> {
				Platform.runLater(() -> DungeonCracker.generate.update());
			});
		}
	}

	public boolean parse() {
		try {
			this.x = Integer.parseInt(this.textFields[0].getText().trim());
			this.y = Integer.parseInt(this.textFields[1].getText().trim());
			this.z = Integer.parseInt(this.textFields[2].getText().trim());
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	public void addToPane(StackPane pane) {
		pane.getChildren().addAll(this.labels);
		pane.getChildren().addAll(this.textFields);
	}

}
