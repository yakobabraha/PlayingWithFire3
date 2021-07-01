package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.entities.BombsManager;
import wolf.playingwithfire3.entities.ComPlayer;
import wolf.playingwithfire3.entities.LocalPlayer;
import wolf.playingwithfire3.entities.Player;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.tile.Tile;
import wolf.playingwithfire3.worlds.World;

public class GameState extends State{

	private Player[] players;
	private Game game;
	private World world;
	private BombsManager bombsManager;
	
	//timer
	private int startTimer = 180;
	private int currentTimer = -1;
	private long startTime = System.currentTimeMillis();
	
	public GameState(Game game) {
		super(game);
		this.game = game;
		world = new World("res/worlds/world1.txt");
		bombsManager = new BombsManager(world.getWidth(), world.getHeight(),3.0f,1.0f,game.getFps(),world);
		players = new Player[4];
		players[0] = new LocalPlayer(world,game,bombsManager,world.getSpawnX1()*Tile.TILEWIDTH+SettingState.xOffset,world.getSpawnY1()*Tile.TILEWIDTH+SettingState.yOffset,1100000000,1,3, "default");
		players[1] = new LocalPlayer(world,game,bombsManager,world.getSpawnX2()*Tile.TILEWIDTH+SettingState.xOffset,world.getSpawnY2()*Tile.TILEWIDTH+SettingState.yOffset,1100000000,2,3,"default");
		players[2] = new ComPlayer(world,3, "default");
		players[3] = new ComPlayer(world,4, "default");
	}

	public void tick() {
		world.tick();
		bombsManager.tick();
		for(int i = 0; i < players.length;i++) {
			if(players[i]!=null)
				players[i].tick();
		}
		timertick();
	}
	
	public void timertick() {
		if(currentTimer != 0) {
			long difference = System.currentTimeMillis() - startTime;
			currentTimer= (int) (startTimer - (difference/1000));
		}
	}
	
	public void render(Graphics graphics) {
		
		world.render(graphics);
		bombsManager.render(graphics);
		for(int i = 0; i < players.length;i++) {
			if(players[i]!=null)
				players[i].render(graphics);
		}
		drawlayout(graphics);
		//graphics.drawString("Health: "+players[0].getHealth(), 90, 180);
		
	}
	
	public void drawlayout(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		//SideBar hintergrund
		graphics.fillRect(0, 0, SettingState.xOffset, game.height);
		//Ränder
		graphics.fillRect(0, 0, game.width, SettingState.yOffset);
		graphics.fillRect(0, game.height-5, game.width, SettingState.yOffset);
		graphics.fillRect(0, 0, SettingState.xOffset, game.height);
		drawsidebar(graphics);
	}
	
	public void drawsidebar(Graphics graphics) {
		graphics.drawImage(Assets.logo, 5, 10, null);
		for(int i = 0;i<4;i++) {
			drawPlayerCard(graphics, i);
		}
	}
	
	public void drawPlayerCard(Graphics graphics, int i) {
		//player card rand
		graphics.setColor(Color.black);
		graphics.drawRect(10, 120 + i * 100, 100, 80);
		//playerbild
		graphics .setColor(Color.darkGray);
		graphics.fillRect(32, 120 + i * 100+5, 55, 50);
		if(i == 0)
			graphics.drawImage(Assets.yellowDogFace,32+5, 120 + i * 100+5+4, null);
		if(i==1)
			graphics.drawImage(Assets.whiteDogFace,32+5, 120 + i * 100+5+5, null);
		//herze
		if(players[i]!=null) {
			int playerHealth = players[i].getHealth();
			for(int x = 0; x<playerHealth;x++) {
				graphics.drawImage(Assets.heart, 25 +24 * x, 120 + i * 100 + 55, null);
			}
		}
		renderTimer(graphics);
	}
	
	public void renderTimer(Graphics graphics) {
		//time digits
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		graphics.drawString(formatSeconds(currentTimer), 30, 550);
		//timeline
		float G = startTimer * 1000;
		float W = G - (System.currentTimeMillis() - startTime);
		float percent = W / G;
		int length = 0;
		if(currentTimer != 0)
			length = (int) (100 * percent);
		graphics.drawRect(14, 599, 102, 22);
		graphics.drawRect(13, 598, 104, 24);
		graphics.setColor(Color.RED);
		graphics.fillRect(15, 600, length, 21);
	}
	
	public String formatSeconds(int seconds) {
		int rest = seconds % 60;
		int minutes = (seconds - rest)/60;
		String restString = Integer.toString(rest);
		if(rest<10)
			restString = "0"+restString;
		return Integer.toString(minutes)+" : "+restString;
	}
	
}
