import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.glass.events.KeyEvent;

public class LayerLogo extends Layer {
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private int rotation;
	
	private BufferedImage image;
	private BufferedImage selectedImage;

	public LayerLogo(BufferedImage bf, JComponent jcomponentVar, String stringVar) {
		super(jcomponentVar, stringVar);
		x = 0;
		y = 0;
		rotation = 0;
		image = bf;
		width = 50;
		height = 50;
		selectedImage = getIndex(0, 0);
	}
	
	private BufferedImage getIndex(int indexX, int indexY) {
		return image.getSubimage(indexX * 300, indexY * 300, 300, 300);
	}
	
	@Override
	public void paint(Graphics2D render) {
		AffineTransform oldTransform = render.getTransform();
		render.translate(x + width / 2, y + height / 2);
		render.rotate(Math.toRadians(rotation));
		render.drawImage(selectedImage, -width / 2, -height / 2, width, height, painter);
		render.setTransform(oldTransform);
	}
	
	@Override
	public void drawUI(JPanel component) {
		component.add(new JLabel("<html>Use Arrow Keys to move<br>A or D to rotate<br>W or S to expand and contract</html>"));
		component.add(new JLabel("<html><br>Font Size: </html>"));
		final JComboBox<ImageIcon> comboBox2 = new JComboBox<ImageIcon>();
		for(int j = 0; j < 2; j++) {
			for(int i = 0; i < 5; i++) {
				comboBox2.addItem(new ImageIcon(getIndex(i, j).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			}
		}
		comboBox2.setMaximumSize(new Dimension((int) comboBox2.getPreferredSize().getWidth(), (int) comboBox2.getPreferredSize().getHeight()));
		comboBox2.setFocusable(false);
		comboBox2.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(comboBox2);
		comboBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedImage = getIndex(comboBox2.getSelectedIndex() - (int)Math.floor(comboBox2.getSelectedIndex() / 5)*5, (int)Math.floor(comboBox2.getSelectedIndex() / 5));
				painter.requestFocus();
				painter.repaint();
			}
		});
	}
	
	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_RIGHT) x+=2;
		else if(keyCode == KeyEvent.VK_LEFT) x-=2;
		if(keyCode == KeyEvent.VK_DOWN) y+=2;
		else if(keyCode == KeyEvent.VK_UP) y-=2;
		if(keyCode == KeyEvent.VK_A) rotation-=2;
		if(keyCode == KeyEvent.VK_D) rotation+=2;
		if(keyCode == KeyEvent.VK_W) {
			width+=2;
			height+=2;
		}
		if(keyCode == KeyEvent.VK_S) {
			width-=2;
			height-=2;
		}
		painter.repaint();
	}
}