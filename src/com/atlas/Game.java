package com.atlas;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.atlas.resources.Render;
import com.atlas.resources.SpriteLoader;

/**
 * Main class for ATLAS. Runs the game engine and all important functions
 * 
 * @author Jonathan Davis Copyright 2015
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
	private long ticks = 0;
	
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
		input = new InputHandler(this);
		renderer = new Render(this, window);
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
				ticks++;
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
			break;
		case Options:
			break;
		case Setup:
			break;
		case Store:
			break;
		case Play:
			break;
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
			break;
		case Options:
			renderer.renderOptions(g);
			break;
		case Setup:
			renderer.renderSetup(g);
			break;
		case Store:
			renderer.renderStore(g);
			break;
		case Play:
			renderer.renderGame(g);
			break;
		}
		
		g.dispose();
		bs.show();
	}
	
	private void getInput()
	{
		input.getInput();
	}
	
	public long getTicks()
	{
		return ticks;
	}
	
	public enum GameState
	{
		Menu, Options, Setup, Store, Play
	}
}
