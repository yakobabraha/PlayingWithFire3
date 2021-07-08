package server;

import org.json.simple.JSONObject;

public class Spieler {
    private int x, y, leben, animationIndex, spielerIndex;
    private String spielerID, skinPaket, worldName, gesetzteBomben, powerups;

    private enum AUSRICHTUNG {
    	up,
    	down,
    	left,
    	right
    }
    private AUSRICHTUNG ausrichtung;
    
    public Spieler(int initX, int initY, String spielerID_, String skinPaket_, String worldName_){
        x = initX;
        y = initY;
        leben = 3;
        spielerID = spielerID_;
        skinPaket = skinPaket_;
        ausrichtung = AUSRICHTUNG.down;
        animationIndex = 0;
        worldName = worldName_;
    }
    
    public boolean setSpielerIndex(int index) {
    	spielerIndex = index;
    	return true;
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

    public void setXPosition(int x_){
        x = x_;
    }

    public void setYPosition(int y_){
        y = y_;
    }
    
    public void setPowerups(String powerups_) {
    	powerups = powerups_;
    }
    
    public void setLeben(int leben_){
        leben = leben_;
    }
    
    public void setBomben(String bomben) {
    	gesetzteBomben = bomben;
    }
    
    public boolean setData(int x_, int y_, int leben_, int aniIndex, String ausrichtung_, String bomben, String powerups_){
        x = x_;
        y = y_;
        leben = leben_;
        animationIndex = aniIndex;
        ausrichtung = AUSRICHTUNG.valueOf(ausrichtung_);
        gesetzteBomben = bomben;
        powerups = powerups_;
        return true;
    }
    
    public JSONObject getSpielerDaten() {
        JSONObject daten = new JSONObject();

        daten.put("ID", spielerID);
        daten.put("x", x);
        daten.put("y", y);
        daten.put("health", leben);
        daten.put("ausrichtung", ausrichtung.toString());
        daten.put("animationenIndex", animationIndex);
        daten.put("skinPaket", skinPaket);
        daten.put("spielerIndex", spielerIndex);
        daten.put("worldName", worldName);
        daten.put("bomben", gesetzteBomben);
        daten.put("powerups", powerups);
        
        return daten;
    }
}

