import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

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
        saveBtn.setOnAction(new onSave());
        loadBtn.setOnAction(new onLoad());
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
            estimatedGPALabel.setText("GPA: " + String.format("%.2f", estimatedGPA));
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

    private class onSave implements EventHandler<ActionEvent>{
        Alert alert;
        @Override
        public void handle(ActionEvent actionEvent) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Saving will overwrite anything previously saved. Proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.CANCEL){
                return;
            }

            //insert everything into database.
            Connection connection;
            try {
                connection = DatabaseManager.getConnection(); //for getting the connection
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Clear the table before inserting new data
            try (Statement clearStatement = connection.createStatement()) {
                clearStatement.executeUpdate("DELETE FROM COURSES");
            } catch (SQLException e) {
                throw new RuntimeException("Failed to clear the table", e);
            }
            PreparedStatement preparedStatement = null; //prepared statement object
            //insert valid inputs into DB
            for(int i = 0 ; i < inputList.size(); i++){
                if(inputList.get(i).verifyInput()){
                    String insertSQL = "INSERT INTO COURSES (COURSE_NAME, LETTER_GRADE, CREDITS) VALUES (?, ?, ?);"; //the insert query
                    try {
                        preparedStatement = connection.prepareStatement(insertSQL); //creating the prepared statement object
                        preparedStatement.setString(1, inputList.get(i).courseNameField.getText());
                        preparedStatement.setString(2, inputList.get(i).letterGradeCB.getValue());
                        preparedStatement.setString(3, inputList.get(i).numCreditsCb.getValue().toString());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //Extract fields from DB...
    private class onLoad implements EventHandler<ActionEvent>{
        //confirmation popup
        Alert alert;
        @Override
        public void handle(ActionEvent actionEvent) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Loading will clear any unsaved changes. Proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.CANCEL){
                return;
            }
            //clear arraylist
            inputList.clear();
            inputColumn.getChildren().clear();
            Connection connection = null; //for getting the connection
            try {
                connection = DatabaseManager.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            PreparedStatement preparedStatement = null; //prepared statement object
            ResultSet resultSet = null; //result set object
            String str = "";
            try{
                String readSQL = "SELECT * FROM COURSES;"; //the read query
                preparedStatement = connection.prepareStatement(readSQL); //initializing the prepared statement object
                resultSet = preparedStatement.executeQuery(); //executing the query
                while(resultSet.next()){ //iterating through the result set
                    //getting the values and making a string out of it
                    CourseInput ci = createBlock(MainPane.this,resultSet.getString("COURSE_NAME"),resultSet.getInt("CREDITS"),resultSet.getString("LETTER_GRADE"));
                    inputList.add(ci);
                    inputColumn.getChildren().add(ci);
                }
            }
            catch (SQLException err){
                err.printStackTrace();
            }
            finally { //closing the prepared statement
                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public CourseInput createBlock(MainPane mp, String cname, int numCreds, String letter){
        CourseInput cInput = new CourseInput(mp);
        cInput.letterGradeCB.setValue(letter);
        cInput.numCreditsCb.setValue(numCreds);
        cInput.courseNameField.setText(cname);
        return cInput;
    }


}
