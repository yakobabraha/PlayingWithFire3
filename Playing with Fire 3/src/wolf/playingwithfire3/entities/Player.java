package wolf.playingwithfire3.entities;

public abstract class Player extends Entity{

	
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	public abstract int getHealth();
	
	public abstract int getBombAmount();
	public abstract void setBombAmount(int bombAmount);
	public abstract int getPlayerNumber();
	public abstract void setDirection(String direction);
	public abstract void setAnimIndex(int i);
}
