package wolf.playingwithfire3.entities;

import java.awt.Color;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class Player extends Entity{
	
	public static int DEFAULT_HEALTH = 10;
	public static float DEFAULT_SPEED = 3.0f;
	
	protected int health;
	protected float speed;
	protected float xMove, yMove;
	private Game game;
	private World world;
	private BombsManager bombsManager;
	private long lastTimeDamage = -1;
	private long damageCooldown;
	private int playerNumber;
	private int bombAmount;
	
	public Player(World world,Game game, BombsManager bombsManager,float x, float y, long damageCooldown, int playerNumber, int bombAmount) {
		super(x, y, 45, 45);
		this.bombsManager = bombsManager;
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		this.game = game;
		this.world = world;
		
		bounds.x = 9;
		bounds.y = 16;
		bounds.width = 18;
		bounds.height = 31;
		
		this.damageCooldown = damageCooldown;
		this.playerNumber = playerNumber;
		this.bombAmount = bombAmount;
		
	}
	
	protected boolean collisionWithTile(int x, int y) {
		return world.getTile(x, y).isSolid() || bombsManager.isSolid(x, y);
	}
	
	public boolean isPixelInExplosion(int x, int y) {
		return bombsManager.getExplosions(x/Tile.TILEWIDTH, y/Tile.TILEHEIGHT);
	}
	
	public void move() {
		moveX();
		movey();
	}
	
	public void moveX() {
		if(xMove > 0) {
			int tx = (int) (x+ xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			if(tx == (int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH) {
				x+=xMove;
				return;
			}
				
			if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILEHEIGHT)&&!collisionWithTile(tx, (int) (y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x +=xMove;
			}else {
				x = tx*Tile.TILEWIDTH-bounds.x-bounds.width-1;
				
			}
		}else if(xMove < 0) {
			int tx = (int) (x+ xMove + bounds.x ) / Tile.TILEWIDTH;
			if(tx == (int) (x+ bounds.x ) / Tile.TILEWIDTH) {
				x+=xMove;
				return;
			}
			if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILEHEIGHT)&&!collisionWithTile(tx, (int) (y+bounds.y+bounds.height)/Tile.TILEHEIGHT)) {
				x +=xMove;
			}
		}
	}

	public void movey() {
		// TODO Auto-generated method stub
		if(yMove < 0) {
			int ty = (int) (y +yMove + bounds.y) / Tile.TILEHEIGHT;
			if(ty == (int) (y + bounds.y) / Tile.TILEHEIGHT) {
				y += yMove;
				return;
			}
			if(!collisionWithTile((int) (x+bounds.x)/Tile.TILEWIDTH ,ty)&&!collisionWithTile((int) (x+bounds.x+bounds.width)/Tile.TILEWIDTH ,ty))
				y+=yMove;
		}else if(yMove > 0){
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if(ty == (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT) {
				y += yMove;
				return;
			}
			if(!collisionWithTile((int) (x+bounds.x)/Tile.TILEWIDTH ,ty)&&!collisionWithTile((int) (x+bounds.x+bounds.width)/Tile.TILEWIDTH ,ty))
				y+=yMove;
		}
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
		explosionDamage();
	}
	
	public void explosionDamage() {
		if(System.nanoTime()-lastTimeDamage>=damageCooldown||lastTimeDamage == -1) {
			//check if collision box in explosion
			if(isPixelInExplosion((int) x+bounds.x, (int) y+bounds.y)||isPixelInExplosion((int) x+bounds.x+bounds.width, (int) y+bounds.y)||isPixelInExplosion((int) x+bounds.x, (int) y+bounds.y+bounds.height)||isPixelInExplosion((int) x+bounds.x+bounds.width, (int) y+bounds.y+bounds.height)) {
				System.out.println("Damage!");
				health--;
				lastTimeDamage = System.nanoTime();
			}
			
		}
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;
		if(playerNumber == 1) {
			if(game.getKeyManager().up1)
				yMove = -speed;
			if(game.getKeyManager().down1)
				yMove = speed;
			if(game.getKeyManager().left1)
				xMove = -speed;
			if(game.getKeyManager().right1)
				xMove = speed;
			if(game.getKeyManager().setBomb1)
				setBomb();
		}else {
			if(game.getKeyManager().up2)
				yMove = -speed;
			if(game.getKeyManager().down2)
				yMove = speed;
			if(game.getKeyManager().left2)
				xMove = -speed;
			if(game.getKeyManager().right2)
				xMove = speed;
			if(game.getKeyManager().setBomb2)
				setBomb();
		}

	}
	
	public void setBomb() {
		if(bombAmount != 0)
			if(bombsManager.addBomb(getTilePositionX(), getTilePositionY(), this))
				bombAmount--;
	}
	
	
	
	public int getBombAmount() {
		return bombAmount;
	}

	public void setBombAmount(int bombAmount) {
		this.bombAmount = bombAmount;
	}

	public int getTilePositionX() {
		return ((int)x + bounds.x+(bounds.width/2))/Tile.TILEWIDTH;
	}
	
	public int getTilePositionY() {
		return ((int)y + bounds.y+(bounds.height/2))/Tile.TILEHEIGHT;
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
	


	public void render(Graphics graphics) {
		//test
		if(playerNumber == 1)
			graphics.drawImage(Assets.yellowDog,(int) x,(int) y, null);
		if(playerNumber == 2)
			graphics.drawImage(Assets.whiteDog,(int) x,(int) y, null);
		graphics.setColor(Color.red);
		//graphics.fillRect((int) (x +bounds.x), (int) (y +bounds.y), bounds.width, bounds.height);



	}
}
