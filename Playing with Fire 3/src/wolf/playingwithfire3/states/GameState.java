package wolf.playingwithfire3.states;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.entities.Player;
import wolf.playingwithfire3.gfx.Assets;

public class GameState extends State{

	private Player player;
	
	public GameState(Game game) {
		super(game);
		player = new Player(game,100,100);
	}

	public void tick() {
		player.tick();
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.amogus,40,200, null);
		g.drawImage(Assets.amogus,500,500, null);
		g.drawImage(Assets.amogus,150,170, null);
		player.render(g);
	}
	
}
