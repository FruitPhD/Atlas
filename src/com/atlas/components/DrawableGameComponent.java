package com.atlas.components;

import java.awt.Graphics2D;

public abstract class DrawableGameComponent extends GameComponent
{
	void draw(Graphics2D g)
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
