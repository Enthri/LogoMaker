import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class PaintComponent extends JComponent {
	public PaintComponent() {
		super();
		rect = new Rectangle(0, 0, 20, 20);
	}
	private Rectangle rect;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		Graphics2D render = (Graphics2D) g;
		render.fill(rect);
	}
	
	public void randomizeLocation() {
		Random RNG = new Random();
		rect.setLocation(RNG.nextInt(this.getWidth() - 20) + 20, RNG.nextInt(this.getHeight() - 20) + 20);
	}
}
