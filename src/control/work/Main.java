package control.work;

import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("window.fxml")));
        Scene mainScene = new Scene(root);
        primaryStage.setTitle("Программа-тест");
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        }
}
