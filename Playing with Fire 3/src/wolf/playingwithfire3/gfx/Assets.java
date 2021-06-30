package wolf.playingwithfire3.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static int width = 45, height = 45;
	
	public static BufferedImage whiteDog, yellowDog, yellowDogFace, whiteDogFace;
	public static BufferedImage floor, box, wall;
	public static BufferedImage redBomb,bombExplosion;
	public static BufferedImage logo, heart;
	
	public static BufferedImage[] default_bluePlayer_down;
	public static BufferedImage[] default_bluePlayer_right;
	public static BufferedImage[] default_bluePlayer_left;
	public static BufferedImage[] default_bluePlayer_up;
	
	public static BufferedImage[] default_redPlayer_down;
	public static BufferedImage[] default_redPlayer_right;
	public static BufferedImage[] default_redPlayer_left;
	public static BufferedImage[] default_redPlayer_up;
	
	public static BufferedImage[] default_purplePlayer_down;
	public static BufferedImage[] default_purplePlayer_right;
	public static BufferedImage[] default_purplePlayer_left;
	public static BufferedImage[] default_purplePlayer_up;
	
	public static BufferedImage[] default_greenPlayer_down;
	public static BufferedImage[] default_greenPlayer_right;
	public static BufferedImage[] default_greenPlayer_left;
	public static BufferedImage[] default_greenPlayer_up;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
		//test
		whiteDog = sheet.crop(0, 2*height, width, height);
		yellowDog = sheet.crop(0, height, width, height);
		whiteDogFace = sheet.crop(3*width, 0, width, height);
		yellowDogFace = sheet.crop(width, height, width, height);
		
		floor = sheet.crop(0, 0, width, height);
		box = sheet.crop(width, 0, width, height);
		wall = sheet.crop(2*width, 0, width, height);
		
		redBomb = sheet.crop(2*width, height, width, height);
		bombExplosion = sheet.crop(3*width, height, width, height);
		
		logo = sheet.crop(width, 2* height, 116, 87);
		heart = sheet.crop(0, 3 * height, 24, 24);
		//players
		default_bluePlayer_down = new BufferedImage[2];
		default_bluePlayer_up = new BufferedImage[2];
		default_bluePlayer_right = new BufferedImage[1];
		default_bluePlayer_left = new BufferedImage[1];
		
		default_bluePlayer_down[0] = sheet.crop(width * 4, height * 0, width, height);
		default_bluePlayer_down[1] = sheet.crop(width * 5, height * 0, width, height);
		default_bluePlayer_up[0] = sheet.crop(width * 6, height * 0, width, height);
		default_bluePlayer_up[1] = sheet.crop(width * 7, height * 0, width, height);
		default_bluePlayer_left[0] = sheet.crop(width * 8, height * 0, width, height);
		default_bluePlayer_right[0] = sheet.crop(width * 9, height * 0, width, height);
		
		default_redPlayer_down = new BufferedImage[2];
		default_redPlayer_up = new BufferedImage[2];
		default_redPlayer_right = new BufferedImage[1];
		default_redPlayer_left = new BufferedImage[1];
		
		default_redPlayer_down[0] = sheet.crop(width * 4, height * 1, width, height);
		default_redPlayer_down[1] = sheet.crop(width * 5, height * 1, width, height);
		default_redPlayer_up[0] = sheet.crop(width * 6, height * 1, width, height);
		default_redPlayer_up[1] = sheet.crop(width * 7, height * 1, width, height);
		default_redPlayer_left[0] = sheet.crop(width * 8, height * 1, width, height);
		default_redPlayer_right[0] = sheet.crop(width * 9, height * 1, width, height);

		default_purplePlayer_down = new BufferedImage[2];
		default_purplePlayer_up = new BufferedImage[2];
		default_purplePlayer_right = new BufferedImage[1];
		default_purplePlayer_left = new BufferedImage[1];
		
		default_purplePlayer_down[0] = sheet.crop(width * 4, height * 2, width, height);
		default_purplePlayer_down[1] = sheet.crop(width * 5, height * 2, width, height);
		default_purplePlayer_up[0] = sheet.crop(width * 6, height * 2, width, height);
		default_purplePlayer_up[1] = sheet.crop(width * 7, height * 2, width, height);
		default_purplePlayer_left[0] = sheet.crop(width * 8, height * 2, width, height);
		default_purplePlayer_right[0] = sheet.crop(width * 9, height * 2, width, height);
		
		default_greenPlayer_down = new BufferedImage[2];
		default_greenPlayer_up = new BufferedImage[2];
		default_greenPlayer_right = new BufferedImage[1];
		default_greenPlayer_left = new BufferedImage[1];
		
		default_greenPlayer_down[0] = sheet.crop(width * 4, height * 3, width, height);
		default_greenPlayer_down[1] = sheet.crop(width * 5, height * 3, width, height);
		default_greenPlayer_up[0] = sheet.crop(width * 6, height * 3, width, height);
		default_greenPlayer_up[1] = sheet.crop(width * 7, height * 3, width, height);
		default_greenPlayer_left[0] = sheet.crop(width * 8, height * 3, width, height);
		default_greenPlayer_right[0] = sheet.crop(width * 9, height * 3, width, height);
		
		
		//testend
	}
	
}
