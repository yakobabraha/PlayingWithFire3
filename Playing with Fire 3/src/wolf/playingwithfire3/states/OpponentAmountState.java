package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.ui.OpponentAmountButton;
import wolf.playingwithfire3.ui.PlayButton;
import wolf.playingwithfire3.ui.UIManager;

public class OpponentAmountState extends State{

	private Game game;
	private UIManager uiManager;
	
	private int playerAmount;
	
	private StateManager stateManager;
	
	public OpponentAmountState(Game game, int playerAmount, StateManager stateManager) {
		super(game);
		this.game = game;
		this.playerAmount = playerAmount;
		
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		this.stateManager = stateManager;
		
		uiManager.addObject(new OpponentAmountButton(280, 370, 270, 45, Assets.btn_start,game, playerAmount, 1,stateManager));
		uiManager.addObject(new OpponentAmountButton(280, 450, 270, 45, Assets.btn_start,game, playerAmount, 2,stateManager));
		uiManager.addObject(new OpponentAmountButton(280, 530, 270, 45, Assets.btn_start,game, playerAmount, 3,stateManager));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Assets.opponentAmount,0,0,null);
		uiManager.render(graphics);
	}

}
