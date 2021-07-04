package wolf.playingwithfire3.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

	private ArrayList<UIObject> objects;
	
	public UIManager() {
		objects = new ArrayList<UIObject>();
	}
	
	public void tick() {
		for(UIObject o : objects)
			o.tick();
	}
	
	public void render(Graphics graphics) {
		for(UIObject o : objects)
			o.render(graphics);
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o : objects)
			o.onMouseMove(e);
	}
	
	public void onMouseRelease(MouseEvent e){
		for(UIObject o : objects)
			o.onMouseRelease(e);
	}
	
	public void addObject(UIObject o) {
		objects.add(o);
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}
	
}
