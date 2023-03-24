package mancala;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

/**
 * @title RoundButton.java
 * @author Ryan Johnson, Hank Rugg
 * @description A class for creating elliptical buttons, rather than the default rectangular JButton.
 * This class was modified from that found at the following location: 
 * https://cs.smu.ca/~porter/csc/465/code/misc/gui/RoundButton.java2html.
 */
public class RoundButton extends JButton {
	public RoundButton() {
	    Dimension size = getPreferredSize();
	    size.width = size.height = Math.max(size.width,size.height);
	    setPreferredSize(size);

	    setContentAreaFilled(false);
	  }

//	  protected void paintBorder(Graphics g) {
//	    g.setColor(getForeground());
//	    g.drawOval(0, 0, getSize().width-1,     getSize().height-1);
//	  }

	  Shape shape;
	  public boolean contains(int x, int y) {
	    if (shape == null || 
	      !shape.getBounds().equals(getBounds())) {
	      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
	    }
	    return shape.contains(x, y);
	  }
}
