package wolf.playingwithfire3.states;

import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.entities.BombsManager;
import wolf.playingwithfire3.entities.Entity;
import wolf.playingwithfire3.entities.Player;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class GameState extends State{

	private Player[] players;
	private Game game;
	private World world;
	private BombsManager bombsManager;
	
	public GameState(Game game) {
		super(game);
		this.game = game;
		world = new World("res/worlds/world1.txt");
		bombsManager = new BombsManager(world.getWidth(), world.getHeight(),3.0f,1.0f,game.getFps(),world);
		players = new Player[4];
		players[0] = new Player(world,game,bombsManager,world.getSpawnX1()*Tile.TILEWIDTH,world.getSpawnY1()*Tile.TILEWIDTH,1100000000,1,3);
		players[1] = new Player(world,game,bombsManager,world.getSpawnX2()*Tile.TILEWIDTH,world.getSpawnY2()*Tile.TILEWIDTH,1100000000,2,3);
	}

	public void tick() {
		world.tick();
		bombsManager.tick();
		for(int i = 0; i < players.length;i++) {
			if(players[i]!=null)
				players[i].tick();
		}
		
	}
	
	public void render(Graphics graphics) {
		graphics.fillRect(0, 0, game.width, game.height);
		world.render(graphics);
		bombsManager.render(graphics);
		for(int i = 0; i < players.length;i++) {
			if(players[i]!=null)
				players[i].render(graphics);
		}
		graphics.drawString("Health: "+players[0].getHealth(), 90, 180);
	}
	
}
