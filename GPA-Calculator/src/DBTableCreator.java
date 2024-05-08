/* This class makes use of the DatabaseManager.java to establish connection
 * to the H2 database and create the tables in the database.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTableCreator {

    public static void createTableCourses() throws SQLException {

        //creating connection object
        Connection connection = DatabaseManager.getConnection();
        Statement statement = null; //creating statement object

        try{
            statement = connection.createStatement(); //initializing statement object
            // making the CREATE command to create the tables in the database
            String createTableSQL = "CREATE TABLE IF NOT EXISTS COURSES (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "COURSE_NAME VARCHAR(255), " +
                    "LETTER_GRADE VARCHAR(2), " +
                    "CREDITS INT);";
            statement.executeUpdate(createTableSQL); //executing the CREATE command
            System.out.println("Table created successfully");
        }
        catch(SQLException err){ //catching any SQL exception
            err.printStackTrace();
        }
        finally{ //closing the statement object
            try{
                if(statement != null){
                    statement.close();//closing the statement object
                }
            }
            catch(SQLException err){//catching any SQL exception
                err.printStackTrace();
            }
        }
    }
}