import processing.core.PApplet;
import processing.core.PImage;

public class Windspeed extends PowerGeneration{

    Windspeed(int updateRate, String table, String columnP, String columnE, int xpos, int ypos, int xlength, int ylength, PApplet p){
        super(updateRate, table, columnP, columnE, xpos, ypos, xlength, ylength, p);
        this.p=p;
        this.updateRate=updateRate;
        this.table=table;
        this.columnP=columnP;
        this.columnE=columnE;
        this.xpos=xpos;
        this.ypos=ypos;
        this.xlength=xlength;
        this.ylength=ylength;
    }
}
