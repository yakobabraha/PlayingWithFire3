package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.PlayerAmountState;
import wolf.playingwithfire3.states.State;

public class PlayButton extends UIImageButton{

	private Game game;
	
	public PlayButton(float x, float y, int width, int height, BufferedImage[] images, Game game) {
		super(x, y, width, height, images, game);
		this.game = game;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		State.setState(new PlayerAmountState(game));
	}

}
