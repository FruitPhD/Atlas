package com.atlas;

public class Reference
{
	public static final String NAME = "ATLAS";
	public static final String VERSION = "v0.1.0";
	public static final String WEBSITE = "https://sites.google.com/site/fruitprogramming/";
	public static final String LOGGER_NAME = "ATLAS LOG";
	public static final String LOGGER_FILE = "logs/";
	public static final String SPRITESHEET_PATH = "res/sprites.png";
	public static final String IMAGES_PATH = "res/";
	
	public static final int MAP_WIDTH = 2048;
	
	/** Gravitational acceleration */
	public static final float gravity = 9.80665f / 20f;
	
	/** Sets the UPS to 60*/
	public static final double NANOSECONDS_PER_TICK = 1000000000.0 / 60.0;
	public static final double SECONDS_PER_TICK = 1.0 / 60.0;
}
