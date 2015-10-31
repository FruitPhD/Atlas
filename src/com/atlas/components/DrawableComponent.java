package com.atlas.components;

import java.awt.Graphics;

public abstract class DrawableComponent extends Component
{
	void render(Graphics g)
	{
		
	}
	
	boolean isVisible()
	{
		return false;
	}
	
	void makeVisible()
	{
		
	}
	
	void makeInvisible()
	{
		
	}
}
