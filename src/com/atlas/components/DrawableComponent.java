package com.atlas.components;

import java.awt.Graphics;

public abstract class DrawableComponent extends Component
{
	boolean visible = true;
	
	public void render(Graphics g)
	{
		
	}
	
	boolean isVisible()
	{
		return visible;
	}
	
	void makeVisible()
	{
		visible = true;
	}
	
	void makeInvisible()
	{
		visible = false;
	}
}
