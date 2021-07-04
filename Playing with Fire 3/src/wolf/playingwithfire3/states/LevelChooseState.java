package wolf.playingwithfire3.states;

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
	
	public LevelChooseState(Game game, int playerAmount, int opponentAmount) {
		super(game);
		this.game = game;
		this.playerAmount = playerAmount;
		this.opponentAmount = opponentAmount;
		
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new LevelChooseButton(300, 300, 180, 22, Assets.btn_start,game, playerAmount, opponentAmount, "world1"));
		uiManager.addObject(new LevelChooseButton(300, 340, 180, 22, Assets.btn_start,game, playerAmount, opponentAmount, "world2"));
		uiManager.addObject(new LevelChooseButton(300, 380, 180, 22, Assets.btn_start,game, playerAmount, opponentAmount, "world3"));
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
		graphics.drawString("Choose Level", 350, 100);
		uiManager.render(graphics);
	}

}