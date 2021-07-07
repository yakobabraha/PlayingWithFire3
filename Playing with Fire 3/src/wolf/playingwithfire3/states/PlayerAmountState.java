package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.ui.PlayerAmountButton;
import wolf.playingwithfire3.ui.UIManager;

public class PlayerAmountState extends State{
	
	private Game game;
	private UIManager uiManager;
	
	public PlayerAmountState(Game game) {
		super(game);
		this.game = game;
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new PlayerAmountButton(280, 370, 270, 45, Assets.btn_start,game,1));
		uiManager.addObject(new PlayerAmountButton(280, 450, 270, 45, Assets.btn_start,game,2));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		graphics.fillRect(0, 0, game.width, game.height);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Choose Player Amount", 350, 100);
		uiManager.render(graphics);
	}

}
