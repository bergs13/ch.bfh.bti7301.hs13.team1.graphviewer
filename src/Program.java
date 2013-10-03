import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import demo.SampleCirclePanel;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setTitle("A Sample Component");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,1));
		
		JPanel sampleCirclePanel = new SampleCirclePanel();
		sampleCirclePanel.setBorder(new LineBorder(Color.red));
		
		frame.add(sampleCirclePanel);
		frame.setVisible(true);
	}
}
