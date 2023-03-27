package mancala;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Contains all information necessary for a marble in the Mancala game.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class Marble {
	private static Image[] MARBLE_IMAGES_LIST = new Image[6];
	private final Image marbleImage;
	private final Random rand = new Random();
	private int xcord;
	private int ycord;

	/**
	 * Creates a new marble in the Mancala game. The location coordinates of this
	 * marble are randomly assigned within its pit in @seePlayPanel.java. The marble
	 * is assigned a random color from the list of marble images.
	 */
	public Marble() {
		this.marbleImage = MARBLE_IMAGES_LIST[rand.nextInt(MARBLE_IMAGES_LIST.length)];
	}

	/**
	 * Splits the primary image (mancalaImages.png), containing all images necessary
	 * to the Mancala game, into separate images used for the wooden Mancala board
	 * and each of the marble images. These images are resized from the original
	 * image sizes to properly fit the dimensions of the program.
	 */
	static {
		// Define the dimensions for each image from the original image file
		try {
			BufferedImage mainImage = ImageIO.read(new File("mancalaImages.png"));
			BufferedImage blueMarble = mainImage.getSubimage(33, 32, 120, 150);
			BufferedImage greenMarble = mainImage.getSubimage(55, 285, 120, 150);
			BufferedImage redMarble = mainImage.getSubimage(34, 542, 120, 100);
			BufferedImage orangeMarble = mainImage.getSubimage(90, 770, 100, 100);
			BufferedImage purpleMarble = mainImage.getSubimage(20, 979, 115, 100);
			BufferedImage yellowMarble = mainImage.getSubimage(42, 1205, 110, 100);

			// Scale the images to the correct size
			Image resizedBlueMarble = blueMarble.getScaledInstance(25, 40, Image.SCALE_SMOOTH);
			Image resizedGreenMarble = greenMarble.getScaledInstance(30, 40, Image.SCALE_SMOOTH);
			Image resizedRedMarble = redMarble.getScaledInstance(35, 25, Image.SCALE_SMOOTH);
			Image resizedOrangeMarble = orangeMarble.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			Image resizedPurpleMarble = purpleMarble.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			Image resizedYellowMarble = yellowMarble.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

			// Add all of these resized images to the array of marble images
			MARBLE_IMAGES_LIST[0] = resizedBlueMarble;
			MARBLE_IMAGES_LIST[1] = resizedGreenMarble;
			MARBLE_IMAGES_LIST[2] = resizedRedMarble;
			MARBLE_IMAGES_LIST[3] = resizedOrangeMarble;
			MARBLE_IMAGES_LIST[4] = resizedPurpleMarble;
			MARBLE_IMAGES_LIST[5] = resizedYellowMarble;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image getMarbleImage() {
		return marbleImage;
	}

	public int getXcord() {
		return this.xcord;
	}

	public void setXcord(int x) {
		this.xcord = x;
	}

	public int getYcord() {
		return this.ycord;
	}

	public void setYcord(int y) {
		this.ycord = y;
	}
}