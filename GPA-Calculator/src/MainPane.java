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
import javafx.scene.paint.Color;
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
    private Label errorText = new Label("");

    public MainPane() {
        errorText.setTextFill(Color.RED);
        calculateButton.setOnAction(new onCalculate());

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
        buttonsPane.getChildren().addAll(addCourseBtn, calculateButton, estimatedGPALabel, estimatedLetterLabel, saveBtn, loadBtn, errorText);
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

    private class onCalculate implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(inputList.isEmpty()){
                errorText.setText("Course list is empty.");
                return;
            }
            //do GPA Calculation
            int totalCredits = 0;
            double totalGPA = 0.0;
            double letterGradeVal = 0.0;
            for(int i = 0; i < inputList.size(); i++){
                if(inputList.get(i).verifyInput()){
                    totalCredits += inputList.get(i).numCreditsCb.getValue();
                    switch(inputList.get(i).letterGradeCB.getValue()) {
                        case "A+":
                            letterGradeVal = 4.33;
                            break;
                        case "A":
                            letterGradeVal = 4.0;
                            break;
                        case "A-":
                            letterGradeVal = 3.67;
                            break;
                        case "B+":
                            letterGradeVal = 3.33;
                            break;
                        case "B":
                            letterGradeVal = 3.0;
                            break;
                        case "B-":
                            letterGradeVal = 2.67;
                            break;
                        case "C+":
                            letterGradeVal = 2.33;
                            break;
                        case "C":
                            letterGradeVal = 2.0;
                            break;
                        case "C-":
                            letterGradeVal = 1.67;
                            break;
                        case "D":
                            letterGradeVal = 1.0;
                            break;
                        case "E":
                            letterGradeVal = 0.0;
                            break;
                    }
                    totalGPA += inputList.get(i).numCreditsCb.getValue() * letterGradeVal;
                }
                else{
                    errorText.setText("Error in input.");
                    return;
                }
            }
            estimatedGPA = totalGPA / totalCredits ;
            estimatedGPALabel.setText("GPA: " + estimatedGPA);
            if(estimatedGPA >= 4.33){
                estimatedLetterGrade = "A+";
            }
            else if(estimatedGPA < 4.33 && estimatedGPA >= 4.00){
                estimatedLetterGrade = "A+";
            }
            else if(estimatedGPA < 4.0 && estimatedGPA >= 3.67){
                estimatedLetterGrade = "A-";
            }
            else if(estimatedGPA < 3.67 && estimatedGPA >= 3.33){
                estimatedLetterGrade = "B+";
            }
            else if(estimatedGPA < 3.33 && estimatedGPA >= 3.0){
                estimatedLetterGrade = "B";
            }
            else if(estimatedGPA < 3.0 && estimatedGPA >= 2.67){
                estimatedLetterGrade = "B-";
            }
            else if(estimatedGPA < 2.67 && estimatedGPA >= 2.33){
                estimatedLetterGrade = "C+";
            }
            else if(estimatedGPA < 2.33 && estimatedGPA >= 2.0){
                estimatedLetterGrade = "C";
            }
            else if(estimatedGPA < 2.0 && estimatedGPA >= 1.67){
                estimatedLetterGrade = "C-";
            }
            else if(estimatedGPA < 1.67 && estimatedGPA >= 1.0){
                estimatedLetterGrade = "D";
            }
            else if(estimatedGPA < 1.0){
                estimatedLetterGrade = "E";
            }
            estimatedLetterLabel.setText(estimatedLetterGrade);
            errorText.setText("");
        }
    }

}
