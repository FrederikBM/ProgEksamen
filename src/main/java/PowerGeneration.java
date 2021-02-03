import processing.core.PApplet;
import processing.core.PImage;

import java.sql.*;
//strømgeneratoren laver 5 strøm i sekundet.
public class PowerGeneration extends PApplet {
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    PApplet p;
    private Connection connection = null;
    int updateRate;
    String table = "";
    String columnP = "";
    String columnE = "";

    public PowerGeneration(int updateRate, String table, String columnP, String columnE, PApplet p) {
        this.p = p;
        this.updateRate = updateRate;
        this.table = table;
        this.columnP = columnP;
        this.columnE = columnE;

        try {
            connection = DriverManager.getConnection(databaseURL);
            println("Connected to MS Access database. ");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    void connectDatabaseLogin() {


        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet wind = s.executeQuery("SELECT [ID], [Vindstyrke], [Energi] FROM " + table);
            //ResultSet studentList = s.executeQuery("SELECT [Bruger], [Karakter] FROM Logins WHERE Laerer=false");

            while (wind.next()) {
                String rsWindSpeed = wind.getString(1);
                System.out.println(rsWindSpeed);
            }

            /*if(){

            }*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void animation() {
        boolean shift = false;
        if (frameCount % updateRate == 0 && shift == false) {
            //image(regn1,0,0);
        }
    }



    void click() {
        if (mousePressed && mouseX < x1 && mouseX > x2 && mouseY < y1 && mouseY > y2) {

        }
    }
}

