package neil.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import neil.dungeons.Result;
import neil.dungeons.VersionCrack;
import neil.dungeons.kaptainwutax.magic.RandomSeed;

import java.util.ArrayList;
import java.util.List;

import static neil.gui.DungeonCracker.generate;
import static neil.gui.MCVersion.v1_13;


public class Bruteforce extends Stage {

	public static Bruteforce instance = new Bruteforce();
	private final StackPane pane;
	private Result result = null;
	private String sequence = "";

	private Bruteforce() {
		this.pane = new StackPane();
		this.setTitle("Dungeon Cracker | Bruteforce");
		this.setResizable(false);

		Scene scene = new Scene(pane, 1280, 720);
		this.setScene(scene);
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Result init() throws InterruptedException {
		for (int x = 0; x < DungeonCracker.floor.floorPattern.length; x++) {
			for (int z = 0; z < DungeonCracker.floor.floorPattern[x].length; z++) {
				ImageView image = DungeonCracker.floor.floorPattern[x][z];

				if (image.getImage() == Floor.COBBLE) {
					this.sequence += "0";
				} else if (image.getImage() == Floor.MOSSY) {
					this.sequence += "1";
				} else if (image.getImage() == Floor.UNKNOWN) {
					this.sequence += "2";
				}
			}
		}

		int x = DungeonCracker.spawnerCoords.x;
		int y = DungeonCracker.spawnerCoords.y;
		int z = DungeonCracker.spawnerCoords.z;

		Label sequenceLabel = new Label("Floor Sequence: " + this.sequence);
		sequenceLabel.setFont(Font.font(16.0D));
		//sequenceLabel.setTranslateX(-200.0D);
		sequenceLabel.setTranslateY(-320.0D);

		Label localPosLabel = new Label(String.format("Local Position: (%d, %d, %d)", x & 15, y, z & 15));
		localPosLabel.setFont(Font.font(16.0D));
		//localPosLabel.setTranslateX(-500.0D);
		localPosLabel.setTranslateY(-300.0D);

		Label chunkPosLabel = new Label(String.format("Chunk Position: (%d, %d)", x >> 4, z >> 4));
		chunkPosLabel.setFont(Font.font(16.0D));
		//chunkPosLabel.setTranslateX(-500.0D);
		chunkPosLabel.setTranslateY(-280.0D);

		this.pane.getChildren().addAll(sequenceLabel, localPosLabel, chunkPosLabel);

		TextArea outputLog = new TextArea();
		outputLog.setScaleX(0.75D);
		outputLog.setScaleY(0.75D);
		outputLog.setTranslateY(40.0D);
		outputLog.setFont(Font.font(25.0D));
		this.pane.getChildren().add(outputLog);

		MCVersion version = generate.version;
		Run kernel = new Run(x, y, z, sequence, version);
		Thread thread = new Thread(kernel);
		thread.start();
		outputLog.setText("Starting the cracking, this might take a while");
		if (version.compareTo(MCVersion.v1_14) < 0) {
			outputLog.setText("You need a second dungeon you can start inputting it now");
		}
		thread.join();
		Result result = kernel.getResult();
		System.out.println(version);
		if (version.isNewerThan(MCVersion.v1_14)) {
			outputLog.setText("Your seed is one of these: " + result.getWorldSeeds().toString());
			return null;
		}
		if (instance.result != null) {
			for (Long structureSeed : result.getStructureSeeds()) {
				if (instance.result.getStructureSeeds().contains(structureSeed)) {
					System.out.println("Structure seed found " + structureSeed);
					List<Long> ws = new ArrayList<>();
					for (long upperBits = 0; upperBits < (1L << 16); upperBits++) {
						long worldSeed = (upperBits << 48) | structureSeed;
						if (!RandomSeed.isRandomSeed(worldSeed)) continue;
						System.out.format("\t With nextLong() worldseed equivalent %d.\n", worldSeed);
						ws.add(worldSeed);
					}
					outputLog.setText("Structure seed found " + structureSeed + "\n With randomly generated world seed equivalents: " + ws);
					break;
				}
			}
		} else {
			outputLog.setText("You can now start cracking the second one ");
		}
		return result;

	}

	public static class Run implements Runnable {
		private volatile Result result;
		private final int x;
		private final int y;
		private final int z;
		private final String sequence;
		private final MCVersion version;

		public Run(int x, int y, int z, String sequence, MCVersion version) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.sequence = sequence;
			this.version = version;
		}

		@Override
		public void run() {
			VersionCrack versionCrack = new VersionCrack(version, x, y, z, sequence);
			//result = versionCrack.run();
            new VersionCrack(version, x, y, z, sequence).runTest();
		}

		public Result getResult() {
			return result;
		}
	}

}
