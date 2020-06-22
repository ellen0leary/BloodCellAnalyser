package Other;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage ps;

    @Override
    public void start(Stage primaryStage) throws Exception{
        ps = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
        primaryStage.setTitle("Blood Cell Analyser");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
