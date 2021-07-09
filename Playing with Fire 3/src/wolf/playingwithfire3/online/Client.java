package wolf.playingwithfire3.online;

// Alex & Yakob

import static java.lang.Math.toIntExact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import wolf.playingwithfire3.entities.Player;
import wolf.playingwithfire3.states.QueueState;
import wolf.playingwithfire3.utils.Utils;

public class Client {
	private Socket client = null;
	private JSONParser parser = new JSONParser();
	private int x=1, y=1, health=3, animationenIndex=0, spielerIndex;
	private String playerID, gameID, powerupsRaw, ausrichtung="down", skinPaket="default", instruction = "join",ownPlayerID;
	
	private QueueState queueState;
	private JSONObject[] powerups = new JSONObject[4];
	private JSONObject bombs = new JSONObject();
	
	private Player[] players;
	private boolean started;

	
	public Client(QueueState queueState) {
		playerID = Utils.generateRandomString(20);
		ownPlayerID = playerID;
		this.queueState = queueState;
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
	
	public void addPowerUp(int x_, int y_, String type) {
		JSONObject data = new JSONObject();
		data.put("x", x_);
		data.put("y", y_);
		data.put("type", type);
		
		for(int i = 0; i < powerups.length; i++) {
			if(powerups[i] != null) {
				powerups[i] = data;
			}
		}
	}

	public void setBomb(int x_, int y_, long l) {
		bombs.put("timestamp", l);
		bombs.put("x", x_);
		bombs.put("y", y_);
	}

	public String getOwnPlayerId() {
		return ownPlayerID;
	}
	
	public Socket getClient() {
		return client;
	}
	
    public JSONObject setPlayer() {
        JSONObject player = new JSONObject();

        player.put("ID", ownPlayerID);
        player.put("gameID", "yarro");
        player.put("x", x);
        player.put("y", y);
        player.put("health", health);
        player.put("instruction", instruction);
        player.put("skin", skinPaket);
        player.put("ausrichtung", ausrichtung);
        player.put("animationIndex", animationenIndex);
        player.put("bomben", bombs.toString());
        player.put("powerups", Arrays.toString(powerups));
        
        return player;
    }
    
    public void gameStarting10s() {
    	System.out.println("Game is starting in 10 seconds");
    	queueState.setLastSeconds(true);
    }
    
    public void gameStart() {
    	System.out.println("Game is starting!!!");
    	players = queueState.startGame();
    	instruction = "update";
    	started = true;
    }
    
    public JSONObject parsePowerups(String rawData) {
    	try {
			JSONArray parsedData = (JSONArray) parser.parse(rawData);
			
			for(int i = 0; i < parsedData.size(); i++) {
				if(parsedData.get(i) != null) {
					// die einzelnen Powerups verarbeiten;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public void parseBomb(JSONObject bomb) {
    	long bombx = (long) bomb.get("x");
    	long bomby = (long) bomb.get("y");
    	long bombts = (long) bomb.get("timestamp");
    	if(bombx != 0) System.out.println(bomb.get("x"));
    	if(bombx != 0) System.out.println(bomb.get("y"));
    	if(bombx != 0) System.out.println(bomb.get("timestamp"));
    	
    	if(bombs.toString().equals(bomb.toString())) bombs = new JSONObject();
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
    	JSONObject bomb;
		try {
			if(jsonObject.get("bomben") == null) bomb = new JSONObject();
			else { bomb = (JSONObject) parser.parse((String) jsonObject.get("bomben"));}
			
			if(bomb.get("x") != null) parseBomb(bomb);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	
    	if(!started ) {
    		queueState.joinPlayer(x, y, health, playerID, skinPaket, spielerIndex);
    	}else {
    		if(players[spielerIndex-1].isOnlinePlayer()) {
	    		players[spielerIndex-1].setX(x);
	    		players[spielerIndex-1].setY(y);
	    		players[spielerIndex-1].setAnimIndex(animationenIndex);
	    		players[spielerIndex-1].setDirection(ausrichtung);
	    		players[spielerIndex-1].setHealth(health);
    		}
    	}
    }
    
    public void startListener () {
    	DataInputStream input = null;
    	String data = "";
        while (true) 
        {
            try {
            	input = new DataInputStream(client.getInputStream());
            	try {
            		data = input.readUTF().toString();
            		
            		if(data.equals("Game is full, starting in 10s")) gameStarting10s();
            		else if(data.equals("Game is starting..")) gameStart();
            		else {            			
            			JSONArray parsedData = (JSONArray) parser.parse(data);
            			
            			for(int i = 0; i < parsedData.size(); i++) {
            				if(parsedData.get(i) != null) {
            					parsePlayer((JSONObject) parsedData.get(i));
            				}
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
    
	public void initClient() {
		try {
			client = new Socket("127.0.0.1", 1445);
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
	
}
