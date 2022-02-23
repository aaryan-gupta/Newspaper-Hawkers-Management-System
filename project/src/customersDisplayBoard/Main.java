package customersDisplayBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
				Parent root=FXMLLoader.load(getClass().getResource("CustomerDisplayBoard.fxml"));
				Scene scene = new Scene(root,600,600);
				primaryStage.setScene(scene);
				primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}