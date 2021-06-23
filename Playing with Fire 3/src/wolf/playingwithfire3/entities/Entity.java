package wolf.playingwithfire3.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
	
	
	protected int width, height;
	protected float x, y;
	protected Rectangle bounds;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		bounds = new Rectangle(0,0,width, height);
	}
	
	public abstract void tick();
	public abstract void render(Graphics graphics);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
