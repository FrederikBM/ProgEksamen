import com.sun.scenario.effect.impl.prism.ps.PPSInvertMaskPeer;
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
    boolean shiftButton = false;
    int currentEnergy = 100;

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


    void click() {
        int runs = 0;

        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet stroem = s.executeQuery("SELECT [ID], [CEnergy] FROM Stroem");

            while (stroem.next()) {
                String rsStroem = stroem.getString(2);
                //System.out.println(rsRisiko);

                if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength&&!shiftButton) {
                    if(runs>0){
                        break;
                    } else {
                        runs++;
                    }
                    shiftButton =true;
                    System.out.println("clicked");
                } else if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength&& shiftButton){
                    if(runs>0){
                        break;
                    } else {
                        runs++;
                    }
                    shiftButton =false;
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

    void animation(PImage PIa, PImage PIb, PImage PIc){
        p.image(PIc,vovseXpos,vovseYpos);

        if(!shiftButton){
            //p.image(PIa,xpos,ypos);
            if(p.frameCount % updateRate == 0){
                currentEnergy-=25;
                System.out.println("Eyy");
                if(currentEnergy<=75){
                    vovseXpos-=20;
                    System.out.println("Eyyx2");
                }
            }
        } else {
            //p.image(PIb,xpos,ypos);
            p.fill(0);
            p.rect(xpos,ypos,50,50);
        }
    }
}

