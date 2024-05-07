import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainPane extends VBox {
    private Label title = new Label("Marina's GPA Calculator");
    public ArrayList<CourseInput> inputList = new ArrayList<>();
    private Button addCourseBtn = new Button("Add Course");
    private VBox inputColumn = new VBox();

    public MainPane() {
        addCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Remove CourseInput instance from its parent
                CourseInput course = new CourseInput(MainPane.this); // Pass reference to MainPane
                inputColumn.getChildren().add(course);
                inputList.add(course);
            }
        });
        getChildren().addAll(title, inputColumn, addCourseBtn);
    }

    public void removeCourse(CourseInput courseInput) {
        inputColumn.getChildren().remove(courseInput);
        inputList.remove(courseInput);
    }
}
