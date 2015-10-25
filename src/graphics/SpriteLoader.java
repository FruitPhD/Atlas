package graphics;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import core.Game;
import core.Log;
import core.Reference;

public class SpriteLoader
{
	/** Singleton instance of SpriteLoader */
	private static SpriteLoader spriteLoader = new SpriteLoader();
	private static BufferedImage spriteSheet;
	private HashMap<Integer, Sprite> sprites = new HashMap();
	private HashMap<String, Sprite> images = new HashMap();
	
	public SpriteLoader()
	{
		loadSpriteSheet(Reference.SPRITESHEET_PATH);
	}
	
	public static SpriteLoader getSingleton()
	{
		return spriteLoader;
	}
	
	private void loadSpriteSheet(String path)
	{
		try {
			URL url = this.getClass().getClassLoader().getResource(path);
			
			if (url == null)
				System.err.println("Path not found");
			
			spriteSheet = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Sprite sheet not found!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the sprite at the given index in the sheet. If the sprite has not been cached, it will cache the sprite in
	 * a hash map.
	 * @param index - Index at which to retrieve the Sprite
	 * @return Sprite - Sprite at the given index
	 */
	public Sprite loadSprite(int index)
	{
		// Do not cache a sprite that has already been cached, simply return it.
		if (sprites.get(index) != null)
			return (Sprite) sprites.get(index);
		
		// Sprites are 32 pixels square
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(32, 32, Transparency.BITMASK);
		
		// Finds the x and y coordinates of the sprite according to the index and gets the subimage from the sprite sheet
		// Note that 32 is the number of sprites per row in the sheet, not the sprite size
		BufferedImage subImage = null;
		int x = 0, y = 0;
		y = index / 32;
		x = index - y * 32;
		// Convert the (x, y) coordinate of the sprite into coordinate of start pixel
		x *= 32;
		y *= 32;
		// And load the subimage from the spritesheet
		if (spriteSheet != null)
		{
			Log.info("Getting sprite #" + index + " at (" + x + ", " + y + ")");
			subImage = spriteSheet.getSubimage(x, y, 32, 32);
		}
		else
			System.err.println("Sprite sheet does not exist!");
		
		image.getGraphics().drawImage(subImage, 0, 0, null);
		
		Sprite sprite = new Sprite(image);
		sprites.put(index, sprite);
		
		return sprite;
	}
	
	/**
	 * Loads a sprite to the cache with the given name
	 * 
	 * @param name - The name of the file
	 * @return Sprite - image with given name
	 */
	public Sprite loadSprite(String name)
	{
		// Do not cache a sprite that has already been cached, simply return it.
		if (images.get(name) != null)
			return (Sprite) images.get(name);
		
		BufferedImage source = null;
		
		try {
			URL url = this.getClass().getClassLoader().getResource(Reference.IMAGES_PATH + name + ".png");
			
			if (url == null)
				System.err.println("Path not found");
			
			source = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Sprite sheet not found!");
			e.printStackTrace();
		}
		
		// Sprites are 32 pixels square
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(32, 32, Transparency.TRANSLUCENT);
		
		image.getGraphics().drawImage(source, 0, 0, null);
		
		Sprite sprite = new Sprite(image);
		images.put(name, sprite);
		
		return sprite;
	}
}
