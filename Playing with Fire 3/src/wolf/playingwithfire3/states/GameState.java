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
	private int startTimer = 180;
	private int currentTimer = -1;
	private long startTime = System.currentTimeMillis();
	
	public GameState(Game game, int playerNumber, int opponentAmount, String worldName) {
		super(game);
		this.game = game;
		
		uiManager = new UIManager();
		uiManager.addObject(new QuitButton(0,650, 180, 22, Assets.btn_start, game));
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
	
	public void spawnPlayers(int playerNumber, int opponentAmount) {
		for(int i = 1;i<=playerNumber;i++) {
			System.out.println(i);
			players[i-1] = new LocalPlayer(world,game,bombsManager,1100000000,i,3, "default");
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
					State.setState(new WinState(game, i));
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
		graphics.setColor(Color.LIGHT_GRAY);
		//SideBar hintergrund
		graphics.fillRect(0, 0, SettingState.xOffset, game.height);
		//Ränder
		graphics.fillRect(0, 0, game.width, SettingState.yOffset);
		graphics.fillRect(0, game.height-15, game.width, SettingState.yOffset);
		graphics.fillRect(0, 0, SettingState.xOffset, game.height);
		graphics.fillRect(805, 0, 15, game.height);
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
		if(players[i]!=null) {
			if(players[i].getHealth() == 0) {
				graphics.drawImage(Assets.skull,32+5, 120 + i * 100+5+4, null);
			}else {
				graphics.drawImage(Assets.yellowDogFace,32+5, 120 + i * 100+5+4, null);
			}
			if(i == 0)
				graphics.setColor(Color.blue);
			if(i == 1)
				graphics.setColor(Color.red);
			if(i == 2)
				graphics.setColor(Color.pink);
			if(i == 3)
				graphics.setColor(Color.green);
			
			
			graphics.fillRect(32+5+40, 120 + i * 100+5+4+22,15,15);
			graphics.setColor(Color.black);
		}

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
