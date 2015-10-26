package com.atlas;

import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.atlas.components.Button;

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
	
	public List<Key> keys = new ArrayList<Key>();
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void toggleKey(int keyCode, boolean isDown)
	{
		
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
		if (pointer == "play")
		{
			game.state = Game.GameState.Setup;
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
}
