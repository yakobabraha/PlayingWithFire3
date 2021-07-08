package wolf.playingwithfire3.entities;

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

	
	public OnlinePlayer(float x, float y, int width, int height,int playerNumber ,String skinName, World world, Client client) {
		super(world.getSpawnX(playerNumber)*Tile.TILEWIDTH+SettingState.xOffset,world.getSpawnY(playerNumber)*Tile.TILEHEIGHT+SettingState.yOffset, 45, 45);
		// TODO Auto-generated constructor stub
		this.client = client;

		animations = new AnimationPacket(playerNumber,skinName);
		health = 3;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 3;
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
		graphics.drawImage(animations.getCurrentFrame(), (int) x, (int) y, null);
		if(health != 0) {
			
		}
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
