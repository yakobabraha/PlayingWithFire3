package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.MenuState;
import wolf.playingwithfire3.states.State;

public class QuitButton extends UIImageButton{

	public QuitButton(float x, float y, int width, int height, BufferedImage[] images, Game game) {
		super(x, y, width, height, images, game, "Back to Menu");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		State.setState(new MenuState(game));
	}

}
