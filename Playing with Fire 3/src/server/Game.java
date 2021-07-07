package server;

import java.util.Arrays;

import org.json.simple.JSONObject;

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
            	spieler.setSpielerIndex(i + 1);
                Spielerliste[i] = spieler;
                placed = true;
                break;
            }
        }
        return placed;
    }
    
    public boolean checkIfFull() {
    	for(int i = 0; i < Spielerliste.length; i++){
            if(Spielerliste[i] == null){
                return false;
            }
        }
    	return true;
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
            if(Spielerliste[i] != null && Spielerliste[i].getSpielerID().equals(spielerID)){
                return Spielerliste[i];
            }
        }

        return null;
    }

    public boolean joinGame(int x, int y, String spieler_, String skinPaket, String worldName){
        Spieler spieler = new Spieler(x, y, spieler_, skinPaket, worldName);
        Spieler exist = getPlayer(spieler_);
        if(exist != null) return false;
        return distributePlayer(spieler);
    }
    
    public String getGameInfo() {
    	JSONObject []game = new JSONObject[4];

        for(int i = 0; i < Spielerliste.length; i++){
        	JSONObject SpielerDaten = null;
        	if(Spielerliste[i] != null) {
        		 SpielerDaten = Spielerliste[i].getSpielerDaten();
        		 Spielerliste[i].setBomben(null);
        	}
        	game[i] = SpielerDaten;
        }
    	
    	return Arrays.toString(game);
    }
}