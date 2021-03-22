import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.StringList;

import java.sql.*;

public class Main extends PApplet{
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    private Connection connection = null;
    String test = "Blaest";
    PImage house;
    PImage regn1;
    PImage regn2;
    Windspeed ws = new Windspeed(100, "Blaest", "Vindstyrke", "Energi", 180, 100, 50, 50 , this);
    Waterlevel wl = new Waterlevel(100, "Regn", "Vandstand", "Energi", 150, 450, 50, 50, this);


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
        house = loadImage("Huset.jpg");
        regn1 = loadImage("regn1.png");
        regn2 = loadImage("regn2.png");
        image(house, 0, 0);
    }

    @Override
    public void draw(){
        ws.animation(regn1,regn2);
    }

    @Override
    public void mouseReleased(){
        ws.click();
        wl.click();
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
