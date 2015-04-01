import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Layer {
	
	protected String name;
	protected JButton button;
	
	protected JComponent painter;
	
	public Layer(JComponent jcomponentVar, String stringVar) {
		name = stringVar;
		painter = jcomponentVar;
		button = new JButton(stringVar);
		button.setFocusable(false);
	}
	
	public String getName() {
		return name;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void paint(Graphics2D render) {
	}
	
	public void drawUI(JPanel component) {
	}
	
	public void keyPressed(int keyCode) {
	}
}