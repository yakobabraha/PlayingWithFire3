package server.Server;

import org.json.simple.JSONObject;

import server.Game.Spieler;

public class Game{
    private String GameID;
    private Spieler []Spielerliste = new Spieler[4];

    public Game(String id){
    	GameID = id;
    }

    public String getGameID() {
    	return GameID;
    }
    
    public boolean distributePlayer(Spieler spieler){
        boolean placed = false;
        for(int i = 0; i < Spielerliste.length; i++){
            if(Spielerliste[i] == null){
                Spielerliste[i] = spieler;
                placed = true;
                break;
            }
        }
        return placed;
    }
    
    public Spieler[] getSpielerliste() {
    	return Spielerliste;
    }
    
    public boolean removePlayer(String spielerID) {
        for(int i = 0; i < Spielerliste.length; i++){
            if(Spielerliste[i].getSpielerID().equals(spielerID)){
                Spielerliste[i] = null;
                return true;
            }
        }

        return false;
    }

    public Spieler getPlayer(String spielerID){
        for(int i = 0; i < Spielerliste.length; i++){
            if(Spielerliste[i].getSpielerID().equals(spielerID)){
                return Spielerliste[i];
            }
        }

        return null;
    }

    public boolean joinGame(int x, int y, String spieler_){
        Spieler spieler = new Spieler(x, y, spieler_);

        return distributePlayer(spieler);
    }

    public boolean leaveGame(){
    	return true;
    }
    
    public JSONObject[] getGameInfo() {
    	JSONObject []game = new JSONObject[4];
    	
        for(int i = 0; i < Spielerliste.length; i++){
        	JSONObject SpielerDaten = Spielerliste[i].getSpielerDaten();
        	game[i] = SpielerDaten;
        }
    	
    	return game;
    }
}