package core;

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

import components.Button;

public class InputHandler implements KeyListener, MouseListener
{
	private Game game;
	private List<Button> buttons;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	public InputHandler(Game game)
	{
		this.game = game;
		buttons = new ArrayList<Button>();
		game.addKeyListener(this);
		game.addMouseListener(this);
	}
	
	public void getInput()
	{
		mouseX = MouseInfo.getPointerInfo().getLocation().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y;
	}
	
	public void addButton(Button button)
	{
		if (button != null && !buttons.contains(button))
		{
			Log.info("Added button: " + button.getName());
			buttons.add(button);
		}
	}
	
	public List<Button> getButtons()
	{
		return buttons;
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
		
		Iterator<Button> i = buttons.iterator();
		while (i.hasNext())
		{
			Button button = (Button) i.next();
			if (button.inRegion(mouseX, mouseY) && button.isActive())
				pointer = button.act();
		}
		
		if (pointer == "quit")
			game.stop();
		else if (pointer == "about")
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
		else if (pointer == "settings")
			game.state = Game.GameState.Options;
		else if (pointer == "play");
			game.state = Game.GameState.Setup;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		mouseAction();
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
