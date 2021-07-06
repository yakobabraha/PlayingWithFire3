package server.Game;

import org.json.simple.JSONObject;

public class Spieler {
    private int x, y, leben;
    private String spielerID;

    public Spieler(int initX, int initY, String spielerID_){
        x = initX;
        y = initY;
        leben = 100;
        spielerID = spielerID_;
    }

    public String getSpielerID(){
        return spielerID;
    }

    public boolean checkPosition(){
        return true;
    }

    public int getXPosition(){
        return x;
    }

    public int getYPosition(){
        return y;
    }

    public int getLeben(){
        return leben;
    }

    public boolean setXPosition(int x_){
        x = x_;
        return true;
    }

    public boolean setYPosition(int y_){
        y = y_;
        return true;
    }

    public boolean setLeben(int leben_){
        leben = leben_;
        return true;
    }
    
    public boolean setData(int x_, int y_, int leben_){
        x = x_;
        y = y_;
        leben = leben_;
        return true;
    }
    
    public JSONObject getSpielerDaten() {
        JSONObject daten = new JSONObject();

        daten.put("ID", spielerID);
        daten.put("x", x);
        daten.put("y", y);
        daten.put("health", leben);

        return daten;
    }
}

