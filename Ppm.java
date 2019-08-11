import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Kevin Cummings
 * @version 1.0
 * @since 2/4/2017
 */
public class Ppm
{
	private Color[][] pixels;
	
	/**
	 * Empty constructor for Ppm
	 */
	public Ppm()
	{
		
	}
	
	/**
	 * @param color
	 * 
	 * Constructor for Ppm that takes pixel color data
	 */
	public Ppm(Color[][] color)
	{
		this.pixels = color;
	}
	
	/**
	 * @param filename
	 * 
	 * Reads a file entered by the user and places data in Ppm class
	 * 
	 * @return a boolean value indicating success
	 * @throws Exception 
	 */
	
	public boolean readFile(String filename) throws Exception
	{
		Scanner reader;
		ArrayList<String> numberList = new ArrayList<>();
		ArrayList<Color> colorList = new ArrayList<>();
		
		try {
			reader = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			return false;
		}
		
		String firstline = reader.nextLine();
		String[] dimensions = reader.nextLine().split(" ");
		int width = 0, height = 0;
		
		if (firstline.equals("P3"))
		{
			if (dimensions[0].equals("#"))
			{
				String[] dimensionsAgain = reader.nextLine().split(" ");
				width = Integer.parseInt(dimensionsAgain[0]);
				height = Integer.parseInt(dimensionsAgain[1]);
			}
		
			else
			{
				width = Integer.parseInt(dimensions[0]);
				height = Integer.parseInt(dimensions[1]);
			}
		}
		
		else
		{
			throw new Exception();
		}
		
		while(reader.hasNextLine())
		{
			if (reader.hasNextInt())
			{
				int number = reader.nextInt();
				String line = Integer.toString(number);
				
				if (!line.substring(0, 1).equals("#"))
				{
					numberList.add(line);
				}
			}
			
			else
			{
				reader.nextLine();
			}
		}
		
		pixels = new Color[width][height];
		numberList.remove(0);
		numberList.add("0");
		
		for (int i = 2; i < numberList.size(); i += 3)
		{
			int r = Integer.parseInt(numberList.get(i - 2));
			int g = Integer.parseInt(numberList.get(i - 1));
			int b = Integer.parseInt(numberList.get(i));
			colorList.add(new Color(r, g, b));
		}
		
		int colorIndex = 0;
		
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				pixels[j][i] = colorList.get(colorIndex);
				colorIndex++;
			}
		}
		
		reader.close();
		
		return true;
	}
	
	/**
	 * @param filename
	 * 
	 * Writes a ppm file with data from the Ppm class
	 * 
	 * @return a boolean value indicating success
	 */
	public boolean writeFile(String filename)
	{
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
			writer.println("P3");
			writer.println(pixels.length + " " + pixels[0].length);
			writer.println(this.getMaxValue());
			
			for (int i = 0; i < pixels[0].length; i++)
			{
				for (int j = 0; j < pixels.length; j++)
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					writer.println(r + "");
					writer.println(g + "");
					writer.println(b + "");
				}
			}
			
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Unable to write file");
			return false;
		}
		
		return true;
	}
	
	/**
	 * @return Color data from the Ppm class
	 */
	public Color[][] getData()
	{
		return pixels;
	}
	
	/**
	 * @return A new Ppm class with darkened pixels 
	 */
	public Ppm darken()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{	
						int r = pixels[j][i].getRed();
						int g = pixels[j][i].getGreen();
						int b = pixels[j][i].getBlue();
						
						if (i == 0 && j == 0)
						{
							r = 255;
						}
						
						else
						{
							r /= 2;
						}
						
						g /= 2;
						b /= 2;
						
						newPixels[j][i] = new Color(r, g, b);
				} catch (NullPointerException e) {
					System.out.println("Darkening failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with lightened pixels
	 */
	public Ppm lighten()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					if (r > 127)
					{
						r = 255;
					}
					
					else
					{
						r *= 2;
					}
					
					if (g > 127)
					{
						g = 255;
					}
					
					else
					{
						g *= 2;
					}
					
					if (b > 127)
					{
						b = 255;
					}
					
					else
					{
						b *= 2;
					}
						
					newPixels[j][i] = new Color(r, g, b);
				} catch (NullPointerException e) {
					System.out.println("Lighten failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with greyscale pixels
	 */
	public Ppm greyscale()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					int total = r + g + b; 
					total /= 3;
					
					newPixels[j][i] = new Color(total, total, total);
				} catch (NullPointerException e) {
					System.out.println("Greyscaling failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with inverted colors
	 */
	public Ppm invert()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					r = 255 - r;
					g = 255 - g;
					b = 255 - b;
					
					newPixels[j][i] = new Color(r, g, b);
				} catch (NullPointerException e) {
					System.out.println("Inverting failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with the pixels flipped vertically
	 */
	public Ppm flipVertical()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		ArrayList <Color> colorList = new ArrayList<>();
		
		try
		{
			for (int i = this.getHeight() - 1; i >= 0; i--)
			{
				for (int j = 0; j < this.getWidth(); j++)
				{
					colorList.add(pixels[j][i]);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Vertical flipping failed due to color corruption.");
			
			return this;
		}
		
		try
		{
			int colorIndex = 0;
			
			for (int i = 0; i < this.getHeight(); i++)
			{
				for (int j = 0; j < this.getWidth(); j++)
				{
					newPixels[j][i] = colorList.get(colorIndex);
					colorIndex++;
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Horizontal flipping failed due to color corruption.");
			
			return this;
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with the pixels flipped horizontally
	 */
	public Ppm flipHorizontal()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		ArrayList <Color[]> colorList = new ArrayList<>();
		
		try
		{
			for (int i = pixels.length - 1; i >= 0; i--)
			{
				colorList.add(pixels[i]);
			}
		} catch (NullPointerException e) {
			System.out.println("Horizontal flipping failed due to color corruption.");
			
			return this;
		}
		
		try
		{
			int colorIndex = 0;
			
			for (int i = 0; i < pixels.length; i++)
			{
				newPixels[i] = colorList.get(colorIndex);
				colorIndex++;
			}
		} catch (NullPointerException e) {
			System.out.println("Horizontal flipping failed due to color corruption.");
			
			return this;
		}
		
		return new Ppm(newPixels);
	}
	
	
	/**
	 * @return A new Ppm class with a noisy pixel configuration
	 */
	public Ppm addNoise()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					int[] nums = {r, g, b};
					
					for (int k = 0; k < nums.length; k++)
					{
						Random random = new Random();
						int number = random.nextInt(64);
						
						Random positiveOrNegative = new Random();
						int sign = positiveOrNegative.nextInt(1);
						
						if (sign == 0)
						{
							nums[k] += number;
							
							if (nums[k] > 255)
							{
								nums[k] = 255;
							}
						}
						
						else
						{
							nums[k] -= number;
							
							if (nums[k] < 0)
							{
								nums[k] = 0;
							}
						}
					}
					
					newPixels[j][i] = new Color(nums[0], nums[1], nums[2]);
				} catch (NullPointerException e) {
					System.out.println("Greyscaling failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with a posterized pixel configuration
	 */
	public Ppm posterize()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					int[] nums = {r, g, b};
					
					for (int k = 0; k < nums.length; k++)
					{
						if (nums[k] < 16)
						{
							nums[k] = 8;
						}
						
						else if (nums[k] < 48)
						{
							nums[k] = 32;
						}
						
						else if (nums[k] < 80)
						{
							nums[k] = 64;
						}
						
						else if (nums[k] < 112)
						{
							nums[k] = 96;
						}
						
						else if (nums[k] < 144)
						{
							nums[k] = 128;
						}
						
						else if (nums[k] < 176)
						{
							nums[k] = 160;
						}
						
						else if (nums[k] < 208)
						{
							nums[k] = 192;
						}
						
						else if (nums[k] < 240)
						{
							nums[k] = 224;
						}
						
						else
						{
							nums[k] = 248;
						}
					}
					
					newPixels[j][i] = new Color(nums[0], nums[1], nums[2]);
				} catch (NullPointerException e) {
					System.out.println("Inverting failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with a softened pixel configuration
	 */
	public Ppm soften()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					Color[] surrounding = new Color[9];

					if (j > 0 && i > 0 && i < pixels[0].length - 1 && j < pixels.length - 1)
					{
						// Fills a 3 x 3 square left to write.
						surrounding[0] = pixels[j - 1][i - 1];
						surrounding[1] = pixels[j][i - 1];
						surrounding[2] = pixels[j + 1][i - 1];
						surrounding[3] = pixels[j - 1][i];
						surrounding[4] = pixels[j][i];
						surrounding[5] = pixels[j + 1][i];
						surrounding[6] = pixels[j - 1][i + 1];
						surrounding[7] = pixels[j][i+1];
						surrounding[8] = pixels[j + 1][i + 1];

						int redAverage = 0, greenAverage = 0, blueAverage = 0;
						
						for (int k = 0; k < surrounding.length; k++)
						{
							redAverage += surrounding[k].getRed();
							greenAverage += surrounding[k].getGreen();
							blueAverage += surrounding[k].getBlue();
						}
						
						newPixels[j][i] = new Color(redAverage / 9, greenAverage / 9, blueAverage / 9);
					}
					
					else
					{
						newPixels[j][i] = pixels[j][i];
					}
				} catch (NullPointerException e) {
					System.out.println("Softening failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @param size
	 * @param color
	 * 
	 * @return A new Ppm class with a frame of specified size and color
	 */
	public Ppm addFrame(int size, Color color)
	{
		Color[][] newPixels = new Color[pixels.length + size][pixels[0].length + size];
		int halfSize;
		
		if (size % 2 != 0)
		{
			halfSize = (size + 1) / 2;
		}
		
		else
		{
			halfSize = size / 2;
		}
		
		for (int i = halfSize; i < newPixels[0].length - halfSize; i++)
		{
			for (int j = halfSize; j < newPixels.length - halfSize; j++)
			{				
				newPixels[j][i] = pixels[j - halfSize][i - halfSize];
			}
		}
		
		for (int i = 0; i < newPixels[0].length; i++)
		{
			for (int j = 0; j < newPixels.length; j++)
			{
				if (newPixels[j][i] == null)
				{
					newPixels[j][i] = color;
				}
			}
		}
		
		return new Ppm(newPixels);
	}
	
	/**
	 * @return A new Ppm class with a hidden surprise
	 */
	public Ppm surprise()
	{
		Color[][] newPixels = new Color[pixels.length][pixels[0].length];
		Ppm newPpm = new Ppm(pixels);
	
		for (int i = 0; i < pixels[0].length; i++)
		{
			for (int j = 0; j < pixels.length; j++)
			{
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					int[] nums = {r, g, b};
					
					int average = 0;
					
					for (int k = 0; k < nums.length; k++)
					{
						average += nums[k];
					}
					
					average /= 3;
					
					if (average < 85)
					{
						newPixels[j][i] = new Color(0, 0, 0);
					}
					
					else if (average < 170)
					{
						newPixels[j][i] = new Color(127, 127, 127);
					}
					
					else
					{
						newPixels[j][i] = new Color(255, 255, 255);
					}
				} catch (NullPointerException e) {
					System.out.println("Darkening failed due to color corruption.");
					
					return this;
				}
			}
		}
		
		return new Ppm(newPixels).addFrame(15, Color.BLACK).addFrame(15, Color.WHITE).addFrame(15, Color.BLACK);
	}
	
	/**
	 * @return The width of the pixel configuration
	 */
	public int getWidth()
	{
		return pixels.length;
	}
	
	/**
	 * @return The height of the pixel configuration
	 */
	public int getHeight()
	{
		return pixels[0].length;
	}
	
	/**
	 * @return The maximum value of any component of any color in the Ppm data
	 */
	public int getMaxValue()
	{
		int max = 0;
		
		for (int i = 0; i < this.getHeight(); i++)
		{		
			for (int j = 0; j < this.getWidth(); j++)
			{			
				try
				{
					int r = pixels[j][i].getRed();
					int g = pixels[j][i].getGreen();
					int b = pixels[j][i].getBlue();
					
					if (r > max)
					{
						max = r;
					}
					
					if (g > max)
					{
						max = g;
					}
					
					if (b > max)
					{
						max = b;
					}
				} catch (NullPointerException e) {
					System.out.println("File has color corruption.");
				}
			}
		}
		
		return max;
	}
}
