package wolf.playingwithfire3;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import wolf.playingwithfire3.display.Display;
import wolf.playingwithfire3.gfx.Assets;
import wolf.playingwithfire3.input.KeyManager;
import wolf.playingwithfire3.sound.AudioPlayer;
import wolf.playingwithfire3.states.GameState;
import wolf.playingwithfire3.states.State;

//Hauptklasse
public class Game implements Runnable{
	
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	
	private int fps = 60;
	//States
	private State gameState;
	
	//Input
	private KeyManager keyManager;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;	
		keyManager = new KeyManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		
		Assets.init();
		
		//alle states erstellen und ersten state setzen
		gameState = new GameState(this);
		State.setState(gameState);
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	//tick: Positionen, Variablen werden aktualisiert
	private void tick() {	
		keyManager.tick();
		
		if(State.getState() != null)
			State.getState().tick();
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
		
		if(State.getState() != null)
			State.getState().render(g);
			
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
