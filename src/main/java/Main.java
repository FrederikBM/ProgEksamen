import processing.core.PApplet;
import processing.core.PVector;
import processing.data.StringList;

import java.sql.*;

public class Main extends PApplet{
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    private Connection connection = null;
    String test = "Blaest";
    Windspeed ws = new Windspeed(10, "Blaest", "Vindstyrke", "Energi", this);
    Waterlevel wl = new Waterlevel(10, "Regn", "Vandstand", "Energi", this);

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
        ws.connectDatabaseLogin();

    }

    @Override
    public void draw(){

    }

    void connectDatabaseLogin() {
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet wind = s.executeQuery("SELECT [ID], [Vindstyrke] FROM Blaest");
            //ResultSet studentList = s.executeQuery("SELECT [Bruger], [Karakter] FROM Logins WHERE Laerer=false");

            while (wind.next()) {
                String rsWindSpeed = wind.getString(1);
                System.out.println(rsWindSpeed);
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
