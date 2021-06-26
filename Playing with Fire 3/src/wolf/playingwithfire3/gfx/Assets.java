package wolf.playingwithfire3.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static int width = 45, height = 45;
	
	public static BufferedImage whiteDog, yellowDog;
	public static BufferedImage floor, box, wall;
	public static BufferedImage redBomb,bombExplosion;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
		
		whiteDog = sheet.crop(0, 2*height, width, height);
		yellowDog = sheet.crop(0, height, width, height);
		
		floor = sheet.crop(0, 0, width, height);
		box = sheet.crop(width, 0, width, height);
		wall = sheet.crop(2*width, 0, width, height);
		
		redBomb = sheet.crop(2*width, height, width, height);
		bombExplosion = sheet.crop(3*width, height, width, height);
		
	}
	
}