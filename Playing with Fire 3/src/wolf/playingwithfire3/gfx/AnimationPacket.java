package wolf.playingwithfire3.gfx;

import java.awt.image.BufferedImage;

public class AnimationPacket {
	
	protected Animation animDown;
	protected Animation animUp;
	protected Animation animLeft;
	protected Animation animRight;
	protected String direction;
	protected boolean moving;
	
	public AnimationPacket(int playerNumber, String skinName) {
		System.out.println(playerNumber);
		switch(skinName) {
		case "default":
			//default Skin
			switch(playerNumber) {
			case 1:
				animDown = new Animation(250,Assets.test_bluePlayer_down);
				animUp = new Animation(250,Assets.test_bluePlayer_up);
				animLeft = new Animation(500,Assets.test_bluePlayer_left);
				animRight = new Animation(500,Assets.test_bluePlayer_right);
				break;
			case 2:
				animDown = new Animation(250,Assets.test_redPlayer_down);
				animUp = new Animation(250,Assets.test_redPlayer_up);
				animLeft = new Animation(500,Assets.test_redPlayer_left);
				animRight = new Animation(500,Assets.test_redPlayer_right);
				break;
			case 3:
				animDown = new Animation(250,Assets.test_purplePlayer_down);
				animUp = new Animation(250,Assets.test_purplePlayer_up);
				animLeft = new Animation(500,Assets.test_purplePlayer_left);
				animRight = new Animation(500,Assets.test_purplePlayer_right);
				break;
			case 4:
				animDown = new Animation(250,Assets.test_greenPlayer_down);
				animUp = new Animation(250,Assets.test_greenPlayer_up);
				animLeft = new Animation(500,Assets.test_greenPlayer_left);
				animRight = new Animation(500,Assets.test_greenPlayer_right);
				break;		
			}
		
		}
		direction = "down";
	}
	
	public void tick() {
		if(moving) {
			animDown.tick();
			animUp.tick();
			animLeft.tick();
			animRight.tick();	
		}
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void directionByMovement(float xMove, float yMove) {
		if(xMove>0) {
			direction = "right";
		}else if(xMove < 0) {
			direction = "left";
		}else if(yMove > 0) {
			direction = "down";
		}else if(yMove < 0) {
			direction = "up";
		}
		moving = !(xMove == 0 && yMove == 0); 
	}
	
	public BufferedImage getCurrentFrame() {
		switch(direction) {
		case "down":
			return animDown.getCurrentFrame();
		case "up":
			return animUp.getCurrentFrame();
		case "left":
			return animLeft.getCurrentFrame();
		case "right":
			return animRight.getCurrentFrame();
		default:
			return animDown.getCurrentFrame();
		}
	}
}
