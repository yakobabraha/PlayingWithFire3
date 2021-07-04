package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.ui.PlayButton;
import wolf.playingwithfire3.ui.UIImageButton;
import wolf.playingwithfire3.ui.UIManager;

public class MenuState extends State{

	private Game game;
	private UIManager uiManager;
	
	
	public MenuState(Game game) {
		super(game);
		this.game = game;
		uiManager = new UIManager();
		game.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new PlayButton(300, 300, 180, 22, Assets.btn_start,game));
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		uiManager.tick();
	}

	@Override
	public void render(Graphics graphics) {
		graphics.fillRect(0, 0, game.width, game.height);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Menu", 350, 100);
		uiManager.render(graphics);
		//graphics.drawImage(Assets.btn_start[0], 300, 300, null);
	}

}