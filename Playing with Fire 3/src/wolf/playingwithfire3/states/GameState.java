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
import wolf.playingwithfire3.ui.QuitButton;
import wolf.playingwithfire3.ui.UIManager;
import wolf.playingwithfire3.worlds.World;

public class GameState extends State{

	private Player[] players;
	private Game game;
	private World world;
	private BombsManager bombsManager;
	private UIManager uiManager;
	
	//timer
	private int startTimer = 120;
	private int currentTimer = -1;
	private long startTime = System.currentTimeMillis();
	
	private int zoneLayer = 1;
	private long zoneDelay = 200000000;
	private long zoneStart = System.nanoTime();
	
	private StateManager stateManager;
	
	
	public GameState(Game game, int playerNumber, int opponentAmount, String worldName, StateManager stateManager) {
		super(game);
		this.game = game;
		
		this.stateManager = stateManager;
		
		uiManager = new UIManager();
		uiManager.addObject(new QuitButton(10,650, 110, 22, Assets.btn_start, game, stateManager));
		game.getMouseManager().setUIManager(uiManager);
		
		world = new World("res/worlds/"+worldName+".txt");
		bombsManager = new BombsManager(world.getWidth(), world.getHeight(),1.5f,1.0f,game.getFps(),world);
		players = new Player[4];
		spawnPlayers(playerNumber, opponentAmount);
		//players[0] = new LocalPlayer(world,game,bombsManager,1100000000,1,3, "default");
		//players[1] = new LocalPlayer(world,game,bombsManager,1100000000,2,3,"default");
		//players[2] = new ComPlayer(3, "default", world, bombsManager);
		//players[3] = new ComPlayer(4, "default", world, bombsManager);
	}
	
	public GameState(Game game,Player[] players, String worldName, BombsManager bombsManager, World world, StateManager stateManager) {
		super(game);
		this.game = game;
		
		uiManager = new UIManager();
		uiManager.addObject(new QuitButton(10,650, 110, 22, Assets.btn_start, game,stateManager));
		game.getMouseManager().setUIManager(uiManager);
		
		this.world = world;
		this.bombsManager = bombsManager;
		this.players = players;
		this.stateManager = stateManager;
		System.out.println(bombsManager);
	}
	
	public void spawnPlayers(int playerNumber, int opponentAmount) {
		for(int i = 1;i<=playerNumber;i++) {
			System.out.println(i);
			players[i-1] = new LocalPlayer(world,game,bombsManager,1100000000,i,3, "default",false);
		}
		
		for(int i = 2;i<=opponentAmount+1;i++) {
			System.out.println(i);
			if(players[i-1]==null) {
				players[i-1] = new ComPlayer(i, "default", world, bombsManager);
			}
		}
	}

	public void tick() {
		world.tick();
		bombsManager.tick();
		uiManager.tick();
		for(int i = 0; i < players.length;i++) {
			if(players[i]!=null) {
				checkWin();
				players[i].tick();
			}
		}
		timertick();
		zonetick();
	}
	
	public void zonetick() {
		if(currentTimer == 0) {
			if(System.nanoTime() - zoneStart>zoneDelay) {
				for(int i = zoneLayer; i<world.getHeight()/2;i++) {
					for(int k = i; k<(world.getWidth()-i);k++) {
						System.out.println(k);
						if(world.getTileID(k, i)!=2) {
							world.setTile(k, i, 2);
							zoneStart = System.nanoTime();
							return;
						}
					}
					for(int k = i; k<(world.getWidth()-i);k++) {
						if(world.getTileID(world.getWidth() - i-1, k)!=2) {
							world.setTile(world.getWidth() - i-1, k, 2);
							zoneStart = System.nanoTime();
							return;
						}
					}
					for(int k = i; k<(world.getWidth()-i);k++) {
						if(world.getTileID(world.getWidth()-k, world.getHeight()-i-1)!=2) {
							world.setTile(world.getWidth()-k, world.getHeight()-i-1, 2);
							zoneStart = System.nanoTime();
							return;
						}
					}
					for(int k = i; k<(world.getWidth()-i);k++) {
						if(world.getTileID(i, world.getHeight() - k)!=2) {
							world.setTile(i, world.getHeight() - k, 2);
							zoneStart = System.nanoTime();
							return;
						}
					}
				}
			}
		}
	}
	
	public void checkWin() {
		int amount = 0;
		for(int i = 0; i < players.length;i++) {
			if(players[i]!=null && players[i].getHealth() != 0) {
				amount++;
			}
		}
		if(amount == 1) {
			for(int i = 0; i < players.length;i++) {
				if(players[i]!=null && players[i].getHealth() != 0) {
					stateManager.setState(new WinState(game, i,stateManager));
				}
			}
		}
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
		uiManager.render(graphics);
		//graphics.drawString("Health: "+players[0].getHealth(), 90, 180);
		
	}
	
	public void drawlayout(Graphics graphics) {
		graphics.drawImage(Assets.gameGUI,0,0,null);
		for(int i = 0;i<4;i++) {
			drawPlayerCard(graphics, i);
		}
	}
	public void drawPlayerCard(Graphics graphics, int i) {
		if(players[i]!=null) {
			if(players[i].getHealth() == 0) {
				graphics.drawImage(Assets.skull,36+6, 220 + i * 100+5+4, null);
			}else {
				if(i==0)
				graphics.drawImage(Assets.blueProfile,22, 200 + i * 100+5+4, null);
				else if(i==1)
					graphics.drawImage(Assets.redProfile,23, 213 + i * 100+5+4, null);
				else if(i==2)
					graphics.drawImage(Assets.greenProfile,22, 225 + i * 100+5+4, null);
				else graphics.drawImage(Assets.purpleProfile,22, 235+ i * 100+5+4, null);
			}
		}
		//herzen
		if(players[i]!=null) {
			int playerHealth = players[i].getHealth();
			for(int x = 0; x<playerHealth;x++) {
				if(i==0)
				graphics.drawImage(Assets.blueheart, 28 +24 * x, 215 + i * 100 + 55, null);
				else if(i==1)
				graphics.drawImage(Assets.redheart, 28 +24 * x, 228 + i * 100 + 55, null);
				else if(i==2)
				graphics.drawImage(Assets.purpleheart, 28 +24 * x, 240 + i * 100 + 55, null);
				else graphics.drawImage(Assets.greenheart, 28 +24 * x, 250 + i * 100 + 55, null);

			}
		}
		renderTimer(graphics);
	}
	
	public void renderTimer(Graphics graphics) {
		//time digits
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		graphics.setColor(Color.GRAY);
		graphics.drawRect(238, 755, 90, 30);
		graphics.drawRect(237, 754, 92, 32);
		graphics.fillRect(239, 756, 90,30 );
		graphics.setColor(Color.RED);
		graphics.drawString(formatSeconds(currentTimer), 250, 780);
		//timeline
		float G = startTimer * 1000;
		float W = G - (System.currentTimeMillis() - startTime);
		float percent = W / G;
		int length = 0;
		if(currentTimer != 0)
			length = (int) (100 * percent);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(350, 760, 102, 22);
		graphics.drawRect(349, 759, 104, 24);
		graphics.setColor(Color.RED);
		graphics.fillRect(350, 761, length, 21);
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
