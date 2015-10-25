package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import core.Game;
import core.Log;

public class Render
{
	private Game game;
	private JFrame window;
	
	private SpriteLoader spriteLoader;
	
	public Render(Game game, JFrame window)
	{
		this.game = game;
		this.window = window;
		this.spriteLoader = game.spriteLoader;
	}
	
	public void renderMenu(Graphics g)
	{
		drawRect(0, 0, window.getWidth() / 32, window.getHeight() / 32, 0, g);
		drawRect(0, (int) (window.getHeight() / 32 * (5.0f/ 8)), window.getWidth() / 32, 1, 2, g);
		drawRect(0, (int) (window.getHeight() / 32 * (5.0f/ 8)) + 1, window.getWidth() / 32, (int) (window.getHeight() / 32 * (3.0f/ 8)) - 1, 1, g);
	}
	
	/**
	 * Draws a given sprite to the screen at the specified tile location
	 * 
	 * @param x - the x tile position of the screen to draw the sprite to
	 * @param y - the y tile position of the screen to draw the sprite to
	 * @param index - the index of the sprite
	 * @param g - the graphics to draw to
	 */
	private void draw(int x, int y, int index, Graphics g)
	{
		g.drawImage(spriteLoader.loadSprite(index).getImage(), x * 32, y * 32, null);
	}
	
	/**
	 * Draws a rectangle filled with the given sprite at the given tile location with the given dimensions
	 * 
	 * @param x - x tile location
	 * @param y - y tile location
	 * @param width - the width in tiles
	 * @param height - the height in tiles
	 * @param index - the index of the sprite
	 * @param g - the graphics to draw to
	 */
	private void drawRect(int x, int y, int width, int height, int index, Graphics g)
	{
		for (int i = y; i < y + height; i++)
		{
			for (int j = x; j < x + width; j++)
			{
				draw(j, i, index, g);
			}
		}
	}
}
