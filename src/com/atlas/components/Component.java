package com.atlas.components;

public abstract class Component
{
	boolean enabled = true;
	
	public void update(long ticks)
	{
		
	}
	
	boolean isEnabled()
	{
		return enabled;
	}
	
	void enable()
	{
		enabled = true;
	}
	
	void disable()
	{
		enabled = false;
	}
}
