import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.glass.events.KeyEvent;

public class LayerText extends Layer {
	
	private int x;
	private int y;
	
	private int rotation;
	
	private String text;
	
	private Font font;
	private Color color;
	
	private boolean bold = false;
	private boolean italic = false;

	public LayerText(JComponent jcomponentVar, String stringVar) {
		super(jcomponentVar, stringVar);
		x = 0;
		y = 0;
		rotation = 0;
		text = "Title";
		font = new Font("Arial", Font.PLAIN, 12);
		color = Color.BLACK;
	}
	
	@Override
	public void paint(Graphics2D render) {
		AffineTransform oldTransform = render.getTransform();
		render.setFont(font);
		render.setPaint(color);
		FontMetrics fm = render.getFontMetrics();
		render.translate(x + fm.stringWidth(text) / 2, y + fm.getHeight() / 2);
		render.rotate(Math.toRadians(rotation));
		render.drawString(text, -fm.stringWidth(text) / 2, fm.getHeight() / 2);
		render.setTransform(oldTransform);
	}
	
	@Override
	public void drawUI(JPanel component) {
		component.add(new JLabel("<html>Use Arrow Keys to move<br>A or D to rotate</html>"));
		component.add(new JLabel("<html><br>Text: </html>"));
		final JTextField textField = new JTextField(text, 15);
		textField.setMaximumSize(new Dimension((int) textField.getPreferredSize().getWidth(), (int) textField.getPreferredSize().getHeight()));
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(textField);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text = textField.getText();
				painter.requestFocus();
				painter.repaint();
			}
		});
		component.add(new JLabel("<html><br>Font Style: </html>"));
		final JComboBox<String> comboBox = new JComboBox<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		comboBox.setMaximumSize(new Dimension((int) comboBox.getPreferredSize().getWidth(), (int) comboBox.getPreferredSize().getHeight()));
		comboBox.setSelectedItem("Arial");
		comboBox.setFocusable(false);
		comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				font = new Font((String)comboBox.getSelectedItem(), font.getStyle(), font.getSize());
				painter.requestFocus();
				painter.repaint();
			}
		});
		final JCheckBox checkBox = new JCheckBox("Bold");
		checkBox.setFocusable(false);
		checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(checkBox);
		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bold = checkBox.isSelected();
				setFontStyle();
				painter.requestFocus();
				painter.repaint();
			}
		});
		final JCheckBox checkBox2 = new JCheckBox("Italics");
		checkBox2.setFocusable(false);
		checkBox2.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(checkBox2);
		checkBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				italic = checkBox2.isSelected();
				setFontStyle();
				painter.requestFocus();
				painter.repaint();
			}
		});
		component.add(new JLabel("<html><br>Font Size: </html>"));
		final JComboBox<Integer> comboBox2 = new JComboBox<Integer>();
		for(int i = 10; i <= 100; i++) comboBox2.addItem(i);
		comboBox2.setMaximumSize(new Dimension((int) comboBox2.getPreferredSize().getWidth(), (int) comboBox2.getPreferredSize().getHeight()));
		comboBox2.setSelectedItem(12);
		comboBox2.setFocusable(false);
		comboBox2.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(comboBox2);
		comboBox2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				font = new Font(font.getName(), font.getStyle(), (Integer)comboBox2.getSelectedItem());
				painter.requestFocus();
				painter.repaint();
			}
		});
		component.add(new JLabel("<html><br>Font Color: </html>"));
		final JButton button = new JButton("Select Color");
		button.setFocusable(false);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		component.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(component, "Choose Text Color", color);
				painter.requestFocus();
				painter.repaint();
			}
		});
	}
	
	private void setFontStyle() {
		int a = Font.PLAIN;
		if(bold) a += Font.BOLD;
		if(italic) a += Font.ITALIC;
		font = new Font(font.getName(), a, font.getSize());
	}
	
	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_RIGHT) x+=2;
		else if(keyCode == KeyEvent.VK_LEFT) x-=2;
		if(keyCode == KeyEvent.VK_DOWN) y+=2;
		else if(keyCode == KeyEvent.VK_UP) y-=2;
		if(keyCode == KeyEvent.VK_A) rotation-=2;
		if(keyCode == KeyEvent.VK_D) rotation+=2;
		painter.repaint();
	}
}