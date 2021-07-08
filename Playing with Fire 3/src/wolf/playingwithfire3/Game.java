package wolf.playingwithfire3;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import wolf.playingwithfire3.display.Display;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.input.KeyManager;
import wolf.playingwithfire3.input.MouseManager;
import wolf.playingwithfire3.states.MenuState;
import wolf.playingwithfire3.states.State;
import wolf.playingwithfire3.states.StateManager;

//Hauptklasse
public class Game implements Runnable{
	
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private StateManager stateManager;
	
	private int fps = 60;
	//States
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;	
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		this.stateManager = new StateManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		//alle states erstellen und ersten state setzen

		stateManager.setState(new MenuState(this,stateManager));
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	//tick: Positionen, Variablen werden aktualisiert
	private void tick() {	
		keyManager.tick();
		
		if(stateManager.getState() != null)
			stateManager.getState().tick();
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
		//ab hier malen
		
		if(stateManager.getState() != null)
			stateManager.getState().render(g);
			
		//gemaltes Bild wird im Screen gezeigt
		bs.show();
		g.dispose();
	}	
	

	
	//wird am Anfang gerunnt (Thread) und die Schleife wird gestartet
	public void run() {		
		init();	
		//Game loop: Es soll genau 60 mal in der sekunde getickt werden 
		this.fps = 60;
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
	
	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
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
