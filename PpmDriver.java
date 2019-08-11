import java.awt.Color;
import java.util.Scanner;

// May take a few seconds to run. Refresh project for new picture files to appear.
// Image files can be viewed by any program capable of viewing a PPM file, such as GIMP.
// Enter aldo.ppm or import your own ppm file to test the program.

/**
 * @author Kevin Cummings
 * @version 1.0
 * @since 2/4/2017
 */
public class PpmDriver
{
	public static void main(String args[])
	{
		Ppm file = new Ppm();	
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter a P3 PPM filename: ");
		String filename = in.nextLine();
		
		boolean works = false;
		
		try {
			works = file.readFile(filename);
		} catch (Exception e) {
			System.out.println("Invalid PPM format. A P3 file is needed.");
			System.exit(0);
		}
		
		if (!works)
		{
			System.out.println("File not found");
			System.exit(0);
		}
		
		Ppm darken = file.darken();
		darken.writeFile(filename.split("\\.")[0] + "_darken.ppm");
		
		Ppm lighten = file.lighten();
		lighten.writeFile(filename.split("\\.")[0] + "_lighten.ppm");
		
		Ppm greyscale = file.greyscale();
		greyscale.writeFile(filename.split("\\.")[0] + "_greyscale.ppm");
		
		Ppm invert = file.invert();
		invert.writeFile(filename.split("\\.")[0] + "_invert.ppm");
		
		Ppm flipVertical = file.flipVertical();
		flipVertical.writeFile(filename.split("\\.")[0] + "_flipVertical.ppm");
		
		Ppm flipHorizontal = file.flipHorizontal();
		flipHorizontal.writeFile(filename.split("\\.")[0] + "_flipHorizontal.ppm");
		
		Ppm addNoise = file.addNoise();
		addNoise.writeFile(filename.split("\\.")[0] + "_addNoise.ppm");
		
		Ppm posterize = file.posterize();
		posterize.writeFile(filename.split("\\.")[0] + "_posterize.ppm");
		
		Ppm soften = file.soften();
		soften.writeFile(filename.split("\\.")[0] + "_soften.ppm");
		
		Ppm addFrame = file.addFrame(25, Color.BLACK);
		addFrame.writeFile(filename.split("\\.")[0] + "_addFrame.ppm");
		
		Ppm surprise = file.surprise();
		surprise.writeFile(filename.split("\\.")[0] + "_surprise.ppm");
	}
}