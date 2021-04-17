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
    int currentDataSelection = 1;
    int deteriorationValue = 1;
    int repairValue = 1;
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
            //Laver en forbindelse til databasen
            connection = DriverManager.getConnection(databaseURL);
            p.println("Connected to MS Access database. ");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    void machineDeterioration() {
        Statement s = null;
        //Her får vi risikoværdien og energi-værdien, som vi så skriver på skærmen længere ned i koden
        //Derudover er det også her man langsomt ødelægger generatorne
        try {
            s = connection.createStatement();
            ResultSet rsRisc = s.executeQuery("SELECT [Risiko], [Energi] FROM " + table + " WHERE [Risiko] = " + currentDataSelection);

            while (rsRisc.next()) {
                risc = rsRisc.getString(1);
                energyProd = rsRisc.getString(2);

                //Her bliver vindmøllen og generatoren langsomt ødelagt
                if (p.frameCount % updateRate == 0 && deteriorationValue < 40) {
                    currentDataSelection += deteriorationValue;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void click() {
        //Her kan man klikke på maskinerne for at reperere dem en smule.
        //Vi kalder machineDeterioration så at den opdaterer korrekt, selvom lyset er slukket
        if (p.mouseX > xpos && p.mouseX < xpos + xlength && p.mouseY > ypos && p.mouseY < ypos + ylength && currentDataSelection != 1) {
            currentDataSelection -= repairValue;
            machineDeterioration();
            System.out.println("clicked");
        }
    }

    void animation(PImage PIa, PImage PIb, int textX, int textY) {
        //Her er animationerne for maskinerne og for teksten der viser hvor ødelagte de er, og hvor meget energi de producerer

        float riscFloat = Float.parseFloat(risc);
        float energyProdFloat = Float.parseFloat(energyProd);

        if (p.frameCount % updateRate == 0 && shift) {
            shift = false;
        } else if (p.frameCount % updateRate == 0 && !shift) {
            shift = true;
        }
        if (shift) {
            p.image(PIa, xpos, ypos);
        } else {
            p.image(PIb, xpos, ypos);
        }
        p.fill(riscFloat * 6, energyProdFloat * 40, 0);
        p.text(table + " is currently at " + risc + "/40.0 risc", textX, textY);
        p.text(table + " is currently generating " + energyProd + " per tick", textX, textY + 20);
    }
}