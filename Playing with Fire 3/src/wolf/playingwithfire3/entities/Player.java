package wolf.playingwithfire3.entities;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;

public class Player extends Entity{
	
	public static int DEFAULT_HEALTH = 10;
	public static float DEFAULT_SPEED = 3.0f;

	protected int health;
	protected float speed;
	protected float xMove, yMove;
	private Game game;
	
	public Player(Game game, float x, float y) {
		super(x, y);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		this.game = game;
	}
	
	public void move() {
		x += xMove;
		y += yMove;
	}
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public void tick() {
		getInput();
		move();
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		if(game.getKeyManager().up)
			yMove = -speed;
		if(game.getKeyManager().down)
			yMove = speed;
		if(game.getKeyManager().left)
			xMove = -speed;
		if(game.getKeyManager().right)
			xMove = speed;

	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void render(Graphics g) {
		//test
		g.drawImage(Assets.shrek,(int) x,(int) y, null);
	}
}
