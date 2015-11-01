package com.atlas.components;

import java.awt.Graphics;

import com.atlas.Game;
import com.atlas.Game.PlayerType;
import com.atlas.InputHandler;
import com.atlas.graphics.Render;
import com.atlas.helper.MathHelper;

public class Tank extends DrawableComponent
{
	private PlayerType controller;
	private int index;
	private Game game;
	private InputHandler input;
	private Render renderer;
	int[] terrain;
	long ticks = 0;
	int windowWidth;
	int windowHeight;
	
	boolean isMoving = false;
	
	private float xPos;
	private float yPos;
	/** Direction of the tank
	 * 0 - right flat
	 * 1 - right uphill
	 * 2 - right downhill
	 * 3 - left flat
	 * 4 - left uphill
	 * 5 - left uphill
	 */
	private int dir;
	private float gunAngle;
	private float power;
	private float xVel;
	private float yVel;
	private int weapon;
	
	public Tank(int spawnX, int index, PlayerType playerType, Game game)
	{
		controller = playerType;
		this.index = index;
		this.game = game;
		input = game.input;
		renderer = game.renderer;
		windowWidth = game.window.getWidth();
		windowHeight = game.window.getHeight();
		terrain = game.map.getTerrain();
		
		xPos = spawnX;
		yPos = windowHeight - terrain[spawnX + 16];
		gunAngle = 55;
		power = 50;
	}
	
	public void update(long ticks)
	{
		this.ticks = ticks;
		
		int in = getInput();
		
		isMoving = false;
		xVel = 0;
		
		if (in == 0)
		{
			fire();
		}
		if (in == 1)
		{
			isMoving = true;
			xVel = -0.25f;
			dir = 3;
		}
		if (in == 2)
		{
			isMoving = true;
			xVel = 0.25f;
			dir = 0;
		}
		if (in == 3)
		{
			gunAngle = MathHelper.clamp(gunAngle - 0.25f, 0, 180);
		}
		if (in == 4)
		{
			gunAngle = MathHelper.clamp(gunAngle + 0.25f, 0, 180);
		}
		if (in == 5)
		{
			power = MathHelper.clamp(power + 0.25f, 0, 100);
		}
		if (in == 6)
		{
			power = MathHelper.clamp(power - 0.25f, 0, 100);
		}
		
		if (xVel !=0 && xPos + xVel >= 0 && xPos + xVel <= windowWidth - 32)
		{
			xPos += xVel;
			yPos = windowHeight - terrain[(int) xPos + 16];
		}
	}
	
	private int getInput()
	{
		return input.getTankInput(index);
	}

	private void fire()
	{
		Component c;
		switch(weapon)
		{
		case 0:
			c = new Shell(xPos, yPos, gunAngle, power, game);
			break;
		default:
			c = new Shell(xPos, yPos, gunAngle, power, game);
		}
		
		game.addComponent(c);
		game.endTurn();
	}
	
	public void render(Graphics g)
	{
		int x = (int) xPos;
		int y = (int) yPos;
		
		int sprite = 16 * (index + 1) + 6 * (dir / 3);
		if (isMoving)
			sprite += (int) (ticks / 10 % 2);
		int gunSprite = 7 * 16 + (int) (gunAngle) / 15;
		
		renderer.draw(x, y - 28, gunSprite, g);
		g.drawString("Turn: " + game.getTurn(), 10, 40);
		if (x >= 0 && x <= windowWidth - 32 && y >= 0 && y <= windowHeight)
		{
			renderer.draw(x, y - 28, sprite, g);
		}
	}
}
