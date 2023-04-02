package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * A class for creating elliptical buttons, rather than the default rectangular
 * JButton.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class RoundButton extends JButton {
	/*
	 * This button is linked with a particular pit for the purpose of determining
	 * which pit a user clicks on and setting the coordinates of the marbles within
	 * the linked pit. This class is based off of the RoundButton class found at the
	 * location shown below.
	 * 
	 * @see <a href=
	 * "https://cs.smu.ca/~porter/csc/465/code/misc/gui/RoundButton.java2html">
	 * RoundButton.java</a>
	 */
	private final int pitNumber;
	private Shape shape;

	/**
	 * Creates a new button linked with the specified pit within the Mancala game.
	 * 
	 * @param pitNumber the index of the pit within the game's list of pits to be
	 *                  linked to the button
	 */
	public RoundButton(int pitNumber) {
		this.pitNumber = pitNumber;

		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		setContentAreaFilled(false);
	}

	/**
	 * Overrides the contains() method in the JButton class. Instead of having a
	 * rectangular boundary, the button has an elliptical boundary.
	 * 
	 * @param x an integer representing the x-coordinate of the point in question
	 * @param y an integer representing the y-coordinate of the point in question
	 * @return a boolean indicating whether the designated point is within the
	 *         bounds of a shape (true if the point is contained within the shape;
	 *         false otherwise)
	 */
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}

	/**
	 * Returns the index of a button's corresponding pit in a Mancala game's list of
	 * pits.
	 * 
	 * @return an integer representing the index of a button's corresponding pit in
	 *         a Mancala game's list of pits
	 */
	public int getPitNumber() {
		return pitNumber;
	}

	protected void paintBorder(Graphics g) {
		if (isBorderPainted()) {
			g.setColor(getForeground());
			  g.drawOval(0, 0, getSize().width-1, getSize().height-1);
		}
	}
}
