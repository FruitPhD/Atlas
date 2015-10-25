package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener
{
	public InputHandler(Game game)
	{
		game.addKeyListener(this);
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void toggleKey(int keyCode, boolean isDown)
	{
		
	}
}
