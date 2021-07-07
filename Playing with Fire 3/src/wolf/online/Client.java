package wolf.online;

import java.io.OutputStream;

import static java.lang.Math.toIntExact;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.TimeUnit;

public class Client {
	private Socket client = null;
	private JSONParser parser = new JSONParser();
	
	public Client(Socket clientconn) {
		client = clientconn;
	}
	
	public Socket getClient() {
		return client;
	}
	
    public JSONObject setPlayer() {
        JSONObject player = new JSONObject();

        player.put("ID", "yarro1");
        player.put("gameID", "yarrosgame");
        player.put("x", 1);
        player.put("y", 2);
        player.put("health", 100);
        player.put("instruction", "join");
        player.put("skin", "default");
        
        return player;
    }
    
    public JSONObject setPlayer2() {
        JSONObject player = new JSONObject();

        player.put("ID", "yarro2");
        player.put("gameID", "yarrosgame");
        player.put("x", 40);
        player.put("y", 20);
        player.put("health", 100);
        player.put("instruction", "join");
        player.put("skin", "default");
        player.put("animationIndex", 1);
        
        return player;
    }
    
    public JSONObject setPlayer3() {
        JSONObject player = new JSONObject();

        player.put("ID", "yarro3");
        player.put("gameID", "yarrosgame");
        player.put("x", 40);
        player.put("y", 20);
        player.put("health", 100);
        player.put("instruction", "join");
        player.put("skin", "default");
        player.put("animationIndex", 1);
        
        return player;
    }
    
    public JSONObject setPlayer4() {
        JSONObject player = new JSONObject();

        player.put("ID", "yarro4");
        player.put("gameID", "yarrosgame");
        player.put("x", 40);
        player.put("y", 20);
        player.put("health", 100);
        player.put("instruction", "join");
        player.put("skin", "default");
        player.put("animationIndex", 1);
        
        return player;
    }
    
    public void parsePlayer(JSONObject jsonObject) {
    	String playerID, skin, ausrichtung;
    	int x, y, health, aniIndex, spielerIndex;
    	
    	playerID = (String) jsonObject.get("ID");
    	skin = (String) jsonObject.get("skinPaket");
    	ausrichtung = (String) jsonObject.get("ausrichtung");
    	
    	x = toIntExact((long) jsonObject.get("x"));
    	y = toIntExact((long) jsonObject.get("y"));
    	health = toIntExact((long) jsonObject.get("health"));
    	aniIndex = toIntExact((long) jsonObject.get("animationenIndex"));
    	spielerIndex = toIntExact((long) jsonObject.get("spielerIndex"));
    	
    	
    }
    
    public void startListener () {
    	DataInputStream input = null;
        while (true) 
        {
            try {
            	input = new DataInputStream(client.getInputStream());
            	try {
					JSONArray parsedData = (JSONArray) parser.parse(input.readUTF().toString());
									
					for(int i = 0; i < parsedData.size(); i++) {
						if(parsedData.get(i) != null) {
							parsePlayer((JSONObject) parsedData.get(i));
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } catch (IOException e) {
            }
        }
    }
    
    public void sendPlayerInfos (JSONObject player) {
    	DataOutputStream output = null;
		try {
			output = new DataOutputStream(client.getOutputStream());
			output.writeUTF(player.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    public static void main(String[] args)
    {
        try
        {
            Socket client = new Socket("127.0.0.1", 1445);
            final Client client_ = new Client(client);
            

            client_.sendPlayerInfos(client_.setPlayer());

            new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("Starting event listener thread");
					client_.startListener();
				}
			}).start();
            
			TimeUnit.SECONDS.sleep(2);
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
