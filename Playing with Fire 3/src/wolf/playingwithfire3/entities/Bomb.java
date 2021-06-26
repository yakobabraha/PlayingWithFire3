package wolf.playingwithfire3.entities;


import java.awt.Graphics;

import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class Bomb extends Entity{

	private float bombDuration;
	private float explosionDuration;
	private int fps;
	private int tickCounter;
	private BombsManager bombsManager;
	private boolean explosion;
	private int range;
	private World world;
	private int[] toDestroy = new int[4];
	
	public Bomb(float x, float y, int width, int height, float bombDuration, float explosionDuration,int range,int fps, BombsManager bombsManager, World world) {
		super(x, y, width, height);
		this.bombDuration = bombDuration;
		this.fps = fps;
		this.tickCounter = 0;
		this.bombsManager = bombsManager;
		this.range = range;
		this.world = world;
		this.explosionDuration = explosionDuration;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(!explosion) {
			tickCounter++;
			if(tickCounter/fps>=bombDuration) {
				System.out.println("booom!");
				tickCounter = 0;
				explode();
			}
		}else {
			tickCounter++;
			if(tickCounter/fps>=explosionDuration) {
				bombsManager.endExplosion((int)x/Tile.TILEWIDTH, (int) y/Tile.TILEHEIGHT);
			}
		}
	}
	
	public void explode() {
		explosion = true; 
		tickCounter = 0;
		for(int i = 1;i<range;i++) {
			if(world.getTile((int)x/Tile.TILEWIDTH+i,(int) y/Tile.TILEHEIGHT).isSolid()) {
				if(world.getTile((int)x/Tile.TILEWIDTH+i,(int) y/Tile.TILEHEIGHT).isDestructible())
					toDestroy[0]=(int)x/Tile.TILEWIDTH+i;
				break;	
			}	
			bombsManager.sendOn((int)x/Tile.TILEWIDTH+i,(int) y/Tile.TILEHEIGHT);
		}
		for(int i = 1;i<range;i++) {
			if(world.getTile((int)x/Tile.TILEWIDTH-i,(int) y/Tile.TILEHEIGHT).isSolid()) {
				if(world.getTile((int)x/Tile.TILEWIDTH-i,(int) y/Tile.TILEHEIGHT).isDestructible())
					toDestroy[1]=(int)x/Tile.TILEWIDTH-i;
				break;
			}
			bombsManager.sendOn((int)x/Tile.TILEWIDTH-i,(int) y/Tile.TILEHEIGHT);
		}
		for(int i = 1;i<range;i++) {
			if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT+i).isSolid()) {
				if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT+i).isDestructible())
					toDestroy[2]=(int) y/Tile.TILEHEIGHT+i;
				break;
			}
			bombsManager.sendOn((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT+i);
		}
		for(int i = 1;i<range;i++) {
			if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT-i).isSolid()) {
				if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT-i).isDestructible())
					toDestroy[3] = (int) y/Tile.TILEHEIGHT-i;
				break;
			}
			bombsManager.sendOn((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT-i);
		}
		
		
	}
	
	public int[] getToDestroy() {
		return toDestroy;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isExploded() {
		return explosion;
	}
	
	@Override
	public void render(Graphics graphics) {
		if(!explosion) {
			graphics.drawImage(Assets.redBomb,(int) x,(int) y, null);
		}else {
			graphics.drawImage(Assets.bombExplosion,(int) x,(int) y, null);
			for(int i = 1;i<range;i++) {
				if(world.getTile((int)x/Tile.TILEWIDTH+i,(int) y/Tile.TILEHEIGHT).isSolid()) {
					if(world.getTile((int)x/Tile.TILEWIDTH+i,(int) y/Tile.TILEHEIGHT).isDestructible()) {
						graphics.drawImage(Assets.bombExplosion,(int) x+i*Tile.TILEWIDTH,(int) y, null);;
					}
					break;	
				}
				graphics.drawImage(Assets.bombExplosion,(int) x+i*Tile.TILEWIDTH,(int) y, null);
				bombsManager.setExplosions((int) x/Tile.TILEWIDTH+i,(int) y/Tile.TILEHEIGHT, true);
				if((int)x/Tile.TILEWIDTH+i==toDestroy[0])
					break;
			}
			for(int i = 1;i<range;i++) {
				if(world.getTile((int)x/Tile.TILEWIDTH-i,(int) y/Tile.TILEHEIGHT).isSolid()) {
					if(world.getTile((int)x/Tile.TILEWIDTH-i,(int) y/Tile.TILEHEIGHT).isDestructible()) {
						graphics.drawImage(Assets.bombExplosion,(int) x-i*Tile.TILEWIDTH,(int) y, null);
					}
					break;
				}
				graphics.drawImage(Assets.bombExplosion,(int) x-i*Tile.TILEWIDTH,(int) y, null);
				bombsManager.setExplosions((int) x/Tile.TILEWIDTH-i,(int) y/Tile.TILEHEIGHT, true);
				if((int)x/Tile.TILEWIDTH-i==toDestroy[1])
					break;
			}
			for(int i = 1;i<range;i++) {
				if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT+i).isSolid()) {
					if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT+i).isDestructible()) {
						graphics.drawImage(Assets.bombExplosion,(int) x,(int) y+i*Tile.TILEHEIGHT, null);
					}
					break;
				}
				graphics.drawImage(Assets.bombExplosion,(int) x,(int) y+i*Tile.TILEHEIGHT, null);
				bombsManager.setExplosions((int) x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT+i, true);
				if((int)y/Tile.TILEHEIGHT+i==toDestroy[2])
					break;
			}
			for(int i = 1;i<range;i++) {
				if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT-i).isSolid()) {
					if(world.getTile((int)x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT-i).isDestructible()) {
						graphics.drawImage(Assets.bombExplosion,(int) x,(int) y-i*Tile.TILEHEIGHT, null);
					}
					break;
				}
				graphics.drawImage(Assets.bombExplosion,(int) x,(int) y-i*Tile.TILEHEIGHT, null);
				bombsManager.setExplosions((int) x/Tile.TILEWIDTH,(int) y/Tile.TILEHEIGHT-i, true);
				if((int)y/Tile.TILEHEIGHT-i==toDestroy[3])
					break;
			}
		}
	}
	
}
