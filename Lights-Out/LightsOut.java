import java.util.Random;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LightsOut extends Application {
	private static int[][] gameState;
	private static GridPane pane;
	private static String yellow = "-fx-background-color: #FFF300; -fx-background-radius: 10;";
	private static String black = "-fx-background-color: #000000; -fx-background-radius: 10;";

	public void start(Stage primaryStage) throws Exception {
		Text text = new Text("Please select a size: ");
		VBox box = new VBox();
		ToggleGroup group = new ToggleGroup();
		box.getChildren().add(text);

		for (int i = 3; i < 10; i++) {
			RadioButton buttons = new RadioButton(String.valueOf(i));
			buttons.setToggleGroup(group);
			box.getChildren().add(buttons);

			if (i == 5) {
				buttons.setSelected(true);
			}
		}

		Button button = new Button("Create Puzzle");
		button.setOnAction(e -> {
			try {
				RadioButton selected = (RadioButton) group.getSelectedToggle();
				createPuzzle(primaryStage, selected.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}	
		});

		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(button);

		// unshow title while minimized. only works when minimize button is clicked, or clicked in taskbar.
		// does not work if another window is clicked. 
		
		primaryStage.iconifiedProperty().addListener(e -> {
			if (e.toString().substring(e.toString().length() - 5, e.toString().length() - 1).equals("true")) {
				primaryStage.setTitle("Lights Out Size");
			} else {
				primaryStage.setTitle("");
			}
		});
		
		
		primaryStage.setScene(new Scene(box, box.getPrefWidth(), box.getPrefHeight()));
		primaryStage.show();
	}

	// flicks light || this method is a lot longer than it could be, but hey, it works
	public void flick(int row, int col) {
		ObservableList<Node> children = pane.getChildren();

		if (gameState[row][col] == 0) {
			gameState[row][col] = 1;		
			// right
			if (col + 1 < gameState[row].length) {
				if (gameState[row][col + 1] == 0) {
					gameState[row][col + 1] = 1;
				} else {
					gameState[row][col + 1] = 0;
				}
			}

			// left
			if (col - 1 >= 0) {
				if (gameState[row][col - 1] == 0) {
					gameState[row][col - 1] = 1;
				} else {
					gameState[row][col - 1] = 0;
				}
			}

			// up
			if (row - 1 >= 0) {
				if (gameState[row - 1][col] == 0) {
					gameState[row - 1][col] = 1;
				} else {
					gameState[row - 1][col] = 0;
				}
			}

			// down 
			if (row + 1 < gameState[col].length) {
				if (gameState[row + 1][col] == 0) {
					gameState[row + 1][col] = 1;
				} else {
					gameState[row + 1][col] = 0;
				}
			}		
		} else {
			gameState[row][col] = 0;

			if (col + 1 < gameState[row].length) {
				if (gameState[row][col + 1] == 0) {
					gameState[row][col + 1] = 1;
				} else {
					gameState[row][col + 1] = 0;
				}
			}

			// left
			if (col - 1 >= 0) {
				if (gameState[row][col - 1] == 0) {
					gameState[row][col - 1] = 1;
				} else {
					gameState[row][col - 1] = 0;
				}
			}

			// up
			if (row - 1 >= 0) {
				if (gameState[row - 1][col] == 0) {
					gameState[row - 1][col] = 1;
				} else {
					gameState[row - 1][col] = 0;
				}
			}

			// down 
			if (row + 1 < gameState[col].length) {
				if (gameState[row + 1][col] == 0) {
					gameState[row + 1][col] = 1;
				} else {
					gameState[row + 1][col] = 0;
				}
			}
		}

		for (Node child : children) {
			if (gameState[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)] == 0) {
				child.setStyle(black);
			} else {
				child.setStyle(yellow);
			}
		}
	}

	public void randomize() {
		Random random = new Random();
		for (int i = 0; i < gameState.length; i++) {
			for (int j = 0; j < gameState.length; j++) {
				boolean isFlick = random.nextBoolean();
				if (isFlick) {
					flick(i, j);
				}
			}
		}
	}

	public void chase() {
		for (int i = 0; i < gameState.length; i++) {
			for (int j = 0; j < gameState.length; j++) {
				if (i - 1 >= 0) {
					if (gameState[i - 1][j] == 1) {
						flick(i, j);
						chase();
					}
				}
			}
		}
	}

	public void createPuzzle(Stage primaryStage, String radioSelected) throws Exception {
		BorderPane borderPane = new BorderPane();
		int gridSize = Integer.parseInt(radioSelected);
		HBox hBox = new HBox();

		pane = new GridPane();
		pane.setStyle("-fx-background-color: #373737");
		pane.setPadding(new Insets(1, 1, 1, 1));
		pane.setHgap(3);
		pane.setVgap(3);
		pane.setAlignment(Pos.CENTER);

		int[][] gameState = new int[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				Button button = new Button();
				button.setMinWidth(50);
				button.setMinHeight(50);
				button.setStyle(black);

				final int newI = i;
				final int newJ = j;
				button.setOnAction(e -> {
					flick(newI, newJ);
				});

				pane.add(button, j, i);
				GridPane.setHalignment(button, HPos.RIGHT);
				GridPane.setValignment(button, VPos.CENTER);

				gameState[i][j] = 0;
			}
		}
		LightsOut.gameState = gameState;
		randomize();

		Button random = new Button("Randomize");
		random.setOnAction(e -> {
			randomize();
		});
		
		Button chase = new Button("Chase Lights");
		chase.setOnAction(e -> {
			chase();
		});
		
		hBox.getChildren().addAll(random, chase);
		hBox.setSpacing(20);
		hBox.setAlignment(Pos.CENTER);

		borderPane.setBottom(hBox);
		borderPane.setCenter(pane);

		borderPane.setMinWidth(250);

		Scene scene = new Scene(borderPane, borderPane.getPrefWidth(), borderPane.getPrefHeight());

		primaryStage.setTitle("Lights Out");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
