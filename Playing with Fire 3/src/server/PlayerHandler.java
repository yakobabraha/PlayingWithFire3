package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import static java.lang.Math.toIntExact;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.concurrent.TimeUnit;

public class PlayerHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private JSONParser parser = new JSONParser();
    private HashMap<String, Game> Gamelist;
    private boolean startedGame = false;
  
    // Constructor
    public PlayerHandler(Socket s, HashMap<String, Game> gamelist, DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        Gamelist = gamelist;
    }
    
    public Game createGame (String GameID) {
    	Game game = new Game(GameID);
  
    	/* Wir verwenden eine HashMap als Datenstruktur, da diese eine Zugriffszeit von O(1) hat */
    	Gamelist.put(GameID, game);
    	return game;
    }
    
    public boolean joinGame (String GameID, String name, int x, int y, String skin, String worldName) {
    	Game game = Gamelist.get(GameID);
    	
    	/* Spiel erstellen falls keins gefunden wird */
    	if(game == null) game = createGame(GameID);
    	
    	
    	/* Spieler tritt dem Spiel hinzu */
    	if(game.joinGame(x, y, name, skin, worldName)) return true;	
    	
    	/* Spiel ist schon voll */
    	return false;
    }
    
    public void updatePlayer (String GameID, String name, int x, int y, int health, int aniIndex, String ausrichtung, String bomben, String powerups) {
    	Game game = Gamelist.get(GameID);
    	Spieler spieler = game.getPlayer(name);
    	spieler.setData(x, y, health, aniIndex, ausrichtung, bomben, powerups);
    }
    
    public void playerAlreadyJoined(String PlayerID) {
		try {
			dos.writeUTF("Player " + PlayerID + " already joined!");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public boolean removePlayer (String GameID, String name) {
    	Game game = Gamelist.get(GameID);
    	return game.removePlayer(name);
    }
    
    public void preGameJoin(String GameID, String PlayerID, JSONObject parsedData) {
    	int x,y;
    	String skin, worldName;
    	x = toIntExact((long) parsedData.get("x"));
		y = toIntExact((long) parsedData.get("y"));
		skin = (String) parsedData.get("skin");
		worldName = "world_2";
		
    	if(!joinGame(GameID, PlayerID, x, y, skin, worldName)) playerAlreadyJoined(PlayerID);
    }
    
    public void preUpdatePlayer(String GameID, String PlayerID, JSONObject parsedData) {
    	int x, y, leben, aniIndex;
    	String ausrichtung, bomben, powerups;
    	
		x = toIntExact((long) parsedData.get("x"));
		y = toIntExact((long) parsedData.get("y"));
		leben = toIntExact((long) parsedData.get("health"));
		aniIndex = toIntExact((long) parsedData.get("animationIndex"));
		ausrichtung = (String) parsedData.get("ausrichtung");
		bomben = (String) parsedData.get("bomben");
		powerups = (String) parsedData.get("powerups");
    	
    	updatePlayer(GameID, PlayerID, x, y, leben, aniIndex, ausrichtung, bomben, powerups);
    }
    
    public String parseInstruction (String data) {
    	try {
    		/*
    		 * Im Grunde ist jede Nachricht von Client side gleich aufgebaut.
    		 * Diese Beruht auf einer Instruction. Es gibt join, update und leave
    		 * 
    		*/
    		
			JSONObject parsedData = (JSONObject) parser.parse(data);
			String Instruction = (String) parsedData.get("instruction");
			String GameID = (String) parsedData.get("gameID");
			String PlayerID = (String) parsedData.get("ID");

			switch(Instruction) {
			case "join":
				preGameJoin(GameID, PlayerID, parsedData);
				break;
			case "update":
				preUpdatePlayer(GameID, PlayerID, parsedData);
				break;
			case "leave":
				removePlayer(GameID, PlayerID);
				break;
			default:
				break;
			}
			
			return GameID;
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return null;
    	
    }
    
    public void startGame() {
    	try {
			dos.writeUTF("Game is full, starting in 10s");
			TimeUnit.SECONDS.sleep(10);
			dos.writeUTF("Game is starting..");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
    
    public void startInformationThread(String gameId) {
    	String toreturn;
    	Game game = Gamelist.get(gameId);
    	while (true) {
    		
    		/*
    		 * Falls die Spielerliste voll ist, wird das Spiel gestartet.
    		 * dazu gibt es einen 10 Sekunden cooldown.
    		 * 
    		 * Jede 20te Millisekunde wird der Status aller Spieler in einem Spiel
    		 * jedem Ingame Spieler gesendet. Dadurch wird eine 50Fps erreicht
    		 * 
    		 */
    		
    		if(!startedGame && game.checkIfFull()) {
    			startGame();
    			startedGame = true;
    		}
    		
    		toreturn = game.getGameInfo();
    		try {
				dos.writeUTF(toreturn);
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (IOException e) {
			} catch (InterruptedException e) {
			}
    	}
    }
  
    public boolean startThread(final String gameID) {
    	new Thread(new Runnable() {
			@Override
			public void run() {
				startInformationThread(gameID);
			}
		}).start();
    	return true;
    }
    
    @Override
    public void run() 
    {
        String received, gameID;
        boolean started = false;
        
        while (true) 
        {
            try {
            	
            	/* readUTF formatiert den Stream Input direkt in einen lesbaren String
            	 * 
            	 * Die Nachricht "Exit" ist eine Abbruchbedingung für den Loop
            	 * 
            	 * startThread startet einen Thread, der die den output stream zu der Client Verbindung nutzt,
            	 * und die Spieler daten aktualisiert .
            	*/
            	
            	
                received = dis.readUTF();
                if(received.equals("Exit")){ 
                	this.s.close();
                	break;
                }
            
                gameID = parseInstruction(received);
                if(!started) started = startThread(gameID);
                
            } catch (IOException e) {
            }
        }
          
        try
        {
            /* verhindern von memory leaks & ressourcen leaks, wie zu viele offene ports */
        	
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
