package com.atlas;

import javax.swing.JFrame;

import com.atlas.helper.Noise;

public class Map
{
	private JFrame window;
	private int[] terrain;
	
	public Map(Game game)
	{
		window = game.window;
		terrain = new int[window.getWidth()];
		generate();
		// TODO load players
	}
	
	public void generate()
	{
		generate(randomMapType());
	}
	
	public void generate(MapType m)
	{
		float mapType = getMapTypePersistence(m);
		
		Noise.setPrimes();
		
		for (int i = 0; i < window.getWidth(); i++)
		{
			float y = 50 * Noise.perlin1D((float) (0.01 * i), mapType, 5) + 256;
		    terrain[i] = (int) y;
		}
	}
	
	private float getMapTypePersistence(MapType m)
	{
		switch(m)
		{
		case PLAINS:
			return 0.25f;
		case HILLS:
			return 0.65f;
		case DESERT:
			return 0.005f;
		case MOUNTAINS:
			return 0.75f;
		default:
			return 0.25f;
		}
	}
	
	private MapType randomMapType()
	{
		int rand = (int) (Math.random() * 4);
		switch (rand)
		{
		case 0:
			return MapType.PLAINS;
		case 1:
			return MapType.HILLS;
		case 2:
			return MapType.DESERT;
		case 3:
			return MapType.MOUNTAINS;
		default:
			return MapType.PLAINS;
		}
	}
	
	public int[] getTerrain()
	{
		return terrain;
	}
	
	public enum MapType
	{
		PLAINS, HILLS, DESERT, MOUNTAINS
	}
}
