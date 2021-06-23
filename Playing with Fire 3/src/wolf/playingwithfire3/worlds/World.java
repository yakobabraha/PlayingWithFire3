package wolf.playingwithfire3.worlds;

import java.awt.Graphics;

import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.utils.Utils;

public class World {

	private int width, height;
	private int spawnX1, spawnY1,spawnX2,spawnY2,spawnX3, spawnY3,spawnX4, spawnY4;
	private int[][] tiles;
	

	public World(String path) {
		loadWorld(path);
		
	}
	
	public void tick() {
	}
	
	public void render(Graphics g) {
		for(int y = 0;y<height;y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT);
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
}
