import javax.swing.JFrame;
import ui.SampleComponent;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setTitle("A Sample Component");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SampleComponent component = new SampleComponent();
		frame.add(component);
		frame.setVisible(true);
	}
}
