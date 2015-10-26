package com.atlas.resources;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;

import com.atlas.Game;
import com.atlas.InputHandler;
import com.atlas.components.Button;
import com.atlas.helper.MathHelper;

public class Render
{
	private Game game;
	private JFrame window;
	private InputHandler input;
	
	private SpriteLoader spriteLoader;
	
	public Render(Game game, JFrame window)
	{
		this.game = game;
		this.window = window;
		this.spriteLoader = game.spriteLoader;
		this.input = game.input;
		
		init();
	}
	
	private void init()
	{
		int x = window.getWidth() / 2 - 256;
		int y = window.getHeight() / 2 - 64;
		Button play = new Button(x, y, "play", "Play");
		Button settings = new Button(x, y += 90, "settings", "Settings");
		Button about = new Button(x, y += 90, "about", "About");
		Button quit = new Button(x, y += 90, "quit", "Quit");
		
		input.addMenuButton(play);
		input.addMenuButton(settings);
		input.addMenuButton(about);
		input.addMenuButton(quit);
		
		
		
		x = window.getWidth() / 2 - 256;
		y = window.getHeight() / 4 - 32;
		Button red = new Button(spriteLoader.getSingleton().loadSprite(16).getImage(), x, y, "red", "");
		Button green = new Button(spriteLoader.getSingleton().loadSprite(32).getImage(), x, y += 60, "green", "");
		Button blue = new Button(spriteLoader.getSingleton().loadSprite(48).getImage(), x, y += 60, "blue", "");
		Button yellow = new Button(spriteLoader.getSingleton().loadSprite(64).getImage(), x, y += 60, "yellow", "");
		Button white = new Button(spriteLoader.getSingleton().loadSprite(80).getImage(), x, y += 60, "white", "");
		Button black = new Button(spriteLoader.getSingleton().loadSprite(96).getImage(), x, y += 60, "black", "");
		Button go = new Button(x, y += 90, "go", "Go!");
		Button back = new Button(x, y += 180, "back", "Return");
		
		input.addSetupButton(red);
		input.addSetupButton(green);
		input.addSetupButton(blue);
		input.addSetupButton(yellow);
		input.addSetupButton(white);
		input.addSetupButton(black);
		input.addSetupButton(go);
		input.addSetupButton(back);
	}
	
	
	
	
	
	public void renderMenu(Graphics g)
	{
		drawTileRect(0, 0, window.getWidth() / 32, window.getHeight() / 32, 0, g);
		drawTileRect(0, (int) (window.getHeight() / 32 * (5.0f / 8)), window.getWidth() / 32, 1, 2, g);
		drawTileRect(0, (int) (window.getHeight() / 32 * (5.0f / 8)) + 1, window.getWidth() / 32, (int) (window.getHeight() / 32 * (3.0f/ 8)) - 1, 1, g);
		draw(window.getWidth() / 5, 192 + (int) (16 * Math.sin(game.getTicks() / 50.0)), "title", g);
		
		draw(window.getWidth() / 5, (int) (window.getHeight() * (5.0f / 8) - 32), 7, g);
		
		draw(0 + MathHelper.clamp((int) (-64 + game.getTicks() / 5 % 2147483647), -64, window.getWidth()),
				(int) (window.getHeight() * (5.0f / 8) - 32), 114, g);
		draw(0 + MathHelper.clamp((int) (-64 + game.getTicks() / 5 % 2147483647), -64, window.getWidth()),
				(int) (window.getHeight() * (5.0f / 8) - 32), (int) (16 + game.getTicks() / 5 % 2), g);
		
		
		
		Iterator<Button> i = input.getMenuButtons().iterator();
		while (i.hasNext())
		{
			Button button = i.next();
			String name = button.getName();
			
			button.draw(g);
			draw(name, button.getX() + 32, button.getY() + button.getHeight() / 4, g);
		}
	}
	
