import java.awt.Graphics2D;

import javax.swing.JButton;

public class Layer {
	
	private String name;
	private JButton button;
	
	public Layer(String stringVar) {
		name = stringVar;
		button = new JButton(stringVar);
	}
	
	public String getName() {
		return name;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void paint(Graphics2D render) {
		
	}
}