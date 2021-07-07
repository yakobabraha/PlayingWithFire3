package server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.Game.Spieler;

public class PlayerHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private JSONParser parser = new JSONParser();
    private HashMap<String, Game> Gamelist = new HashMap<String, Game>();
      
  
    // Constructor
    public PlayerHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    
    public Game createGame (String GameID) {
    	Game game = new Game(GameID);
    	
    	// Hash maps f�r schnelligkeit (O(1)), besser als array, da array O(n)
    	Gamelist.put(GameID, game);
    	return game;
    }
    
    public boolean joinGame (String GameID, String name, int x, int y) {
    	Game game = Gamelist.get(GameID);
    	
    	// Spiel erstellen falls keins gefunden wird
    	if(game == null) {
    		game = createGame(GameID);
    	}
    	
    	// Spieler tritt dem Spiel hinzu
    	if(game.joinGame(x, y, name)) return true;
    	
    	// Spiel ist schon voll
    	return false;
    }
    
    public void updatePlayer (String GameID, String name, int x, int y, int health) {
    	Game game = Gamelist.get(GameID);
    	Spieler spieler = game.getPlayer(name);
    	spieler.setData(x, y, health);
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
			String PlayerID = (String) parsedData.get("playerID");
			int x, y, leben;

			
			switch(instruction) {
			case "join":
				x = (int) parsedData.get("x");
				y = (int) parsedData.get("y");
				
				joinGame(GameID, PlayerID, x, y);
				break;
			case "update":
				x = (int) parsedData.get("x");
				y = (int) parsedData.get("y");
				leben = (int) parsedData.get("leben");
				
				updatePlayer(GameID, PlayerID, x, y, leben);
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
  
    @Override
    public void run() 
    {
        String received;
        String toreturn;
        String gameId;
        Game game;
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
                
                gameId = parseInstruction(received);
                game = Gamelist.get(gameId);
                toreturn = game.getGameInfo().toString();
                dos.writeUTF(toreturn);
                  
            } catch (IOException e) {
                e.printStackTrace();
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
