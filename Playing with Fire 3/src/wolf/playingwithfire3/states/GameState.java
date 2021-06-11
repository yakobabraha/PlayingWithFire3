package wolf.playingwithfire3.states;

import java.awt.Graphics;

import wolf.playingwithfire3.gfx.Assets;

public class GameState extends State{

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.amogus,40,200, null);
		g.drawImage(Assets.amogus,500,500, null);
		g.drawImage(Assets.amogus,150,170, null);
	}
	
}
