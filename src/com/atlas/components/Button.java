package com.atlas.components;

import java.awt.Graphics;
import java.awt.Image;

import com.atlas.Reference;
import com.atlas.resources.SpriteLoader;

public class Button
{
	private Image image;
	private int x;
	private int y;
	private int width;
	private int height;
	private String pointer;
	private String name;
	private boolean active;
	
	public Button(int x, int y, String pointer, String name)
	{
		this.image = SpriteLoader.getSingleton().loadSprite("button").getImage();
		this.x = x;
		this.y = y;
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
		this.pointer = pointer;
		this.name = name;
		this.active = true;
	}
	
	public Button(Image image, int x, int y, String pointer, String name)
	{
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
		this.pointer = pointer;
		this.name = name;
		this.active = true;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, x, y, null);
	}
	
	/** Returns true if the mouse is in the button's region */
	public boolean inRegion(int mouseX, int mouseY)
	{
		if ((mouseX >= x && mouseX <= width + x) && (mouseY >= y && mouseY <= height + y))
			return true;
		else
			return false;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void enable()
	{
		active = true;
	}
	
	public void disable()
	{
		active = false;
	}
	
	public String act()
	{
		return pointer;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
