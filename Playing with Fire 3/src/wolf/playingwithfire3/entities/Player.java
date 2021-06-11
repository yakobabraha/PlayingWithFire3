package wolf.playingwithfire3.entities;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;

public class Player extends Entity{

	private int health;
	private Game game;
	
	public Player(Game game, float x, float y) {
		super(x, y);
		this.game = game;
		health = 3;
	}
	
	public void tick() {
		//test Movement
		if(game.getKeyManager().up)
			y-=5;
		if(game.getKeyManager().down)
			y+=5;
		if(game.getKeyManager().right)
			x+=5;
		if(game.getKeyManager().left)
			x-=5;
	}
	
	public void render(Graphics g) {
		//test
		g.drawImage(Assets.shrek,(int) x,(int) y, null);
	}
}
