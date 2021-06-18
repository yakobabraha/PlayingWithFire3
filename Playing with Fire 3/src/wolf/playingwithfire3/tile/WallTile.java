package wolf.playingwithfire3.tile;

import wolf.playingwithfire3.gfx.Assets;

public class WallTile extends Tile{

	public WallTile(int id) {
		super(Assets.wall, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
}
