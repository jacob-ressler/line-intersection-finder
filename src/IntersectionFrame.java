import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class IntersectionFrame extends JFrame {

	private JPanel contentPane; // Holds all content within the frame
	private JLabel text; // Displays directions and results
	private IntersectionCanvas canvas; // The canvas
	private JButton resetButton; // Resets the canvas
	private ButtonGroup modeButtons; // Button group for logical and device mode buttons
	private JRadioButton logicalMode, deviceMode; // Changes the displayed coordinate system (logical = Cartesian, device = default)
	private JPanel southContainer; // Container for all buttons
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					IntersectionFrame frame = new IntersectionFrame();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IntersectionFrame() {
		super("Finding Intersection of 2 Lines");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				canvas.repaint();
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10)); // border layout with 10 hgap and vgap between components
		contentPane.setBackground(Color.decode("#cccccc"));
		setContentPane(contentPane);
		
		text = new JLabel("Click the canvas to generate lines.");
		text.setSize(contentPane.getWidth(), 25);
		text.setFont(text.getFont().deriveFont(15f)); // font size of 15
		text.setBorder(new EmptyBorder(0, 5, 0, 0));
		contentPane.add(text, BorderLayout.NORTH);
		
		canvas = new IntersectionCanvas();
		canvas.textReference(text);
		canvas.setSize(400, 400);
		contentPane.add(canvas, BorderLayout.CENTER);
		
		resetButton = new JButton("Reset");
		resetButton.setSize(100, 30);
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.clear(); // clear canvas
			}
		});

		logicalMode = new JRadioButton("Logical");
		logicalMode.setSelected(true);
		logicalMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setMode(IntersectionCanvas.Mode.LOGICAL);
			}
		});
		
		deviceMode = new JRadioButton("Device");
		deviceMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setMode(IntersectionCanvas.Mode.DEVICE);
			}
		});
		
		modeButtons = new ButtonGroup();
		modeButtons.add(logicalMode);
		modeButtons.add(deviceMode);
		
		southContainer = new JPanel();
		southContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		southContainer.add(new JLabel("Displayed Coordinate System:"));
		southContainer.add(logicalMode);
		southContainer.add(deviceMode);
		southContainer.add(resetButton);
		contentPane.add(southContainer, BorderLayout.SOUTH);
		
		

	}
}
