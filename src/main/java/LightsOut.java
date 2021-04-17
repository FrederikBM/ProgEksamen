import processing.core.PApplet;
import processing.core.PImage;

import java.nio.channels.Pipe;
import java.sql.*;

public class LightsOut {
    private final String databaseURL = "jdbc:ucanaccess://src//main//resources//Database.accdb";
    PApplet p;
    private Connection connection = null;
    int updateRate;
    int xpos = 0;
    int ypos = 0;
    int xlength = 0;
    int ylength = 0;
    int vovseXpos;
    int vovseYpos;
    public boolean shiftButton = false;
    int currentEnergy = 100;
    int energyConsumptionPerTick = 10;

    LightsOut(int updateRate, int xpos, int ypos, int xlength, int ylength, int vovseXpos, int vovseYpos, PApplet p){
        this.p = p;
        this.updateRate = updateRate;
        this.xpos = xpos;
        this.ypos = ypos;
        this.xlength = xlength;
        this.ylength = ylength;
        this.vovseXpos=vovseXpos;
        this.vovseYpos=vovseYpos;

        try {
            connection = DriverManager.getConnection(databaseURL);
            p.println("Connected to MS Access database. ");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    void energyConsumption(String table1, String table2,int currentDataSelection1,int currentDataSelection2){
        if(p.frameCount%updateRate==0&&!shiftButton&&currentEnergy>0){
            currentEnergy-=energyConsumptionPerTick;
        } else if(p.frameCount%updateRate==0&&!shiftButton&&currentEnergy<=0){
            currentEnergy=0;
            shiftButton=true;
        }

        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet rsRisc = s.executeQuery("SELECT [Energi] FROM " + table1 + " WHERE [Risiko] = " +currentDataSelection1);

            while (rsRisc.next()) {
                float energyProduced = Float.parseFloat(rsRisc.getString(1));

                if(p.frameCount%updateRate==0&&currentEnergy<100){
                    currentEnergy+=energyProduced;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            s = connection.createStatement();
            ResultSet rsRisc = s.executeQuery("SELECT [Energi] FROM " + table2 + " WHERE [Risiko] = " +currentDataSelection2);

            while (rsRisc.next()) {
                float energyProduced = Float.parseFloat(rsRisc.getString(1));

                if(p.frameCount%updateRate==0&&currentEnergy<100){
                    currentEnergy+=energyProduced;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    void click() {



                if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength&&!shiftButton) {

                    shiftButton =true;
                    System.out.println("clicked");
                } else if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength&& shiftButton){

                    shiftButton =false;
                    System.out.println("clicked");
                }

        p.fill(255);
        p.ellipse(xpos,ypos,10,10);
        p.ellipse(xpos+xlength,ypos+ylength,10,10);


    }

    void animation(PImage PIc){
        p.image(PIc,vovseXpos,vovseYpos);

        if(!shiftButton){
            if(p.frameCount % updateRate == 0){
                if(vovseXpos<=500){
                    vovseXpos+=5;
                }
            }
        } else {
            if(p.frameCount % updateRate == 0){
                    vovseXpos -= 15;
                System.out.println(vovseXpos);
            }
        }
        if(vovseXpos<=220){
            p.fill(255,0,0);
            p.rect(0,0,500,500);
        }
        p.fill(255);
        p.text("you house currently has "+ currentEnergy + "/100 energy",270,30);
    }
}

