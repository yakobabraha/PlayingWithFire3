package wolf.playingwithfire3.entities;

import java.awt.Graphics;

public abstract class Entity {
	
	
	
	protected float x, y;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);

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
