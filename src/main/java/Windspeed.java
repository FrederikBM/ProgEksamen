import processing.core.PApplet;

public class Windspeed extends PowerGeneration{

    Windspeed(int updateRate, String table, String columnP, String columnE, PApplet p){
        this.p=p;
        this.updateRate=updateRate;
        this.table=table;
        this.columnP=columnP;
        this.columnE=columnE;
    }
}
