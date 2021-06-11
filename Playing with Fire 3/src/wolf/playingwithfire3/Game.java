package wolf.playingwithfire3;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import wolf.playingwithfire3.display.Display;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.gfx.ImageLoader;
import wolf.playingwithfire3.gfx.SpriteSheet;

//Hauptklasse
public class Game implements Runnable{
	
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//test attribute
	private BufferedImage testImage;
	private int posx=-400;
	private SpriteSheet sheet;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;	
	}
	
	private void init() {
		display = new Display(title, width, height);
		Assets.init();
		
	}
	
	//tick: Positionen, Variablen werden aktualisiert
	private void tick() {
	//test
		posx+=5;
		if(posx>1200) {
			posx=-400;
		}
	}
	
	//render: Variablen, die durch tick geändert wurden(z.B. Postionen) werden jetzt zum rendern verwendet
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		
		//Hier malen
		
		//test
		g.fillRect(0, 0, width, height);
		g.drawImage(Assets.shrek,posx,300,null);
		//testend
		
		//gemaltes Bild wird im Screen gezeigt
		bs.show();
		g.dispose();
	}	
	
	//wird am Anfang gerunnt (Thread) und die Schleife wird gestartet
	public void run() {		
		init();	
		//Game loop: Es soll genau 60 mal in der sekunde getickt werden 
		int fps = 60;
		double timePerTick = 1000000000/fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while(running) {
			now = System.nanoTime();
			delta += (now-lastTime)/timePerTick;
			lastTime = now;
			if(delta>=1) {				
				tick();
				render();
				delta--;
			}
		}
		
	}
	
	//thread wird gestartet (in dem läuft der Game Loop)
	public synchronized void start() {
		if(running) 
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//thread wird gestoppt
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
