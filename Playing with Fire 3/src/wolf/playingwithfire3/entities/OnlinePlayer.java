package wolf.playingwithfire3.entities;

// Yakob & Armin

import java.awt.Graphics;

import wolf.playingwithfire3.gfx.AnimationPacket;
import wolf.playingwithfire3.online.Client;
import wolf.playingwithfire3.states.SettingState;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class OnlinePlayer extends Player{

	protected int health;
	private AnimationPacket animations;
	private World word;
	private Client client;
	private BombsManager bombsManager;
	private int playerNumber;

	
	public OnlinePlayer(float x, float y, int width, int height,int playerNumber ,String skinName, World world, Client client, BombsManager bombsManager) {
		super(world.getSpawnX(playerNumber)*Tile.TILEWIDTH+SettingState.xOffset,world.getSpawnY(playerNumber)*Tile.TILEHEIGHT+SettingState.yOffset, 45, 45);
		// TODO Auto-generated constructor stub
		this.client = client;
		this.bombsManager = bombsManager;
		this.playerNumber = playerNumber;
		animations = new AnimationPacket(playerNumber,skinName);
		health = 3;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public int getBombAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setDirection(String direction) {
		animations.setDirection(direction);
	}
	
	public void setAnimIndex(int i) {
		animations.setAnimIndex(i);
	}

	@Override
	public void setBombAmount(int bombAmount) {
		// TODO Auto-generated method stub
		
	}

	public void setX(float  value) {
		x = value;
	}
	
	public void setY(float value) {
		y = value;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		
		if(health != 0) {
			graphics.drawImage(animations.getCurrentFrame(), (int) x, (int) y, null);
		}
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return playerNumber;
	}

	@Override
	public void setHealth(int h) {
		// TODO Auto-generated method stub
		health = h;
	}

	@Override
	public boolean isOnlinePlayer() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setBomb(int x, int y, int range) {
		// TODO Auto-generated method stub
		bombsManager.addBomb(x, y, this, range,true);
	}

}
