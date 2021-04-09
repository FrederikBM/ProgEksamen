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
    PImage cloud;
    PImage regn1;
    PImage regn2;
    PImage generator1;
    PImage generator2;
    PImage windmill1;
    PImage windmill2;
    boolean shift = true;
    Windspeed ws = new Windspeed(50, "Blaest", "Vindstyrke", "Energi", 160, 90, 50, 50 , this);
    Waterlevel wl = new Waterlevel(50, "Regn", "Vandstand", "Energi", 150, 450, 50, 50, this);


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

        house = loadImage("Huset.png");
        cloud = loadImage("skyer.png");
        regn1 = loadImage("regn1.png");
        regn2 = loadImage("regn2.png");
        generator1 = loadImage("generator1.png");
        generator2 = loadImage("generator2.png");
        windmill1 = loadImage("vindmlle1.png");
        windmill2 = loadImage("vindmlle2.png");

    }

    @Override
    public void draw(){
        clear();
        image(house, 0, 0);
        ws.connectDatabaseLogin();
        wl.connectDatabaseLogin();
        wl.animation(generator1,generator2);
        ws.animation(windmill1,windmill2);
        animation(regn1,regn2);
        image(cloud,0,0);
    }

    @Override
    public void mouseReleased(){
        ws.click();
        wl.click();
    }

    void animation(PImage PIa, PImage PIb) {
        int updateRate = 10;

        if (frameCount % updateRate == 0&& shift) {
            shift=false;
        } else if(frameCount % updateRate == 0&& !shift){
            shift=true;
        }
        if(shift){
            image(PIa,0,-75);
        } else {
            image(PIb,0,-75);
        }
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