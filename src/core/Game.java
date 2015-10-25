package core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import graphics.Render;
import graphics.SpriteLoader;

/**
 * Main class for ATLAS. Runs the game engine and all important functions
 * 
 * @author Jonathan Davis
 *
 */

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable
{
	private JFrame window;
	public static int width = 640;
	public static int height = 480;
	
	public GameState state;
	private boolean running = false;
	
	public Game instance;
	public Render renderer;
	public InputHandler input;
	public SpriteLoader spriteLoader;
	
	public Game()
	{
		Log.init();
		
		Log.info("Starting " + Reference.NAME + " " + Reference.VERSION);
		
		instance = this;
		
		Log.info("Setting up JFrame");
		
		
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		window = new JFrame(Reference.NAME + " " + Reference.VERSION);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setIgnoreRepaint(true);
		window.setResizable(false);
		window.setUndecorated(true);
		
		window.setLayout(new BorderLayout());
		window.add(this, BorderLayout.CENTER);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		init();
	}
	
	public static void main(String[] args)
	{
		new Game().start();
	}
	
	public void init()
	{
		Log.info("Performing init");
		state = GameState.Menu;
		spriteLoader = SpriteLoader.getSingleton();
		renderer = new Render(this, window);
		input = new InputHandler(this);
	}
	
	public void start()
	{
		new Thread(this).start();
		
		running = true;
	}
	
	public void stop()
	{
		Log.info("Stopping " + Reference.NAME + " " + Reference.VERSION);
		running = false;
		System.exit(0);
	}
	
	@Override
	public void run()
	{
		Log.info("Setting up game loop");
		
		long lastRun = System.currentTimeMillis();
		long oldTime = System.nanoTime();
		
		int updates = 0;
		int frames = 0;
		double unprocessed = 0;
		boolean render = false;
		
		while (running)
		{
			long time = System.nanoTime();
			unprocessed += (time - oldTime) / Reference.NANOSECONDS_PER_TICK;
			oldTime = time;
			render = false;
			
			while (unprocessed > 0)
			{
				update();
				updates++;
				unprocessed -= 1;
				
				render = true;	// Render once done updating
			}
			
			// Prevents too many renders
			if (render)
			{
				render();
				frames++;
				getInput();
			}
			
			if (System.currentTimeMillis() - lastRun >= 1000)
			{
				lastRun += 1000;
				System.out.println("Updates: " + updates + ", Frames: " + frames);
				
				updates = 0;
				frames = 0;
			}
		}
	}
	
	private void update()
	{
		switch(state)
		{
		case Menu:
			
		case Options:
			
		case Setup:
			
		case Store:
			
		case Play:
			
		}
	}
	
	private void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		switch(state)
		{
		case Menu:
			renderer.renderMenu(g);
		case Options:
			
		case Setup:
			
		case Store:
			
		case Play:
			
		}
		
		g.dispose();
		bs.show();
	}
	
	private void getInput()
	{
		
	}
	
	private enum GameState
	{
		Menu, Options, Setup, Store, Play
	}
}
