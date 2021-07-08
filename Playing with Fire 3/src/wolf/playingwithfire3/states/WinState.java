package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.ui.QuitButton;
import wolf.playingwithfire3.ui.QuitButton2;
import wolf.playingwithfire3.ui.UIManager;

public class WinState extends State{

	private Game game;
	private int playerIndex;
	
	private UIManager uiManager;
	
	private StateManager stateManager;
	
	public WinState(Game game, int playerIndex, StateManager stateManager) {
		super(game);
		this.game = game;
		this.playerIndex = playerIndex;
		
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		this.stateManager = stateManager;
		
		uiManager.addObject(new QuitButton2(280, 450, 270, 45, Assets.btn_start, game, stateManager));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		graphics.fillRect(0, 0, game.width, game.height);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		if(playerIndex==0) {
			graphics.setColor(Color.BLUE);
			graphics.drawString("BLUE WON!", 350, 100);
		}
		if(playerIndex==1) {
			graphics.setColor(Color.RED);
			graphics.drawString("RED WON!", 350, 100);
		}
		if(playerIndex==2) {
			graphics.setColor(Color.PINK);
			graphics.drawString("PINK WON!", 350, 100);
		}
		if(playerIndex==3) {
			graphics.setColor(Color.GREEN);
			graphics.drawString("GREEN WON!", 350, 100);
		}		
		uiManager.render(graphics);
	}

}
