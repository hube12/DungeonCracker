package gui;

import dungeons.Result;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;


public class Generate {

	public MCVersion version = MCVersion.v1_15;
	private final Button versionButton;
	private final Button findSeedsButton;

	public Generate() {
		this.versionButton = new Button();
		this.versionButton.setScaleX(2.0D);
		this.versionButton.setScaleY(2.0D);
		this.versionButton.setTranslateX(320.0D);
		this.versionButton.setTranslateY(80.0D);

		this.versionButton.setOnMouseClicked(event -> {
			this.version = MCVersion.values()[(this.version.ordinal() + 1) % MCVersion.values().length];
			this.updateVersion();
		});

		this.updateVersion();

		this.findSeedsButton = new Button("          Crack Seed          ");
		this.findSeedsButton.setScaleX(2.0D);
		this.findSeedsButton.setScaleY(2.0D);
		this.findSeedsButton.setTranslateX(320.0D);
		this.findSeedsButton.setTranslateY(150.0D);
		this.findSeedsButton.setDisable(true);

		this.findSeedsButton.setOnMouseClicked(event -> {
			if(!Bruteforce.instance.isShowing()) {
				Bruteforce.instance.show();
				try {
					Result result=Bruteforce.instance.init();
					if (result!=null){
						Bruteforce.instance.setResult(result);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void updateVersion() {
		this.versionButton.setText("          Version: " + this.version.toString() + "          ");
	}

	public void addToPane(StackPane pane) {
		pane.getChildren().addAll(this.versionButton, this.findSeedsButton);
	}

	public void update() {
		boolean flag = DungeonCracker.spawnerCoords.parse();
		flag &= DungeonCracker.floor.bits >= 32.0F;
		this.findSeedsButton.setDisable(!flag);
	}

}
