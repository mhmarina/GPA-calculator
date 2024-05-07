import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainPane test = new MainPane();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);
        root.getChildren().add(test);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
