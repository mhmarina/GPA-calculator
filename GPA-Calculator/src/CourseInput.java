import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


public class CourseInput extends HBox {
    public TextField courseNameField = new TextField();
    public ComboBox<Integer> numCreditsCb = new ComboBox<>();
    public ComboBox<String> letterGradeCB = new ComboBox<>();
    public String[] letterGradesArray = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "E"};
    public int[] numCreditsArray = {1,2,3,4,5,6,7,8,9};
    Button destroyBtn = new Button("Remove");
    MainPane mainPane;

    public CourseInput(MainPane mp){
        mainPane = mp;
        courseNameField.setPromptText("CSE110");
        for (String s : letterGradesArray) {
            letterGradeCB.getItems().add(s);
        }
        for (int i : numCreditsArray){
            numCreditsCb.getItems().add(i);
        }
        destroyBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Remove CourseInput instance from its parent
                mainPane.removeCourse(CourseInput.this);            }
        });

        setSpacing(10);
        getChildren().addAll(courseNameField, numCreditsCb, letterGradeCB, destroyBtn);
    }

    public boolean verifyInput() {
        //verify that a grade and credit num is selected
        if (numCreditsCb.getSelectionModel().getSelectedIndex() == -1) {
            return false;
        }
        if (letterGradeCB.getSelectionModel().getSelectedIndex() == -1) {
            return false;
        }
        return true;
    }
}
