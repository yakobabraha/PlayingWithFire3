package wolf.playingwithfire3.states;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;

public abstract class State {

	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}
	
	public abstract void tick();
	public abstract void render(Graphics graphics);
	
}
