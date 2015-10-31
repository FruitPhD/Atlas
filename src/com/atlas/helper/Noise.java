package com.atlas.helper;

public class Noise
{
	private static int[] primes = new int[3];
	
	public static void setPrimes()
	{
		primes = randomPrimes(3, 15000, 1500000000); // {15731, 789221, 1376312589};
	}
	
	public static float noise(int x)
	{
		x = (x << 13) ^ x;
		
		return (float) (1.0 - ((x * (x * x * primes[0] + primes[1]) + primes[2]) & Integer.MAX_VALUE) / 1073741824f);
	}
	
	public static float smoothNoise(int x)
	{
		return noise(x) / 2 + noise(x - 1) / 4 + noise(x + 1) / 4;
	}
	
	public static float interpolate(float a, float b, float x)
	{
		float f = (float) (x * Math.PI);
		float fraction = (float) ((1 - Math.cos(f)) * 0.5);
		
		return a * (1 - fraction) + b * fraction;
	}
	
	public static float interpolatedNoise(float x)
	{
		int intX = (int) x;
		float frac = x - intX;
		
		float n1 = smoothNoise(intX);
		float n2 = smoothNoise(intX + 1);
		
		return interpolate(n1, n2, frac);
	}
	
	public static float perlin1D(float x, float persistence, int octaves)
	{
		float total = 0;
		for (int i = 0; i < octaves - 1; i++)
		{
			float freq = (float) Math.pow(2, i);
			float amp = (float) (Math.pow(persistence, i));
			
			total += interpolatedNoise(x * freq) * amp;
		}
		
		return total;
	}
	
	public static int[] randomPrimes(int x, int min, int max)
	{
		int[] primes = new int[x];
		
		for (int i = 0; i < x; i++)
		{
			for (int j = min; j < max; j++)
			{
				if (isPrime(j) && Math.round(Math.random() * 10) == 1)
				{
					primes[i] = j;
					break;
				}
			}
		}
		
		return primes;
	}
	
	public static boolean isPrime(int x)
	{
		double sqrt = Math.sqrt(x);
		
		for (int i = 2; i <= sqrt; i++)
		{
			if (x % i == 0)
			{
				return false;
			}
		}
		
		return true;
	}
}
