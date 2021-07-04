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
	
	public OpponentAmountState(Game game, int playerAmount) {
		super(game);
		this.game = game;
		this.playerAmount = playerAmount;
		
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new OpponentAmountButton(300, 300, 180, 22, Assets.btn_start,game, playerAmount, 1));
		uiManager.addObject(new OpponentAmountButton(300, 340, 180, 22, Assets.btn_start,game, playerAmount, 2));
		uiManager.addObject(new OpponentAmountButton(300, 380, 180, 22, Assets.btn_start,game, playerAmount, 3));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics graphics) {
		graphics.fillRect(0, 0, game.width, game.height);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Choose Opponent Amount", 350, 100);
		uiManager.render(graphics);
	}

}
