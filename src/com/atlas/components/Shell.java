package com.atlas.components;

import java.awt.Graphics;

import com.atlas.Game;
import com.atlas.Reference;
import com.atlas.graphics.Render;

public class Shell extends DrawableComponent
{
	private float xPos;
	private float yPos;
	private float xVel;
	private float yVel;
	private float fireAngle;
	
	private Game game;
	private Render renderer;
	private int[] terrain;
	
	public Shell(float x, float y, float angle, float power, Game game)
	{
		xPos = x;
		yPos = y;
		xVel = (float) (Math.cos(Math.toRadians(angle)) * power / 25);
		yVel = (float) -(Math.sin(Math.toRadians(angle)) * power / 25);
		
		this.game = game;
		renderer = game.renderer;
		terrain = game.map.getTerrain();
	}
	
	public void update(long ticks)
	{
		float deltaXVel = 0;
		float deltaYVel = (float) (Reference.gravity * Reference.SECONDS_PER_TICK);
		
		xVel += deltaXVel;
		yVel += deltaYVel;
		
		xPos += xVel;
		yPos += yVel;
		
		// Out of bounds
		if (xPos <= -32 || xPos >= terrain.length || yPos > game.window.getHeight())
		{
			game.removeComponent(this);
			return;
		}
		
		// Impact
		if (xPos > 0 && xPos + 16 <= terrain.length && yPos - 16 >= game.window.getHeight() - terrain[(int) xPos + 16])
		{
			game.removeComponent(this);
			return;
		}
	}
	
	public void render(Graphics g)
	{
		int x = (int) (xPos);
		int y = (int) (yPos - 16);
		renderer.draw(x, y, 16 * 8, g);
	}
}
