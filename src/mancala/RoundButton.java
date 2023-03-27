package mancala;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

/**
 * A class for creating elliptical buttons, rather than the default rectangular JButton. 
 * This button is linked with a particular pit for the purpose of determining which pit a user clicks on
 * and setting the coordinates of the marbles within the linked pit.
 * This class was modified from that found at the following location:
 * {@linkhttps://cs.smu.ca/~porter/csc/465/code/misc/gui/RoundButton.java2html}
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class RoundButton extends JButton {
	private final int pitNumber;

	/**
	 * Creates a new button linked with the specified pit within the Mancala game.
	 * 
	 * @param pitNumber		the index of the pit within the game's list of pits to be linked to the button
	 */
	public RoundButton(int pitNumber) {
		this.pitNumber = pitNumber;

		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		setContentAreaFilled(false);
	}

	/**
	 * Overrides the contains() method in the JButton class. Instead of having a rectangular boundary,
	 * the button has an elliptical boundary.
	 */
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
	
	public int getPitNumber() {
		return pitNumber;
	}

//	  protected void paintBorder(Graphics g) {
//	    g.setColor(getForeground());
//	    g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
//	  }

	Shape shape;


}
