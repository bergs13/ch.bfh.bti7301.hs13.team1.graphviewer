import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		frame.setLayout(new GridLayout(1, 1));
		JPanel sampleCirclePanel = new SampleCirclePanel();
		frame.add(sampleCirclePanel);
		frame.setVisible(true);
	}
}
