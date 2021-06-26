package wolf.playingwithfire3.states;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.entities.BombsManager;
import wolf.playingwithfire3.entities.Player;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.worlds.World;

public class GameState extends State{

	private Player player;
	private Game game;
	private World world;
	private BombsManager bombsManager;
	
	public GameState(Game game) {
		super(game);
		this.game = game;
		world = new World("res/worlds/world1.txt");
		bombsManager = new BombsManager(world.getWidth(), world.getHeight(),3.0f,1.0f,game.getFps(),world);
		player = new Player(world,game,bombsManager,90,135,1100000000);
	}

	public void tick() {
		world.tick();
		bombsManager.tick();
		player.tick();
	}
	
	public void render(Graphics graphics) {
		graphics.fillRect(0, 0, game.width, game.height);
		world.render(graphics);
		bombsManager.render(graphics);
		player.render(graphics);
		graphics.drawString("Health: "+player.getHealth(), 90, 180);
	}
	
}
