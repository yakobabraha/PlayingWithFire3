package wolf.playingwithfire3.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static int width = 32, height = 32;
	
	public static BufferedImage shrek, amogus;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
		
		shrek = sheet.crop(0, 0, width, height);
		amogus = sheet.crop(width, 0, width, height);
	}
	
}
