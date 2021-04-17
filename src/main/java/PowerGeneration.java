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
    boolean shiftButton = false;
    int currentDataSelection = 1;
    int deteriorationValue = 1;
    int repairValue = 1;
    float energyLevel = 0;
    String energyLevelString="";
    String risc;
    String energyProd;


    PowerGeneration(int updateRate, String table, String columnP, String columnE,
                    int xpos, int ypos, int xlength, int ylength, PApplet p) {
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


    void energyProduction(){
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet rsRisc = s.executeQuery("SELECT [Risiko], [Energi] FROM " + table + " WHERE [Risiko] = " +currentDataSelection);

            while (rsRisc.next()) {
                risc = rsRisc.getString(1);
                energyProd = rsRisc.getString(2);

                if(p.frameCount%updateRate==0&&deteriorationValue<40){
                    currentDataSelection+=deteriorationValue;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void click() {

                if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength&&currentDataSelection!=1) {
                    currentDataSelection-=repairValue;
                    energyProduction();
                    System.out.println("clicked");
                }

        p.fill(255);
        p.ellipse(xpos,ypos,10,10);
        p.ellipse(xpos+xlength,ypos+ylength,10,10);

    }

    void animation(PImage PIa, PImage PIb, int textX, int textY) {
        float riscFloat = Float.parseFloat(risc);
        float energyProdFloat = Float.parseFloat(energyProd);

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
        p.fill(riscFloat*6,energyProdFloat*40,0);
        p.text(table+" is currently at "+risc+"/40.0 risc",textX,textY);
        p.text(table+" is currently generating " +energyProd+" per tick", textX,textY+20);
    }
}