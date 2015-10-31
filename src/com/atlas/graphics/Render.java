package com.atlas.graphics;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import com.atlas.Game;
import com.atlas.InputHandler;
import com.atlas.helper.MathHelper;

public class Render
{
	private Game game;
	private JFrame window;
	private InputHandler input;
	
	private SpriteLoader spriteLoader;
	private List<Image> terrainImages = new ArrayList<Image>();
	
	public Render(Game game)
	{
		this.game = game;
		this.window = game.window;
		this.spriteLoader = game.spriteLoader;
		this.input = game.input;
		
		init();
	}
	
	private void init()
	{
		for (int i = 0; i < 32; i++)	// Grass
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 8, 1));
		}
		for (int i = 0; i < 32; i++)	// Sand
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 8, 2));
		}
		for (int i = 0; i < 32; i++)	// Snow
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 8, 3));
		}
		for (int i = 0; i < 32; i++)	// Light dirt
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 32, 4));
		}
		for (int i = 0; i < 32; i++)	// Dark dirt
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 32, 5));
		}
		for (int i = 0; i < 32; i++)	// Light stone
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 32, 6));
		}
		for (int i = 0; i < 32; i++)	// Dark stone
		{
			terrainImages.add(generateTerrainImage(i, 0, 1, 32, 7));
		}
		
		
		
		int x = window.getWidth() / 2 - 256;
		int y = window.getHeight() / 2 - 64;
		Button play = new Button(x, y, "setup", "Play");
		Button settings = new Button(x, y += 90, "settings", "Settings");
		Button about = new Button(x, y += 90, "about", "About");
		Button quit = new Button(x, y += 90, "quit", "Quit");
		
		input.addMenuButton(play);
		input.addMenuButton(settings);
		input.addMenuButton(about);
		input.addMenuButton(quit);
		
		
		
		x = window.getWidth() / 2 - 256;
		y = window.getHeight() / 4 - 32;
		Button red = new Button(spriteLoader.loadSprite(16).getImage(), x, y, "red", "Player 1");
		Button green = new Button(spriteLoader.loadSprite(32).getImage(), x, y += 60, "green", "Player 2");
		Button blue = new Button(spriteLoader.loadSprite(48).getImage(), x, y += 60, "blue", "Player 3");
		Button yellow = new Button(spriteLoader.loadSprite(64).getImage(), x, y += 60, "yellow", "Player 4");
		Button white = new Button(spriteLoader.loadSprite(80).getImage(), x, y += 60, "white", "Player 5");
		Button black = new Button(spriteLoader.loadSprite(96).getImage(), x, y += 60, "black", "Player 6");
		Button go = new Button(x, y += 90, "store", "Go!");
		Button back = new Button(x, y += 180, "menu", "Return");
		
		input.addSetupButton(red);
		input.addSetupButton(green);
		input.addSetupButton(blue);
		input.addSetupButton(yellow);
		input.addSetupButton(white);
		input.addSetupButton(black);
		input.addSetupButton(go);
		input.addSetupButton(back);
		
		x = window.getWidth() / 2 - 256;
		y = window.getHeight() / 4 - 32;
		Button start = new Button(x, y, "start", "Start");
		Button back2 = new Button(x, y += 180, "setup", "Return");
		
		input.addStoreButton(start);
		input.addStoreButton(back2);
	}
	
	
	
	
	
	public void renderMenu(Graphics g)
	{
		drawBackground("title", g);
		
		
		
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
		drawBackground("setup", g);
		
		
		
		for (int i = 0; i < 6; i++)
		{
			draw(window.getWidth() / 2 - 256, window.getHeight() / 4 - 32 + i * 60, 115, g);
		}
		
		Iterator<Button> i = input.getSetupButtons().iterator();
		int player = 0;
		while (i.hasNext())
		{
			Button button = i.next();
			String name = button.getName();
			
			button.draw(g);
			draw(name, button.getX() + 32, button.getY() + button.getHeight() / 4, g);
			
			if (name.length() >= 5 && name.substring(0, 1).equals("P"))
			{
				MathHelper.clamp(player, 0, game.players.length);
				draw(button.getX() - 48, button.getY() + 8, game.getTexture(game.players[player]), g);
				player++;
			}
		}
	}
	
	public void renderStore(Graphics g)
	{
		drawBackground("store", g);
		
		draw(window.getWidth() / 2 - 512, window.getHeight() / 2 - 512, "gui", g);
		
		Iterator<Button> i = input.getStoreButtons().iterator();
		while (i.hasNext())
		{
			Button button = i.next();
			String name = button.getName();
			
			button.draw(g);
			draw(name, button.getX() + 32, button.getY() + button.getHeight() / 4, g);
		}
	}
	
	public void renderGame(Graphics g)
	{
		renderTerrain(g);
	}
	
	private void renderTerrain(Graphics g)
	{
		int[] terrain = game.map.getTerrain();
		
		drawTileRect(0, 0, window.getWidth() / 32, window.getHeight() / 32, 0, g);
		
		for (int i = 0; i < terrain.length; i++)
		{
			int height = window.getHeight() - terrain[i];;
			Image img = null;
			
			img = terrainImages.get(i % 32);
			g.drawImage(img, i, height, null);
			img = terrainImages.get(i % 32 + 32 * 3);
			for (int j = 0; j < 2; j++)
				g.drawImage(img, i, height + 8 + 32 * j, null);
			img = terrainImages.get(i % 32 + 32 * 4);
			for (int j = 0; j < 2; j++)
				g.drawImage(img, i, height + 64 + 32 * j, null);
			img = terrainImages.get(i % 32 + 32 * 5);
			for (int j = 0; j < 4; j++)
				g.drawImage(img, i, height + 128 + 32 * j, null);
			img = terrainImages.get(i % 32 + 32 * 6);
			for (int j = 0; j < 4; j++)
				g.drawImage(img, i, height + 256 + 32 * j, null);
		}
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
	private String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ .,!?\"0123456789|     ";
	
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
	
	private void drawBackground(String title, Graphics g)
	{
		drawTileRect(0, 0, window.getWidth() / 32, window.getHeight() / 32, 0, g);
		drawTileRect(0, (int) (window.getHeight() / 32 * (5.0f / 8)), window.getWidth() / 32, 1, 1, g);
		drawTileRect(0, (int) (window.getHeight() / 32 * (5.0f / 8)) + 1, window.getWidth() / 32, (int) (window.getHeight() / 32 * (3.0f/ 8)) - 1, 4, g);
		draw(window.getWidth() / 5, 80 + (int) (16 * Math.sin(game.getTicks() / 50.0)), title, g);
		
		draw(window.getWidth() / 5, (int) (window.getHeight() * (5.0f / 8) - 32), 10, g);
		
		draw(0 + MathHelper.clamp((int) (-64 + game.getTicks() / 5 % 2147483647), - 64, window.getWidth()),
				(int) (window.getHeight() * (5.0f / 8) - 32), 114, g);
		draw(0 + MathHelper.clamp((int) (-64 + game.getTicks() / 5 % 2147483647), - 64, window.getWidth()),
				(int) (window.getHeight() * (5.0f / 8) - 32), (int) (16 + game.getTicks() / 5 % 2), g);
	}
	
	private Image generateTerrainImage(int x, int y, int width, int height, int index)
	{
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(width, height, Transparency.BITMASK);
		BufferedImage sprite = (BufferedImage) spriteLoader.loadSprite(index).getImage();
		
		BufferedImage subImage = sprite.getSubimage(x, y, width, height);
		image.getGraphics().drawImage(subImage, 0, 0, null);
		
		return image;
	}
}
