package wolf.playingwithfire3.states;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.entities.Player;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.worlds.World;

public class GameState extends State{

	private Player player;
	private Game game;
	private World world;
	
	public GameState(Game game) {
		super(game);
		this.game = game;
		world = new World("res/worlds/world1.txt");
		player = new Player(world,game,90,135);
	}

	public void tick() {
		world.tick();
		player.tick();
	}
	
	public void render(Graphics graphics) {
		graphics.fillRect(0, 0, game.width, game.height);
		world.render(graphics);
		graphics.drawImage(Assets.whiteDog,40,200, null);
		graphics.drawImage(Assets.whiteDog,500,500, null);
		graphics.drawImage(Assets.whiteDog,150,170, null);
		player.render(graphics);
	}
	
}
