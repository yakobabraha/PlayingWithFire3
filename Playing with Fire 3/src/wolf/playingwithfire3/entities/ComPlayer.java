package wolf.playingwithfire3.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import wolf.playingwithfire3.gfx.AnimationPacket;
import wolf.playingwithfire3.states.SettingState;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class ComPlayer extends Player{
		
	private String direction = "left";
	private float xMove = 0;
	private float yMove = -2;
	private AnimationPacket animations;
	private float speed;
	private World world;
	private BombsManager bombsManager;
	
	private Random random = new Random();
	
	private int playerNumber;
	private int bombAmount = 1;
	private int health = 3;
	
	private long lastTimeDamage = -1;
	private long damageCooldown = 1000000000;
	
	private int bombrange;
	
	public ComPlayer(int playerNumber, String skinName, World world, BombsManager bombsManager) {
		
		super(world.getSpawnX(playerNumber)*Tile.TILEWIDTH+SettingState.xOffset,world.getSpawnY(playerNumber)*Tile.TILEHEIGHT+SettingState.yOffset, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		// TODO Auto-generated constructor stub
		animations = new AnimationPacket(playerNumber, skinName);
		speed = 3.0f;
		bounds.x = 9;
		bounds.y = 16;
		bounds.width = 18;
		bounds.height = 28;
		bombrange = 3;
		this.world = world;
		this.bombsManager = bombsManager;
		this.playerNumber = playerNumber;
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public int getBombAmount() {
		return bombAmount;
	}

	@Override
	public void setBombAmount(int bombAmount) {
		// TODO Auto-generated method stub
		this.bombAmount = bombAmount;
	}
	
	public void checkItem() {
		isPixelInItem((int) x+bounds.x, (int) y+bounds.y);
		isPixelInItem((int) x+bounds.x+bounds.width, (int) y+bounds.y);
		isPixelInItem((int) x+bounds.x, (int) y+bounds.y+bounds.height);
		isPixelInItem((int) x+bounds.x+bounds.width, (int) y+bounds.y+bounds.height);
		}

	@Override
	public void tick() {
		if(health != 0) {
			if(random.nextFloat()<=0.005f)
				setBomb();
			randomMovement();
			move();
			explosionDamage();
			checkItem();
			animations.tick();
			animations.directionByMovement(xMove, yMove);
		}
	}
	public void isPixelInItem(int x, int y) {
		 if(world.getTileID((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT)==3) {
				if(speed<5.25) {
					speed = speed+0.75f;
					}
				world.setTile((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT,0);
		 }
		else  if(world.getTileID((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT)==4) {
				if(this.bombAmount<4) {
					this.bombAmount = this.bombAmount + 1;
					}
				world.setTile((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT,0);
		}
		else  if(world.getTileID((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT)==5) {
				if(bombrange<6) {
					bombrange = bombrange + 1;
					}
				world.setTile((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT,0);
		}
	}
	
	public void randomMovement() {
		if(random.nextFloat()<0.05f) {
			xMove = 0;
			yMove = 0;
			float r = random.nextFloat();
			if(r<=0.25) {
				direction = "left";
				xMove = -speed;
			}else if(r<=0.5) {
				direction = "right";
				xMove = speed;
			}else if(r<=0.75) {
				direction = "up";
				yMove = -speed;
			}else {
				direction = "down";
				yMove = speed;
			}			
		}
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
	
	public void setBomb() {
		if(bombAmount != 0)
			if(bombsManager.addBomb(getTilePositionX(), getTilePositionY(), this,bombrange))
				bombAmount--;
	}
	
	public int getTilePositionX() {
		return ((int)x + bounds.x+(bounds.width/2)-SettingState.xOffset)/Tile.TILEWIDTH;
	}
	
	public int getTilePositionY() {
		return ((int)y + bounds.y+(bounds.height/2)-SettingState.yOffset)/Tile.TILEHEIGHT;
	}
	
	public void move() {
		moveX();
		movey();
	}
	
	public void moveX() {
		if(xMove > 0) {
			int tx = (int) (x+ xMove + bounds.x + bounds.width-SettingState.xOffset) / Tile.TILEWIDTH;
			if(tx == (int) (x + bounds.x + bounds.width-SettingState.xOffset) / Tile.TILEWIDTH) {
				x+=xMove;
				return;
			}
				
			if(!collisionWithTile(tx, (int) (y+bounds.y-SettingState.yOffset)/Tile.TILEHEIGHT)&&!collisionWithTile(tx, (int) (y+bounds.y+bounds.height-SettingState.yOffset)/Tile.TILEHEIGHT)) {
				x +=xMove;
			}else {
				x = tx*Tile.TILEWIDTH-bounds.x-bounds.width-1+SettingState.xOffset;
				
			}
		}else if(xMove < 0) {
			int tx = (int) (x+ xMove + bounds.x - SettingState.xOffset) / Tile.TILEWIDTH;
			if(tx == (int) (x+ bounds.x - SettingState.xOffset) / Tile.TILEWIDTH) {
				x+=xMove;
				return;
			}
			if(!collisionWithTile(tx, (int) (y+bounds.y-SettingState.yOffset)/Tile.TILEHEIGHT)&&!collisionWithTile(tx, (int) (y+bounds.y+bounds.height-SettingState.yOffset)/Tile.TILEHEIGHT)) {
				x +=xMove;
			}
		}
	}

	public void movey() {
		// TODO Auto-generated method stub
		if(yMove < 0) {
			int ty = (int) (y +yMove + bounds.y-SettingState.yOffset) / Tile.TILEHEIGHT;
			if(ty == (int) (y + bounds.y-SettingState.yOffset) / Tile.TILEHEIGHT) {
				y += yMove;
				return;
			}
			if(!collisionWithTile((int) (x+bounds.x-SettingState.xOffset)/Tile.TILEWIDTH ,ty)&&!collisionWithTile((int) (x+bounds.x+bounds.width-SettingState.xOffset)/Tile.TILEWIDTH ,ty))
				y+=yMove;
		}else if(yMove > 0){
			int ty = (int) (y + yMove + bounds.y + bounds.height-SettingState.yOffset) / Tile.TILEHEIGHT;
			if(ty == (int) (y + bounds.y + bounds.height-SettingState.yOffset) / Tile.TILEHEIGHT) {
				y += yMove;
				return;
			}
			if(!collisionWithTile((int) (x+bounds.x-SettingState.xOffset)/Tile.TILEWIDTH ,ty)&&!collisionWithTile((int) (x+bounds.x+bounds.width-SettingState.xOffset)/Tile.TILEWIDTH ,ty))
				y+=yMove;
		}
	}
	
	protected boolean collisionWithTile(int x, int y) {
		return world.getTile(x, y).isSolid() || bombsManager.isSolid(x, y);
	}
	
	public boolean isPixelInExplosion(int x, int y) {
		return bombsManager.getExplosions((x-SettingState.xOffset)/Tile.TILEWIDTH, (y-SettingState.yOffset)/Tile.TILEHEIGHT);
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

}
