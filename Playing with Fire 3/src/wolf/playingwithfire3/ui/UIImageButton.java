package wolf.playingwithfire3.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import wolf.playingwithfire3.Game;

public abstract class UIImageButton extends UIObject{

	private BufferedImage[] images;
	private String text;
	
	public UIImageButton(float x, float y, int width, int height, BufferedImage[] images,Game game, String text) {
		super(x, y, width, height,game);
		this.images = images;
		this.text = text;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
		if(hovering)
			graphics.drawImage(images[1], (int) x,(int) y, width, height, null);
		else
			graphics.drawImage(images[0], (int) x,(int) y, width, height, null);
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.BOLD, 16));
		graphics.setColor(Color.BLACK);
		graphics.drawString(text, (int) x + 85, (int) y + height - 17);
	}

	@Override
	public abstract void onClick();

 
	
}
