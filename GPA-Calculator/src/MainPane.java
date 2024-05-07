import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import java.util.ArrayList;

public class MainPane extends VBox {
    private Label title = new Label("Marina's GPA Calculator");
    public ArrayList<CourseInput> inputList = new ArrayList<>();
    private Button addCourseBtn = new Button("Add Course");
    private Button calculateButton = new Button("Calculate");
    private Button loadBtn = new Button("Load");
    private Button saveBtn = new Button("Save");
    private VBox inputColumn = new VBox();
    private HBox body = new HBox();
    private VBox buttonsPane = new VBox();
    public double estimatedGPA = 0.0;
    public String estimatedLetterGrade;
    private Label estimatedGPALabel = new Label("GPA: 0.0");
    private Label estimatedLetterLabel = new Label("F");

    public MainPane() {
        title.setFont(Font.font("Arial",FontWeight.BOLD, 25));
        addCourseBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Remove CourseInput instance from its parent
                CourseInput course = new CourseInput(MainPane.this); // Pass reference to MainPane
                inputColumn.getChildren().add(course);
                inputList.add(course);
            }
        });
        inputColumn.setSpacing(10);
        inputColumn.setPadding(new Insets(10));
        inputColumn.setAlignment(Pos.CENTER);
        ScrollPane sp = new ScrollPane();
        sp.setContent(inputColumn);
        sp.setFitToWidth(true);

        buttonsPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth() * 0.5);
        buttonsPane.setSpacing(5);
        buttonsPane.getChildren().addAll(addCourseBtn, calculateButton, estimatedGPALabel, estimatedLetterLabel, saveBtn, loadBtn);
        estimatedGPALabel.setFont(Font.font("Arial",FontWeight.BOLD, 25));
        estimatedLetterLabel.setFont(Font.font("Arial",FontWeight.BOLD, 25));

        sp.setPrefWidth(Screen.getPrimary().getBounds().getWidth() * 0.75);
        HBox.setHgrow(sp, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(buttonsPane, javafx.scene.layout.Priority.ALWAYS);
        body.setSpacing(15);
        body.getChildren().addAll(sp, buttonsPane);

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);
        getChildren().addAll(title, body);
    }

    public void removeCourse(CourseInput courseInput) {
        inputColumn.getChildren().remove(courseInput);
        inputList.remove(courseInput);
    }
}
