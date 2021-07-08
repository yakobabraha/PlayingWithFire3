package wolf.playingwithfire3.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.states.MenuState;
import wolf.playingwithfire3.states.StateManager;

public class QuitButton extends UIImageButton{

	public QuitButton(float x, float y, int width, int height, BufferedImage[] images, Game game,StateManager stateManager) {
		super(x, y, width, height, images, game, "",stateManager);
		this.stateManager = stateManager;
		// TODO Auto-generated constructor stub
	}
	public void render(Graphics graphics) {
		if(hovering)
			graphics.drawImage(Assets.btn_start[1], (int) x,(int) y, width, height, null);
		else
			graphics.drawImage(Assets.btn_start[0], (int) x,(int) y, width, height, null);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.BOLD, 11));
		graphics.setColor(Color.BLACK);
		graphics.drawString(" Back to menu", (int) x + 16, (int) y + height - 6);
	}
	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new MenuState(game,stateManager));
	}

}
