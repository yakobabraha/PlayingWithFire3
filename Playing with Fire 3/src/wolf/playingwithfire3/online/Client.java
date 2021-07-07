package wolf.playingwithfire3.online;

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
import wolf.playingwithfire3.utils.*;

import java.util.concurrent.TimeUnit;

public class Client {
	private Socket client = null;
	private JSONParser parser = new JSONParser();
	private int x=1, y=1, health=3, animationenIndex=0, spielerIndex;
	private String playerID, gameID, ausrichtung="down", skinPaket="default", instruction = "join";

	
	public Client() {
		playerID = Utils.generateRandomString(20);
		initClient();
	}
	
	public void setX(int x_) {
		x = x_;
	}
	
	public void setY(int y_) {
		y = y_;
	}
	
	public void setHealth(int leben) {
		health = leben;
	}
	
	public void setAnimationenIndex(int index) {
		animationenIndex = index;
	}
	
	public void setSpielerIndex(int index) {
		spielerIndex = index;
	}
	
	public void setAusrichtung(String ausrichtung_) {
		ausrichtung = ausrichtung_;
	}
	
	public void setSkinpaket(String skinPaket_) {
		skinPaket = skinPaket_;
	}
	
	public void setInstruction(String instruction_) {
		instruction = instruction_;
	}
	
	public void initClient() {
		try {
			client = new Socket("127.0.0.1", 1445);
			//sendPlayerInfos(setPlayer());
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("Starting event listener thread");
					startListener();
				}
			}).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getClient() {
		return client;
	}
	
    public JSONObject setPlayer() {
        JSONObject player = new JSONObject();

        player.put("ID", "yarro1");
        player.put("gameID", playerID);
        player.put("x", x);
        player.put("y", y);
        player.put("health", health);
        player.put("instruction", "join");
        player.put("skin", skinPaket);
        player.put("ausrichtung", ausrichtung);
        player.put("animationIndex", animationenIndex);
        
        return player;
    }
    
    public void parsePlayer(JSONObject jsonObject) {    	
    	playerID = (String) jsonObject.get("ID");
    	skinPaket = (String) jsonObject.get("skinPaket");
    	ausrichtung = (String) jsonObject.get("ausrichtung");
    	
    	x = toIntExact((long) jsonObject.get("x"));
    	y = toIntExact((long) jsonObject.get("y"));
    	health = toIntExact((long) jsonObject.get("health"));
    	animationenIndex = toIntExact((long) jsonObject.get("animationenIndex"));
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
    
    public void sendPlayerInfos () {
    	DataOutputStream output = null;
		try {
			output = new DataOutputStream(client.getOutputStream());
			output.writeUTF(setPlayer().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
