package com.atlas;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import com.atlas.components.Component;
import com.atlas.components.DrawableComponent;
import com.atlas.components.Shell;
import com.atlas.components.Tank;
import com.atlas.graphics.Render;
import com.atlas.graphics.SpriteLoader;

/**
 * Main class for ATLAS. Runs the game engine and all important functions
 * 
 * @author Jonathan Davis Copyright 2015
 * 
 */

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable
{
	public JFrame window;
	public static int width = 640;
	public static int height = 480;
	
	public GameState state;
	private boolean running = false;
	private long ticks = 0;
	private boolean ready;
	private boolean componentsAddUpdate = false;
	private boolean componentsDeleteUpdate = false;
	
	public Game instance;
	public Render renderer;
	public InputHandler input;
	public SpriteLoader spriteLoader;
	
	public Map map;
	public PlayerType[] players = {PlayerType.NONE, PlayerType.NONE, PlayerType.NONE,
									PlayerType.NONE, PlayerType.NONE, PlayerType.NONE};
	public List<Component> components = new ArrayList<Component>();
	
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
		renderer = new Render(this);
		map = new Map(this);
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
		
		int readycount = 0;
		int updates = 0;
		int frames = 0;
		int fps = 0;
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
				frames++;
				render(fps);
				getInput();
			}
			
			if (System.currentTimeMillis() - lastRun >= 1000)
			{
				lastRun += 1000;
				
				if (readycount >= 1)
				{
					ready = true;
					readycount = 0;
				}
				else if (ready == false)
				{
					readycount++;
				}
				
				fps = frames;
				
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
			removeQueuedComponents();
			addQueuedComponents();
			for (Component c: components)
				c.update(ticks);
			break;
		}
	}
	
	private void render(int fps)
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
			for (Component c: components)
			{
				if (c instanceof DrawableComponent)
				{
					((DrawableComponent) c).render(g);
				}
			}
			break;
		}
		
		g.drawString("FPS: " + fps, 10, 15);
		
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
	
	/** Cycles the given player (1 - 6) through types NONE, HUMAN, CPU */
	public void changePlayer(int player)
	{
		if (player < 1 || player > 6) return;
		
		PlayerType type = players[player - 1];
		
		switch(type)
		{
		case NONE:
			players[player - 1] = PlayerType.HUMAN;
			break;
		case HUMAN:
			players[player - 1] = PlayerType.CPU;
			break;
		case CPU:
			players[player - 1] = PlayerType.NONE;
		}
	}
	
	public enum GameState
	{
		Menu, Options, Setup, Store, Play
	}
	
	public enum PlayerType
	{
		NONE("player_none"), HUMAN("player_human"), CPU("player_cpu");
		
		String texture;
		
		private PlayerType(String texture)
		{
			this.texture = texture;
		}
	}
	
	public String getTexture(PlayerType playerType)
	{
		switch(playerType)
		{
		case NONE:
			return "player_none";
		case HUMAN:
			return "player_human";
		case CPU:
			return "player_cpu";
		default:
			return "player_none";
		}
	}
	
	private int turn;
	private int playerCount = 0;
	private List<Component> queuedComponents = new ArrayList<Component>();
	private List<Component> queuedDeletedComponents = new ArrayList<Component>();
	
	public void setupGameComponents()
	{
		// Create the AI and Player tanks
		for (int i = 0; i < players.length; i++)
		{
			if (players[i] != PlayerType.NONE)
			{
				int[] terrain = map.getTerrain();
				int x = window.getWidth() / 8 + (int) (Math.random() * 1000);
				int y = window.getHeight() - terrain[x];
				
				Log.info("Created tank - Index: " + playerCount);
				components.add(new Tank(x, playerCount, players[i], this));
				playerCount++;
			}
		}
		turn = (int) (Math.random() * (playerCount));
	}
	
	public void addComponent(Component c)
	{
		if (!components.contains(c))
		{
			componentsAddUpdate = true;
			queuedComponents.add(c);
		}
	}
	
	private void addQueuedComponents()
	{
		if (componentsAddUpdate == false) return;
		
		for (Component c: queuedComponents)
		{
			components.add(c);
		}
		
		for (int i = 0; i < queuedComponents.size(); i++)
		{
			queuedComponents.remove(i);
		}
		
		componentsAddUpdate = false;
	}
	
	public void removeComponent(Component c)
	{
		if (components.contains(c))
		{
			componentsDeleteUpdate = true;
			queuedDeletedComponents.add(c);
		}
	}
	
	private void removeQueuedComponents()
	{
		if (componentsDeleteUpdate == false) return;
		
		for (Component c: queuedDeletedComponents)
		{
			components.remove(c);
		}
		
		for (int i = 0; i < queuedDeletedComponents.size(); i++)
		{
			queuedDeletedComponents.remove(i);
		}
		
		componentsDeleteUpdate = false;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public void endTurn()
	{
		int turns = playerCount - 1;
		if (turn + 1 > turns)
		{
			turn = 0;
		}
		else
		{
			turn++;
		}
		
		ready = false;
	}
	
	public boolean isReady()
	{
		return ready;
	}
}
