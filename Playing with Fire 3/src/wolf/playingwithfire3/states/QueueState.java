package wolf.playingwithfire3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import wolf.playingwithfire3.Game;
import wolf.playingwithfire3.online.Client;

public class QueueState extends State{

	private Client client;
	
	public QueueState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		client = new Client();
		client.sendPlayerInfos();
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		graphics.fillRect(0, 0, game.width, game.height);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 25));
		graphics.setColor(Color.YELLOW);
		graphics.drawString("Queue...", 350, 500);
	}

}
