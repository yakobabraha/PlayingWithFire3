package wolf.playingwithfire3.entities;

import java.awt.Graphics;

import wolf.playingwithfire3.gfx.AnimationPacket;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.states.SettingState;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class ComPlayer extends Player{
		
	private AnimationPacket animations;
	
	public ComPlayer(World world, int playerNumber, String skinName) {

		super(world.getSpawnX(playerNumber)*Tile.TILEWIDTH+SettingState.xOffset,world.getSpawnY(playerNumber)*Tile.TILEHEIGHT+SettingState.yOffset, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		// TODO Auto-generated constructor stub
		animations = new AnimationPacket(playerNumber, skinName);
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBombAmount() {
		return 0;
	}

	@Override
	public void setBombAmount(int bombAmount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		animations.tick();
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		graphics.drawImage(animations.getCurrentFrame(), (int) x, (int) y, null);
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
