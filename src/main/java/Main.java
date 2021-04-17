import processing.core.PApplet;
import processing.core.PImage;

import java.sql.*;

public class Main extends PApplet {
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
    boolean end=false;
    int endtime;
    boolean shift = true;
    Windspeed ws = new Windspeed(20, "Blaest", "Vindstyrke", "Energi", 160, 90, 90, 90, this);
    Waterlevel wl = new Waterlevel(20, "Regn", "Vandstand", "Energi", 150, 450, 70, 50, this);
    LightsOut edison = new LightsOut(60, 80, 200, 50, 50, 500, 350, this);


    public static void main(String[] args) {
        PApplet.main("Main");
    }


    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {
        //Her fylder vi bare de PImage-pladser som vi har gjordt plads til i hukommelsen
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
    public void draw() {
        //Her er det bare at vi lægger tingene ind, så programmet laver noget
        clear();
        //Kun hvis lyset er tændt skal maskinerne gå i stykker + animationer for huset og lamperne.
        if (!edison.shiftButton) {
            image(house1, 0, 0);
            ws.machineDeterioration();
            wl.machineDeterioration();
        } else {
            image(house2, 0, 0);
        }
        edison.energyConsumption("Blaest","Regn",ws.currentDataSelection,wl.currentDataSelection);
        animation(regn1, regn2);
        image(cloud, 0, 0);
        wl.animation(generator1, generator2, 250, 475);
        ws.animation(windmill1, windmill2, 250, 435);
        edison.animation(vovse);

        //Hvis hunden rammer huset, så slutter spillet, fordi man har tabt. Derefter har man muligheden for at starte forfra
        //Der er ikke som sådan en måde at vinde på, men det er bare om at få en personlig highscore. Lidt på samme måde som Flappy Bird
        if(edison.vovseXpos>220){
            text("Time Passed: " + frameCount/60,30,30);
        }
            else if(edison.vovseXpos<220){
                if(!end) {
                    end=true;
                    endtime = frameCount / 60;
                }
                if(end) {


                    fill(150, 0, 0);
                    rect(0, 0, 500, 500);
                    fill(255);
                    rect(200,225,100,50);
                    fill(0);
                    text("Time Passed: " + endtime,205,200);
                    text("Prøv igen",225,250);
                    if(mousePressed&&mouseX > 200 && mouseX < 200 + 100 && mouseY > 225 && mouseY < 225 + 50){
                        end=false;
                        edison.vovseXpos=500;
                        edison.currentEnergy=100;
                        edison.shiftButton=false;
                        ws.currentDataSelection=1;
                        wl.currentDataSelection=1;
                        frameCount=0;
                    }
                }
        }
    }

    @Override
    public void mouseReleased() {
        //Klik kommandoerne
        ws.click();
        wl.click();
        edison.click();
    }

    void animation(PImage PIa, PImage PIb) {
        //Regn animationerne
        int updateRate = 10;

        if (frameCount % updateRate == 0 && shift) {
            shift = false;
        } else if (frameCount % updateRate == 0 && !shift) {
            shift = true;
        }
        if (shift) {
            image(PIa, 0, -75);
        } else {
            image(PIb, 0, -75);
        }
    }
}