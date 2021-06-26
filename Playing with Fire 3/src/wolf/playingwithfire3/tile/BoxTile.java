package wolf.playingwithfire3.tile;

import java.awt.image.BufferedImage;

import wolf.playingwithfire3.gfx.Assets;

public class BoxTile extends Tile{

	public BoxTile(int id) {
		super(Assets.box, id);

	}
	@Override
	public boolean isSolid() {
		return true;
	}
	public boolean isDestructible() {
		return true;
	}
}
