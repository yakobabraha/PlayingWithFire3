package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.MenuState;
import wolf.playingwithfire3.states.State;
import wolf.playingwithfire3.states.StateManager;

public class QuitButton extends UIImageButton{

	public QuitButton(float x, float y, int width, int height, BufferedImage[] images, Game game,StateManager stateManager) {
		super(x, y, width, height, images, game, " Back to Menu",stateManager);
		this.stateManager = stateManager;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		stateManager.setState(new MenuState(game,stateManager));
	}

}