	public void renderOptions(Graphics g)
	{
		drawTileRect(0, 0, window.getWidth() / 32, window.getHeight() / 32, 1, g);
	}
	
	public void renderSetup(Graphics g)
	{
		drawTileRect(0, 0, window.getWidth() / 32, window.getHeight() / 32, 0, g);
		drawTileRect(0, (int) (window.getHeight() / 32 * (5.0f / 8)), window.getWidth() / 32, 1, 2, g);
		drawTileRect(0, (int) (window.getHeight() / 32 * (5.0f / 8)) + 1, window.getWidth() / 32, (int) (window.getHeight() / 32 * (3.0f/ 8)) - 1, 1, g);
		draw(window.getWidth() / 5, 96 + (int) (16 * Math.sin(game.getTicks() / 50.0)), "setup", g);
		
		draw(window.getWidth() / 5, (int) (window.getHeight() * (5.0f / 8) - 32), 7, g);
		
		draw(0 + MathHelper.clamp((int) (-64 + game.getTicks() / 5 % 2147483647), -64, window.getWidth()),
				(int) (window.getHeight() * (5.0f / 8) - 32), 114, g);
		draw(0 + MathHelper.clamp((int) (-64 + game.getTicks() / 5 % 2147483647), -64, window.getWidth()),
				(int) (window.getHeight() * (5.0f / 8) - 32), (int) (16 + game.getTicks() / 5 % 2), g);
		
		
		
		for (int i = 0; i < 6; i++)
		{
			draw(window.getWidth() / 2 - 256, window.getHeight() / 4 - 32 + i * 60, 115, g);
		}
		
		Iterator<Button> i = input.getSetupButtons().iterator();
		while (i.hasNext())
		{
			Button button = i.next();
			String name = button.getName();
			
			button.draw(g);
			draw(name, button.getX() + 32, button.getY() + button.getHeight() / 4, g);
		}
	}
	
	public void renderStore(Graphics g)
	{
		
	}
	
	public void renderGame(Graphics g)
	{
		
	}
	
	
	
	
	
	/**
	 * Draws a given sprite to the screen at the specified tile location
	 * 
	 * @param x - the x pixel position of the screen to draw the sprite to
	 * @param y - the y pixel position of the screen to draw the sprite to
	 * @param index - the index of the sprite
	 * @param g - the graphics to draw to
	 */
	private void draw(int x, int y, int index, Graphics g)
	{
		g.drawImage(spriteLoader.loadSprite(index).getImage(), x, y, null);
	}
	
	private void draw(int x, int y, String name, Graphics g)
	{
		g.drawImage(spriteLoader.loadSprite(name).getImage(), x, y, null);
	}
	
	/**
	 * Draws a given sprite to the screen at the specified tile location
	 * 
	 * @param x - the x tile position of the screen to draw the sprite to
	 * @param y - the y tile position of the screen to draw the sprite to
	 * @param index - the index of the sprite
	 * @param g - the graphics to draw to
	 */
	private void drawTile(int x, int y, int index, Graphics g)
	{
		draw(x * 32, y * 32, index, g);
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
	private void drawTileRect(int x, int y, int width, int height, int index, Graphics g)
	{
		for (int i = y; i < y + height; i++)
		{
			for (int j = x; j < x + width; j++)
			{
				drawTile(j, i, index, g);
			}
		}
	}
	
	// This is the order in which the chars show up in the sprite sheet
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ .,!?\"0123456789|     ";
	
	public void draw(String string, int x, int y, Graphics g)
	{
		String str = string.toUpperCase();
		
		for (int i = 0; i < str.length(); i++)
		{
			int index = chars.indexOf(str.charAt(i));
			if (index >= 0)
			{
				// index of the char in the string + the initial char index in the sprite sheet
				g.drawImage(spriteLoader.loadSprite(index + 16 * 13).getImage(), x + i * 32, y, null);
			}
		}
	}
}
