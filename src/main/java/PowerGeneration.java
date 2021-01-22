import processing.core.PApplet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PowerGeneration extends PApplet {
    PApplet p;
    private Connection connection = null;
    int updateRate;
    String table = "";
    String columnP = "";
    String columnE = "";

    PowerGeneration(int updateRate, String table, String columnP, String columnE, PApplet p){
        this.p=p;
        this.updateRate=updateRate;
        this.table=table;
        this.columnP=columnP;
        this.columnE=columnE;
    }

    void connectDatabaseLogin() {
        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet wind = s.executeQuery("SELECT [ID], [Vindstyrke] FROM '"+table+"'");
            //ResultSet studentList = s.executeQuery("SELECT [Bruger], [Karakter] FROM Logins WHERE Laerer=false");

            while (wind.next()) {
                String rsWindSpeed = wind.getString(1);
            }

            if(){

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void animation(){
        if(frameCount%10==0){

        }
    }
}

