import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
        //Establishing a connection to the database
        DatabaseManager.getConnection();
        //creating a table in the database
        DBTableCreator.createTableCourses();

        MainPane test = new MainPane();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);
        root.getChildren().add(test);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
