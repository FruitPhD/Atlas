package com.atlas;

import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.atlas.graphics.Button;

public class InputHandler implements KeyListener, MouseListener
{
	private Game game;
	private List<Button> menuButtons;
	private List<Button> settingsButtons;
	private List<Button> setupButtons;
	private List<Button> storeButtons;
	private List<Button> pauseButtons;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	public InputHandler(Game game)
	{
		this.game = game;
		menuButtons = new ArrayList<Button>();
		settingsButtons = new ArrayList<Button>();
		setupButtons = new ArrayList<Button>();
		storeButtons = new ArrayList<Button>();
		pauseButtons = new ArrayList<Button>();
		game.addKeyListener(this);
		game.addMouseListener(this);
		setupKeys();
	}
	
	public void getInput()
	{
		mouseX = MouseInfo.getPointerInfo().getLocation().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y;
	}
	
	public void addMenuButton(Button button)
	{
		if (button != null && !menuButtons.contains(button))
		{
			Log.info("Added button: " + button.getName() + ", Pointer: " + button.act());
			menuButtons.add(button);
		}
	}
	
	public List<Button> getMenuButtons()
	{
		return menuButtons;
	}
	
	public void addSettingsButton(Button button)
	{
		if (button != null && !settingsButtons.contains(button))
		{
			Log.info("Added button: " + button.getName() + ", Pointer: " + button.act());
			settingsButtons.add(button);
		}
	}
	
	public List<Button> getSettingsButtons()
	{
		return settingsButtons;
	}
	
	public void addSetupButton(Button button)
	{
		if (button != null && !setupButtons.contains(button))
		{
			Log.info("Added button: " + button.getName() + ", Pointer: " + button.act());
			setupButtons.add(button);
		}
	}
	
	public List<Button> getSetupButtons()
	{
		return setupButtons;
	}
	
	public void addStoreButton(Button button)
	{
		if (button != null && !storeButtons.contains(button))
		{
			Log.info("Added button: " + button.getName() + ", Pointer: " + button.act());
			storeButtons.add(button);
		}
	}
	
	public List<Button> getStoreButtons()
	{
		return storeButtons;
	}
	
	public void addPauseButton(Button button)
	{
		if (button != null && !pauseButtons.contains(button))
		{
			Log.info("Added button: " + button.getName() + ", Pointer: " + button.act());
			pauseButtons.add(button);
		}
	}
	
	public List<Button> getPauseButtons()
	{
		return pauseButtons;
	}
	
	
	
	
	
	public class Key
	{
		public boolean down = false;
		
		public void toggle(boolean isDown)
		{
			down = isDown;
		}
	}
	
	public HashMap<Character, Key> keys = new HashMap<Character, Key>();
	
	private void setupKeys()
	{
		keys.put(' ', new Key());
		keys.put('w', new Key());
		keys.put('a', new Key());
		keys.put('s', new Key());
		keys.put('d', new Key());
		keys.put('r', new Key());
		keys.put('f', new Key());
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == ' ')
			toggleKey(' ', true);
		if (e.getKeyChar() == 'w')
			toggleKey('w', true);
		if (e.getKeyChar() == 'a')
			toggleKey('a', true);
		if (e.getKeyChar() == 's')
			toggleKey('s', true);
		if (e.getKeyChar() == 'd')
			toggleKey('d', true);
		if (e.getKeyChar() == 'r')
			toggleKey('r', true);
		if (e.getKeyChar() == 'f')
			toggleKey('f', true);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyChar() == ' ')
			toggleKey(' ', false);
		if (e.getKeyChar() == 'w')
			toggleKey('w', false);
		if (e.getKeyChar() == 'a')
			toggleKey('a', false);
		if (e.getKeyChar() == 's')
			toggleKey('s', false);
		if (e.getKeyChar() == 'd')
			toggleKey('d', false);
		if (e.getKeyChar() == 'r')
			toggleKey('r', false);
		if (e.getKeyChar() == 'f')
			toggleKey('f', false);
	}
	
	public void toggleKey(char keyCode, boolean isDown)
	{
		keys.get(keyCode).toggle(isDown);
	}
	
	
	
	
	
	public void mouseAction()
	{
		String pointer = "";
		Iterator<Button> i = null;
		
		switch(game.state)
		{
		case Menu:
			i = menuButtons.iterator();
			break;
		case Options:
			i = settingsButtons.iterator();
			break;
		case Setup:
			i = setupButtons.iterator();
			break;
		case Store:
			i = storeButtons.iterator();
			break;
		case Play:
			i = pauseButtons.iterator();
			break;
		}
		
		while (i.hasNext())
		{
			Button button = (Button) i.next();
			if (button.inRegion(mouseX, mouseY) && button.isActive())
			{
				pointer = button.act();
			}
		}
		
		if (pointer == "quit")
		{
			game.stop();
		}
		if (pointer == "about")
		{
			if (Desktop.isDesktopSupported())
			{
				try {
					Desktop.getDesktop().browse(new URI(Reference.WEBSITE));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (pointer == "settings")
		{
			game.state = Game.GameState.Options;
		}
		if (pointer == "setup")
		{
			game.state = Game.GameState.Setup;
		}
		
		// TODO Options
		
		if (pointer == "store")
		{
			game.state = Game.GameState.Store;
		}
		if (pointer == "red")
		{
			game.changePlayer(1);
		}
		if (pointer == "green")
		{
			game.changePlayer(2);
		}
		if (pointer == "blue")
		{
			game.changePlayer(3);
		}
		if (pointer == "yellow")
		{
			game.changePlayer(4);
		}
		if (pointer == "white")
		{
			game.changePlayer(5);
		}
		if (pointer == "black")
		{
			game.changePlayer(6);
		}
		if (pointer == "menu")
		{
			game.state = Game.GameState.Menu;
		}
		
		if (pointer == "start")
		{
			game.map.generate();
			game.setupGameComponents();
			game.state = Game.GameState.Play;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		mouseAction();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public int getTankInput(int index)
	{
		if (index == game.getTurn() && game.isReady())
		{
			if (keys.get(' ').down)
				return 0;
			if (keys.get('a').down)
				return 1;
			if (keys.get('d').down)
				return 2;
			if (keys.get('w').down)
				return 3;
			if (keys.get('s').down)
				return 4;
			if (keys.get('r').down)
				return 5;
			if (keys.get('f').down)
				return 6;
		}
		
		return -1;
	}
}
