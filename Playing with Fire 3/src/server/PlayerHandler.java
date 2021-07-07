package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
    public PlayerHandler(Socket s, HashMap<String, Game> gamelist,  DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        Gamelist = gamelist;
    }
    
    public Game createGame (String GameID) {
    	Game game = new Game(GameID);
    	
    	// Hash maps f�r schnelligkeit (O(1)), besser als array, da array O(n)
    	Gamelist.put(GameID, game);
    	return game;
    }
    
    public boolean joinGame (String GameID, String name, int x, int y, String skin, String worldName) {
    	Game game = Gamelist.get(GameID);
    	
    	// Spiel erstellen falls keins gefunden wird
    	if(game == null) {
    		game = createGame(GameID);
    	}
    	
    	// Spieler tritt dem Spiel hinzu
    	if(game.joinGame(x, y, name, skin, worldName)) {
    		return true;	
    	}
    	// Spiel ist schon voll
    	return false;
    }
    
    public void updatePlayer (String GameID, String name, int x, int y, int health, int aniIndex, String ausrichtung, String bomben) {
    	Game game = Gamelist.get(GameID);
    	Spieler spieler = game.getPlayer(name);
    	spieler.setData(x, y, health, aniIndex, ausrichtung, bomben);
    }
    
    public boolean removePlayer (String GameID, String name) {
    	Game game = Gamelist.get(GameID);
    	return game.removePlayer(name);
    }
    
    public String parseInstruction (String data) {
    	try {
			JSONObject parsedData = (JSONObject) parser.parse(data);
			String instruction = (String) parsedData.get("instruction");
			String GameID = (String) parsedData.get("gameID");
			String PlayerID = (String) parsedData.get("ID");
			int x, y, leben, aniIndex;
			String ausrichtung, skin, worldName, bomben;

			switch(instruction) {
			case "join":
				x = toIntExact((long) parsedData.get("x"));
				y = toIntExact((long) parsedData.get("y"));
				skin = (String) parsedData.get("skin");
				worldName = "world_2";
				
				if(!joinGame(GameID, PlayerID, x, y, skin, worldName)) {
					try {
						dos.writeUTF("Player " + PlayerID + " already joined!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			case "update":
				x = toIntExact((long) parsedData.get("x"));
				y = toIntExact((long) parsedData.get("y"));
				leben = toIntExact((long) parsedData.get("health"));
				aniIndex = toIntExact((long) parsedData.get("animationIndex"));
				ausrichtung = (String) parsedData.get("ausrichtung");
				bomben = (String) parsedData.get("bomben");
				
				updatePlayer(GameID, PlayerID, x, y, leben, aniIndex, ausrichtung, bomben);
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
			//e.printStackTrace();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} 
    }
    
    public void startInformationThread(String gameId) {
    	String toreturn;
    	Game game = Gamelist.get(gameId);
    	while (true) {
    		toreturn = game.getGameInfo();
    		if(game.checkIfFull() && !startedGame) {
    			startGame();
    			startedGame = true;
    		}
    		
    		try {
				dos.writeUTF(toreturn);
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (IOException e) {
			} catch (InterruptedException e) {
			}
    	}
    }
  
    @Override
    public void run() 
    {
        String received;
        String toreturn;
        Game game;
        boolean started = false;
        while (true) 
        {
            try {
                // receive the answer from client
                received = dis.readUTF();
                if(received.equals("Exit"))
                { 
                	this.s.close();
                	break;
                }
                
                final String gameId = parseInstruction(received);
                
                if(!started) {                	
                	new Thread(new Runnable() {
						@Override
						public void run() {
							startInformationThread(gameId);
						}
					}).start();
                	started = true;
                }
            } catch (IOException e) {
            }
        }
          
        try
        {
            // verhindern von memory leaks // ressourcen leaks wie zu viele offene ports
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
