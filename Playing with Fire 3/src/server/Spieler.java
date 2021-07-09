package server;

// Alex

import org.json.simple.JSONObject;

public class Spieler {
    private int x, y, health, animationIndex, spielerIndex;
    private String spielerID, skinPaket, worldName, bombs, powerups;

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
        health = 3;
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
        return health;
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
    
    public void setHealth(int health_){
        health = health_;
    }
    
    public void setBomben(String bomben) {
    	bombs = bomben;
    }
    
    public boolean setData(int x_, int y_, int health_, int aniIndex, String ausrichtung_, String bomben, String powerups_){
        x = x_;
        y = y_;
        health = health_;
        animationIndex = aniIndex;
        ausrichtung = AUSRICHTUNG.valueOf(ausrichtung_);
        bombs = bomben;
        powerups = powerups_;
        return true;
    }
    
    public JSONObject getSpielerDaten() {
        JSONObject daten = new JSONObject();

        daten.put("ID", spielerID);
        daten.put("x", x);
        daten.put("y", y);
        daten.put("health", health);
        daten.put("ausrichtung", ausrichtung.toString());
        daten.put("animationenIndex", animationIndex);
        daten.put("skinPaket", skinPaket);
        daten.put("spielerIndex", spielerIndex);
        daten.put("worldName", worldName);
        
        if(bombs == null) bombs = new JSONObject().toString();
        daten.put("bomben", bombs);
        daten.put("powerups", powerups);
        
        return daten;
    }
}

