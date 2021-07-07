package wolf.playingwithfire3.ui;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.states.OpponentAmountState;
import wolf.playingwithfire3.states.QueueState;
import wolf.playingwithfire3.states.State;

public class PlayOnlineButton extends UIImageButton{

	public PlayOnlineButton(float x, float y, int width, int height, BufferedImage[] images, Game game, String text) {
		super(x, y, width, height, images, game, text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		game.getMouseManager().setUIManager(null);
		State.setState(new QueueState(game));
		
	}

}
