import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Chomp extends Application {
	static final String PATH = "http://cs.gettysburg.edu/~tneller/cs112/chomp/images/";
	static final Image COOKIE_IMAGE = new Image(PATH + "cookie.png");
	static final Image SKULL_IMAGE = new Image(PATH + "cookie-skull.png"); 
	static final Image BLACK_IMAGE = new Image(PATH + "black.png"); 
	static final int GRID_LENGTH = 9;
	static int[][] isCookie = new int[GRID_LENGTH][GRID_LENGTH];
	static boolean isGame = false;
	static GridPane pane = new GridPane();

	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(pane, pane.getPrefHeight(), pane.getPrefWidth());

		for (int i = 0; i < GRID_LENGTH; i++) {	
			for (int j = 0; j < GRID_LENGTH; j++) {
				Button button = new Button();
				pane.add(button, j, i);
				
				final int newI = i;
				final int newJ = j;
				button.setOnAction(e -> {
					if (isCookie[newI][newJ] == 0 && !isGame) {
						for (int k = 0; k <= newI; k++) {
							for (int l = 0; l <= newJ; l++) {
								isCookie[k][l] = 1;
								isCookie[0][0] = -1;
								isGame = true;
								cookie();
							}
						}
					} else if (isCookie[newI][newJ] == 1 && isGame) {
						for (int k = GRID_LENGTH - 1; k >= newI; k--) {
							for (int l = GRID_LENGTH - 1; l >= newJ; l--) {
								isCookie[k][l] = 0;
								cookie();
							}
						}
					} else if (isCookie[newI][newJ] == -1 && isGame) {
						isCookie[newI][newJ] = 0; 
						
						for (int k = GRID_LENGTH - 1; k >= newI; k--) {
							for (int l = GRID_LENGTH - 1; l >= newJ; l--) {
								isCookie[k][l] = 0;
							}
						}
						
						isGame = false;
						cookie();
					}
				});
			}
		}

		cookie();
		pane.setPadding(new Insets(1, 1, 1, 1));
		pane.setHgap(1);
		pane.setVgap(1);
		pane.setAlignment(Pos.CENTER);

		primaryStage.setTitle("Chomp!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void cookie() {
		ObservableList<Node> children = pane.getChildren();

		for (Node child : children) {
			if (isCookie[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)] == 0) {
				child.setStyle("-fx-background-image: url(" + PATH + "black.png);" + "-fx-min-height: 50px; -fx-min-width: 50px;");
			} else if (isCookie[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)] == -1) {
				child.setStyle("-fx-background-image: url(" + PATH + "cookie-skull.png);" + "-fx-min-height: 50px; -fx-min-width: 50px;");
			} else {
				child.setStyle("-fx-background-image: url(" + PATH + "cookie.png);" + "-fx-min-height: 50px; -fx-min-width: 50px;");
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
