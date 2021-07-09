package wolf.playingwithfire3.worlds;

// Leon, Armin, Alex, Armin

import java.awt.Graphics;
import java.util.Random;

import wolf.playingwithfire3.online.Client;
import wolf.playingwithfire3.states.SettingState;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.utils.Utils;

public class World {

	private int width, height;
	private int spawnX1, spawnY1,spawnX2,spawnY2,spawnX3, spawnY3,spawnX4, spawnY4;
	private int[][] tiles;
	private Random random;
	private Client client;
	

	public World(String path) {
		loadWorld(path);
		random = new Random();
		
	}
	
	public void tick() {
	}
	
	public void render(Graphics graphics) {
		for(int y = 0;y<height;y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(graphics, x * Tile.TILEWIDTH+SettingState.xOffset, y * Tile.TILEHEIGHT+SettingState.yOffset);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x<0|| y<0||x>=width||y>=height)
			return Tile.floorTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t==null) {
			return Tile.floorTile;
		}
		return t;
	}
	
	public void destroyBox(int x, int y) {
		if(random.nextBoolean()) {
			tiles[x][y] = 0;
			return;
		}
		float bruh=random.nextFloat();
		if(bruh<=0.33) {
			tiles[x][y] = 3;
		}
		else if(bruh<=0.66) {
			tiles[x][y] = 4;
		}
		else {
			tiles[x][y] = 5;
		}
		if(client!=null)
			client.addPowerUp(x, y, tiles[x][y]);
	}
	
	public void setTile(int x, int y, int id) {
		tiles[x][y]= id;
	}
	
	public int getSpawnX1() {
		return spawnX1;
	}

	public int getSpawnY1() {
		return spawnY1;
	}

	public int getSpawnX2() {
		return spawnX2;
	}

	public int getSpawnY2() {
		return spawnY2;
	}

	public int getSpawnX3() {
		return spawnX3;
	}

	public int getSpawnY3() {
		return spawnY3;
	}

	public int getSpawnX4() {
		return spawnX4;
	}

	public int getSpawnY4() {
		return spawnY4;
	}
	public int getTileID(int x, int y) {
		return tiles[x][y];
	}
	
	public int getSpawnX(int playerNumber) {
		switch(playerNumber) {
		case 1:
			return spawnX1;
		case 2:
			return spawnX2;
		case 3:
			return spawnX3;
		case 4:
			return spawnX4;
		default:
			return spawnX1;		
		}
	}
	
	public int getSpawnY(int playerNumber) {
		switch(playerNumber) {
		case 1:
			return spawnY1;
		case 2:
			return spawnY2;
		case 3:
			return spawnY3;
		case 4:
			return spawnY4;
		default:
			return spawnY1;
		}
	}

	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		
		spawnX1 = Utils.parseInt(tokens[2]);
		spawnY1 = Utils.parseInt(tokens[3]);
		spawnX2 = Utils.parseInt(tokens[4]);
		spawnY2 = Utils.parseInt(tokens[5]);
		spawnX3 = Utils.parseInt(tokens[6]);
		spawnY3 = Utils.parseInt(tokens[7]);
		spawnX4 = Utils.parseInt(tokens[8]);
		spawnY4 = Utils.parseInt(tokens[9]);
		
		tiles = new int[width] [height];
		for(int y = 0;y<height;y++) {
			for(int x = 0; x < width;x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x+y* width)+10]);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getHeight() {
		return height;
	}


	
}
