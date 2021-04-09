import processing.core.PApplet;
import processing.core.PImage;

import java.sql.*;

//strømgeneratoren laver 5 strøm i sekundet.
public class PowerGeneration {
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    PApplet p;
    private Connection connection = null;
    int updateRate;
    String table = "";
    String columnP = "";
    String columnE = "";
    int xpos = 0;
    int ypos = 0;
    int xlength = 0;
    int ylength = 0;
    boolean shift = true;

    PowerGeneration(int updateRate, String table, String columnP, String columnE, int xpos, int ypos, int xlength, int ylength, PApplet p) {
        this.p = p;
        this.updateRate = updateRate;
        this.table = table;
        this.columnP = columnP;
        this.columnE = columnE;
        this.xpos = xpos;
        this.ypos = ypos;
        this.xlength = xlength;
        this.ylength = ylength;

        try {
            connection = DriverManager.getConnection(databaseURL);
            p.println("Connected to MS Access database. ");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    void connectDatabaseLogin() {


        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet generating = s.executeQuery("SELECT [ID], [Risiko], [Energi] FROM " + table);

            while (generating.next()) {
                String rsRisiko = generating.getString(1);
                System.out.println(rsRisiko);
            }

            /*if(){

            }*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void animation(PImage PIa, PImage PIb) {
        if (p.frameCount % updateRate == 0&& shift) {
            shift=false;
        } else if(p.frameCount % updateRate == 0&& !shift){
            shift=true;
        }
        if(shift){
            p.image(PIa,xpos,ypos);
        } else {
            p.image(PIb,xpos,ypos);
        }
    }



    void click() {

        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet generating = s.executeQuery("SELECT [ID], [Risiko], [Energi] FROM " + table);

            while (generating.next()) {
                String rsRisiko = generating.getString(1);
                System.out.println(rsRisiko);

                if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength) {

                    System.out.println("clicked");
                }


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        p.fill(255);
        p.ellipse(xpos,ypos,10,10);
        p.ellipse(xpos+xlength,ypos+ylength,10,10);


    }
}