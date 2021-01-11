import processing.core.PApplet;
import processing.core.PVector;
import processing.data.StringList;

import java.sql.*;

public class Main extends PApplet{
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    private Connection connection = null;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public Main() {
        try {
            connection = DriverManager.getConnection(databaseURL);
            println("Connected to MS Access database. ");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup(){

    }



}
