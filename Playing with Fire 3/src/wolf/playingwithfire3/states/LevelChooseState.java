package wolf.playingwithfire3.states;


// Yakob, Alex, Armin, Leon

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.ui.LevelChooseButton;
import wolf.playingwithfire3.ui.UIManager;

public class LevelChooseState extends State{

	private Game game;
	private UIManager uiManager;
	
	private int playerAmount;
	private int opponentAmount;
	
	private StateManager stateManager;
	
	public LevelChooseState(Game game, int playerAmount, int opponentAmount, StateManager stateManager) {
		super(game);
		this.game = game;
		this.playerAmount = playerAmount;
		this.opponentAmount = opponentAmount;
		
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		this.stateManager = stateManager;
		
		uiManager.addObject(new LevelChooseButton(280, 370, 270, 45, Assets.btn_start,game, playerAmount, opponentAmount, "world1",stateManager));
		uiManager.addObject(new LevelChooseButton(280, 450, 270, 45, Assets.btn_start,game, playerAmount, opponentAmount, "world2",stateManager));
		uiManager.addObject(new LevelChooseButton(280, 530, 270, 45, Assets.btn_start,game, playerAmount, opponentAmount, "world3",stateManager));
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Assets.level,0,0,null);
		uiManager.render(graphics);
	}

}
