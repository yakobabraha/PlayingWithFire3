package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.ui.PlayButton;
import wolf.playingwithfire3.ui.PlayOnlineButton;
import wolf.playingwithfire3.ui.UIImageButton;
import wolf.playingwithfire3.ui.UIManager;

public class MenuState extends State{

	private Game game;
	private UIManager uiManager;
	
	private StateManager stateManager;
	
	
	public MenuState(Game game, StateManager stateManager) {
		super(game);
		this.game = game;
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		this.stateManager = stateManager;
		
		uiManager.addObject(new PlayButton(280, 450, 270, 45, Assets.btn_start,game,stateManager));
		uiManager.addObject(new PlayOnlineButton(280, 530, 270, 45, Assets.btn_start,game, "  Play Online",stateManager));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Assets.background,0,0,null);
		uiManager.render(graphics);
		//graphics.drawImage(Assets.btn_start[0], 300, 300, null);
	}

}