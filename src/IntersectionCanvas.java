import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class IntersectionCanvas extends Canvas {
	public enum Mode {DEVICE, LOGICAL}; // Enumerator for the 2 coordinate systems
	private Mode mode = Mode.LOGICAL; // current coordinate system being displayed (default is logical)
	
	private final float LOGICAL_WIDTH  = 200f; // default logical width of the canvas
	private final float LOGICAL_HEIGHT = 200f; // default logical height of the canvas
	private float pixelsize; // logical size of each pixel in the canvas
	
	private float[] xs = new float[4]; // x-coordinates
	private float[] ys = new float[4]; // y-coordinates
	private int clickCount = 0;
	private JLabel text; // reference to text in frame
	
	public IntersectionCanvas() {
		super();
		this.setBackground(Color.white);
		
		// Attach a mouse adapter to register the mouse clicks on the canvas.
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clickCount < xs.length) {
					xs[clickCount] = fx(e.getX());
					ys[clickCount] = fy(e.getY());
					
					System.out.printf("Mouse clicked at (%f, %f) [logical]\n", xs[clickCount], ys[clickCount]);
					System.out.printf("Mouse clicked at (%d, %d) [device]\n", ix(xs[clickCount]), iy(ys[clickCount]));
					clickCount++;
					int dif = xs.length - clickCount;
					if (dif > 1) {
						text.setText("Click the canvas "+ dif +" more times.");
					} else {
						text.setText("Click the canvas "+ dif +" more time.");
					}
					repaint();
				}
				
			}
		});
	}

	/* Clears the canvas and erases any previous user input. Resets click count and text. */
	public void clear() {
		for (int i = 0; i < xs.length; i++) {
			xs[i] = ys[i] = 0; 
		}
		
		clickCount = 0;
		text.setText("Click the canvas to generate lines.");
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		initGraphics(); // calculate the pixel size ratio to ensure accurate logical and device coordinates
		renderUserInputs(g); // render any user input that has been received
		renderIntersection(g); // calculate and render the intersection if it exists
	}

	/* Update the pixel size ratio (used to keep coordinates accurately calculated and rendered) */
	private void initGraphics() {
		Dimension d = this.getSize();
		int maxX = d.width - 1; int maxY = d.height - 1;
		pixelsize = Math.max(LOGICAL_WIDTH / maxX, LOGICAL_HEIGHT / maxY);
		System.out.println("Pixel size ratio: " + pixelsize);
	}

	/* Render any user input to the canvas */
	private void renderUserInputs(Graphics g) {
		int diam = 8; // ellipse diameter for points
		for (int i = 0; i < clickCount; i++) {
			// draw a small circle at each point
			g.fillOval(ix(xs[i]) - diam/2, iy(ys[i]) - diam/2, diam, diam);
		}
		
		if (clickCount >= 2) {
			// draw a line from first point to second point
			g.drawLine(ix(xs[0]), iy(ys[0]), ix(xs[1]), iy(ys[1]));
		}
		if (clickCount >= 4) {
			// draw a line from third point to fourth point
			g.drawLine(ix(xs[2]), iy(ys[2]), ix(xs[3]), iy(ys[3]));
		}
		
	}
	
	/* Calculate the intersection and render it to the canvas if it exists */
	private void renderIntersection(Graphics g) {
		if (clickCount < 4)
			return; // not enough points yet
		
		int diam = 12; // diameter of intersection ellipse
		float a1, b1, c1, a2, b2, c2;
		
		// for line with points 1 and 2
		a1 = ys[1] - ys[0];
		b1 = xs[0] - xs[1];
		c1 = a1 * xs[0] + b1 * ys[0];
		
		//for line with points 3 and 4
		a2 = ys[3] - ys[2];
		b2 = xs[2] - xs[3];
		c2 = a2 * xs[2] + b2 * ys[2];
		
		float det = a1 * b2 - a2 * b1; // determinant of the lines
		
		if (det == 0) {
			text.setText("The lines do not intersect.");
		}
		else {
			// there is an intersection, so find its coordinates
			float x = (b2 * c1 - b1 * c2) / det;
			float y = (a1 * c2 - a2 * c1) / det;
			
			// make sure the intersection falls within the line segments
			if ((Math.min(xs[0], xs[1]) <= x) && (Math.min(xs[2], xs[3]) <= x) && (Math.max(xs[0], xs[1]) >= x) && (Math.max(xs[2], xs[3]) >= x) && 
				(Math.min(ys[0], ys[1]) <= y) && (Math.min(ys[2], ys[3]) <= y) && (Math.max(ys[0], ys[1]) >= y) && (Math.max(ys[2], ys[3]) >= y)) {
				
				if (mode == Mode.LOGICAL) {
					text.setText("The lines intersect at: ("+x+", "+y+")");
				}
				else { // mode == Mode.DEVICE
					text.setText("The lines intersect at: ("+ix(x)+", "+iy(y)+")");
				}
			}
			else {
				if (mode == Mode.LOGICAL) {
					text.setText("The lines intersect, but not within the selected line segments. ("+x+", "+y+")");
				}
				else {
					text.setText("The lines intersect, but not within the selected line segments. ("+ix(x)+", "+iy(y)+")");
				}
			}
			
			// draw the intersection on the canvas
			g.setColor(Color.red);
			g.fillOval(ix(x) - diam/2, iy(y) - diam/2, diam, diam);
		}
	}

	/* Get a reference to the text Component so it can be updated straight from here */
	public void textReference(JLabel text) {
		this.text = text;
	}
	
	/* Set the Displayed Coordinate Mode */
	public void setMode(Mode m) {
		this.mode = m;
		repaint();
	}
	
	/* HELPER METHODS - Converting Between Logical and Device Coordinates */
	int ix(float x) { return Math.round(x / pixelsize); }
	int iy(float y) { return Math.round(getHeight() - (y / pixelsize)); }
	float fx(int x) { return x * pixelsize; }
	float fy(int y) { return (getHeight() - y) * pixelsize; }


}
