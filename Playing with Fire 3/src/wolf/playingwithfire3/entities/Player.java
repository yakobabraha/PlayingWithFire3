package wolf.playingwithfire3.entities;

// Yakob

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
	public abstract void setHealth(int h);
	public abstract boolean isOnlinePlayer();
	public abstract void setBomb(int x, int y, int range);
}
