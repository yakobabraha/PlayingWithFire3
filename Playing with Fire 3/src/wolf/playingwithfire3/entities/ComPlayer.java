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
	
	private float lastX;
	private float lastY;
	
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
			smartBomb();
			smartMovement();
			move();
			explosionDamage();
			checkItem();
			animations.tick();
			animations.directionByMovement(xMove, yMove);
			checkZoneDamage();
		}
	}
	
	public void smartBomb() {
		if(isPlayerOnOneTile()) {
			if(isBoxAround()) {
				setBomb();
			}
		}
	}
	
	public boolean isBoxAround() {
		int xTile = (int) (x+bounds.x-SettingState.xOffset)/Tile.TILEWIDTH;
		int yTile = (int) (y+bounds.y-SettingState.yOffset)/Tile.TILEHEIGHT;
		return world.getTileID(xTile+1, yTile) == 1 || world.getTileID(xTile-1, yTile) == 1 || world.getTileID(xTile, yTile+1) == 1 || world.getTileID(xTile, yTile-1) == 1;
	}
	
	public void checkZoneDamage() {
		if(System.nanoTime()-lastTimeDamage>=damageCooldown||lastTimeDamage == -1) {
			//check if collision box in explosion
			if(isPixelInZone((int) x+bounds.x, (int) y+bounds.y)||isPixelInZone( (int) x+bounds.x+bounds.width, (int) y+bounds.y)||isPixelInZone((int) x+bounds.x, (int) y+bounds.y+bounds.height)||isPixelInZone((int) x+bounds.x+bounds.width, (int) y+bounds.y+bounds.height)) {
				System.out.println("Damage!");
				health--;
				lastTimeDamage = System.nanoTime();
			}
			
		}
	}
	
	public boolean isPixelInZone(int x, int y) {
		return world.getTileID((int) (x-SettingState.xOffset)/Tile.TILEWIDTH, (int) (y-SettingState.yOffset)/Tile.TILEHEIGHT) == 2;
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
	
	private boolean test;
	
	public void smartMovement() {
		
		int xTile = (int) (x+bounds.x-SettingState.xOffset)/Tile.TILEWIDTH;
		int yTile = (int) (y+bounds.y-SettingState.yOffset)/Tile.TILEHEIGHT;
		
		int[] xMoves = new int[2];
		int[] yMoves = new int[2];
		xMoves[0] = (int) speed;
		xMoves[1] = (int) -speed;
		yMoves[0] = (int) speed;
		yMoves[1] = (int) -speed;
		
		if(world.getTileID(xTile+1, yTile)==2 || bombsManager.isSolid(xTile+1, yTile) )
			xMoves[1] = 0;
		if(world.getTileID(xTile-1, yTile)==2 || bombsManager.isSolid(xTile-1, yTile) )
			xMoves[0] = 0;
		if(world.getTileID(xTile, yTile+1)==2 || bombsManager.isSolid(xTile, yTile + 1) )
			yMoves[0] = 0;
		if(world.getTileID(xTile, yTile-1)==2 || bombsManager.isSolid(xTile, yTile-1) )
			yMoves[1] = 0;
		//|| bombsManager.getExplosionFuture(xTile-1, yTile) || bombsManager.getExplosions(xTile-1, yTile)
		if(bombsManager.getExplosionFuture(xTile, yTile-1))
			System.out.println("yaaaaaaaaaaaa");
		
		if(!test) {
			System.out.println("--------------------------");
			System.out.println(xMoves[0]);
			System.out.println(xMoves[1]);
			System.out.println(yMoves[0]);
			System.out.println(yMoves[1]);
			System.out.println("--------------------------");
			test = true;
		}
		if(isPlayerOnOneTile()) { 
			if(random.nextFloat()<0.05f) {
				xMove = 0;
				yMove = 0;
				float r = random.nextFloat();
				if(xMoves[Math.round(r)] != 0) {
					xMove = xMoves[Math.round(r)];
				}else {
					if(xMoves[0]!=0)
						xMove = xMoves[0];
					if(xMoves[1]!=0)
						xMove = xMoves[1];
				}
				r= random.nextFloat();
				if(yMoves[Math.round(r)] != 0) {
					yMove = yMoves[Math.round(r)];
				}else {
					if(yMoves[0]!=0)
						yMove = yMoves[0];
					if(yMoves[1]!=0)
						yMove = yMoves[1];
				}
				r = random.nextFloat();
				if(xMove != 0 && yMove != 0) {
					int rint = Math.round(r);
					if(rint == 1)
						xMove = 0;
					if(rint == 0)
						yMove = 0;
				}
			}else if(x==lastX && y== lastY && false){
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
		
		
		
		lastX = x;
		lastY = y;
	}
	
	public boolean isPlayerOnOneTile() {
		return ((int)(x+bounds.x-SettingState.xOffset)/Tile.TILEWIDTH) == (int)((x+bounds.x+bounds.width-SettingState.xOffset)/Tile.TILEWIDTH) && (int) ((y+bounds.y-SettingState.yOffset)/Tile.TILEHEIGHT) == (int)((y+bounds.y+bounds.height-SettingState.yOffset)/Tile.TILEHEIGHT);
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

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAnimIndex(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHealth(int h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOnlinePlayer() {
		// TODO Auto-generated method stub
		return false;
	}

}
