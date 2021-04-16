import processing.core.PApplet;
import processing.core.PImage;

import java.sql.*;

public class Main extends PApplet{
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    private Connection connection = null;
    String test = "Blaest";
    PImage house1;
    PImage house2;
    PImage cloud;
    PImage regn1;
    PImage regn2;
    PImage generator1;
    PImage generator2;
    PImage windmill1;
    PImage windmill2;
    PImage vovse;
    boolean shift = true;
    Windspeed ws = new Windspeed(20, "Blaest", "Vindstyrke", "Energi", 160, 90, 90, 90 , this);
    Waterlevel wl = new Waterlevel(20, "Regn", "Vandstand", "Energi", 150, 450, 70, 50, this);
    LightsOut edison = new LightsOut(50,80,200,50,50, 500,350,this);


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

        house1 = loadImage("Huset1.png");
        house2 = loadImage("Huset2.png");
        cloud = loadImage("skyer.png");
        regn1 = loadImage("regn1.png");
        regn2 = loadImage("regn2.png");
        generator1 = loadImage("generator1.png");
        generator2 = loadImage("generator2.png");
        windmill1 = loadImage("vindmlle1.png");
        windmill2 = loadImage("vindmlle2.png");
        vovse = loadImage("vovse.png");

    }

    @Override
    public void draw(){
        clear();

        if(!edison.shiftButton) {
            image(house1, 0, 0);
            ws.energyProduction();
            wl.energyProduction();
        } else {
            image(house2,0,0);
        }

        wl.animation(generator1,generator2,250,475);
        ws.animation(windmill1,windmill2,250,435);
        edison.animation(vovse);
        animation(regn1,regn2);

        image(cloud,0,0);
    }

    @Override
    public void mouseReleased(){
        ws.click();
        wl.click();
        edison.click();
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