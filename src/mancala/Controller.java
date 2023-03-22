package mancala;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Controller {	
	private final JFrame frame;
	private JPanel mainPanel;
	
	public static void main(String[] args) throws IOException {
		new Controller();
    }
	
	public Controller() throws IOException {
		frame = new JFrame("Mancala");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		generateImages();
		
		frame.add(mainPanel);
		frame.setVisible(true);
	}
	
	public void generateImages() throws IOException {
		Border blackline = BorderFactory.createLineBorder(Color.black);  // Testing implement
		// Define the dimensions for each image from the original image file
		BufferedImage mainImage = ImageIO.read(new File("mancalaImages.png"));
		BufferedImage boardImage = mainImage.getSubimage(10, 1400, 480, 150);
		BufferedImage blueMarble = mainImage.getSubimage(33,32, 120,150);
		BufferedImage greenMarble = mainImage.getSubimage(55,285, 120,150);
		BufferedImage redMarble = mainImage.getSubimage(34,542, 120,100);
		BufferedImage orangeMarble = mainImage.getSubimage(90, 770, 100, 100);
		BufferedImage purpleMarble = mainImage.getSubimage(20, 979, 115, 100);
		BufferedImage yellowMarble = mainImage.getSubimage(42, 1205, 110, 100);
		
		// Scale the images to the correct size
		Image resizedBoard = boardImage.getScaledInstance(800, 300, Image.SCALE_SMOOTH);
		Image resizedBlueMarble = blueMarble.getScaledInstance(35, 50, Image.SCALE_SMOOTH);
		Image resizedGreenMarble = greenMarble.getScaledInstance(40, 50, Image.SCALE_SMOOTH);
		Image resizedRedMarble = redMarble.getScaledInstance(45, 35, Image.SCALE_SMOOTH);
		Image resizedOrangeMarble = orangeMarble.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Image resizedPurpleMarble = purpleMarble.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Image resizedYellowMarble = yellowMarble.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		
		// Add the images to labels (for dispaly purposes)
		ImageIcon boardIcon = new ImageIcon(resizedBoard);
		ImageIcon blueMarbleIcon = new ImageIcon(resizedBlueMarble);
		ImageIcon greenMarbleIcon = new ImageIcon(resizedGreenMarble);
		ImageIcon redMarbleIcon = new ImageIcon(resizedRedMarble);
		ImageIcon orangeMarbleIcon = new ImageIcon(resizedOrangeMarble);
		ImageIcon purpleMarbleIcon = new ImageIcon(resizedPurpleMarble);
		ImageIcon yellowMarbleIcon = new ImageIcon(resizedYellowMarble);
		JLabel boardLabel = new JLabel(boardIcon);
		JLabel blueMarbleLabel = new JLabel(blueMarbleIcon);
		JLabel greenMarbleLabel = new JLabel(greenMarbleIcon);
		JLabel redMarbleLabel = new JLabel(redMarbleIcon);
		JLabel orangeMarbleLabel = new JLabel(orangeMarbleIcon);
		JLabel purpleMarbleLabel = new JLabel(purpleMarbleIcon);
		JLabel yellowMarbleLabel = new JLabel(yellowMarbleIcon);
		boardLabel.setBounds(0,120, 800,300);
		blueMarbleLabel.setBounds(10,0,120,100);
		greenMarbleLabel.setBounds(130,0,110,95);
		redMarbleLabel.setBounds(250,0,105,100);
		orangeMarbleLabel.setBounds(370,0,105,100);
		purpleMarbleLabel.setBounds(490,0,115,100);
		yellowMarbleLabel.setBounds(610,0,110,105);

		// Add all image labels to the panel
		mainPanel.add(boardLabel);
		mainPanel.add(blueMarbleLabel);
		mainPanel.add(greenMarbleLabel);
		mainPanel.add(redMarbleLabel);
		mainPanel.add(orangeMarbleLabel);
		mainPanel.add(purpleMarbleLabel);
		mainPanel.add(yellowMarbleLabel);
	}
}
