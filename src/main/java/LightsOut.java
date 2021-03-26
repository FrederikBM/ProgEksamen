import processing.core.PApplet;
import processing.core.PImage;
import java.sql.*;

public class LightsOut {
    //tænd lyset: ingen risiko for død ved ulve. Der trækkes den normale 10 strøm i sekundet
    //sluk lyset: 10 sekunder før død. der trækkes nu kun 5 strøm fra i sekundet.
    PApplet p;
    int updateRate;
    int xpos;
    int ypos;
    int xlength;
    int ylength;

    public LightsOut(int updateRate, int xpos, int ypos, int xlength, int ylength) {
        this.p = p;
        this.updateRate = updateRate;
        this.xpos = xpos;
        this.ypos = ypos;
        this.xlength = xlength;
        this.ylength = ylength;
    }

    void click() {
        p.fill(255);
        p.ellipse(xpos,ypos,10,10);
        p.ellipse(xpos+xlength,ypos+ylength,10,10);
        if (p.mouseX > xpos && p.mouseX < xpos+xlength && p.mouseY > ypos && p.mouseY < ypos+ylength) {
            System.out.println("hi");
        }
    }
}
