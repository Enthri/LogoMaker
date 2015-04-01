import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Engine {
	private static JComponent painter;
	
	private static JFrame frame;
	private static JFileChooser fileChooser;
	
	private static JScrollPane layerContainerParent;
	private static JPanel layerContainer;
	private static JPanel toolPanel;
	
	private static ArrayList<Layer> layerList = new ArrayList<Layer>();
	
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		frame = new JFrame("test");
		fileChooser = new JFileChooser();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800, 600));
		frame.setMinimumSize(new Dimension(600, 400));
		painter = new JComponent() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(this.getBackground());
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.BLACK);
				Graphics2D render = (Graphics2D) g;
				for(Layer layerVar : layerList) {
					layerVar.paint(render);
				}
			}
		};
		painter.setBackground(Color.WHITE);
		painter.setBorder(BorderFactory.createEtchedBorder());
		frame.add(painter);
		layerContainer = new JPanel();
		layerContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
		layerContainerParent = new JScrollPane(layerContainer);
		layerContainerParent.setPreferredSize(new Dimension(0, 90));
		layerContainerParent.setBorder(BorderFactory.createTitledBorder("Layers"));
		frame.add(layerContainerParent, BorderLayout.SOUTH);
		toolPanel = new JPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBorder(null);
		toolPanel.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				if(toolPanel.getSize().getWidth() >= 5 && toolPanel.getBorder() == null) {
					toolPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
				} else if(toolPanel.getSize().getWidth() < 5 && toolPanel.getBorder() != null) {
					toolPanel.setBorder(null);
				}
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}
		});
		frame.add(toolPanel, BorderLayout.EAST);
		JMenu temp;
		JMenuBar menubar = new JMenuBar();
		temp = new JMenu("File");
		temp.add(createMenuItem("Open", null));
		temp.add(createMenuItem("Save", null));
		temp.add(createMenuItem("Save As", null));
		temp.addSeparator();
		temp.add(createMenuItem("Export", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("PNG file", ".png"));
				if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					BufferedImage image = new BufferedImage(painter.getWidth(), painter.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
					painter.paintAll(image.getGraphics());
					try {
						ImageIO.write(image, "PNG", fileChooser.getSelectedFile());
					} catch (IOException e1) {
						System.out.println("Cannot save file..");
					}
				}
			}
		}));
		temp.addSeparator();
		temp.add(createMenuItem("Exit", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		}));
		menubar.add(temp);
		temp = new JMenu("Edit");
		temp.add(createMenuItem("Copy", null));
		temp.add(createMenuItem("Paste", null));
		menubar.add(temp);
		temp = new JMenu("Layers");
		temp.add(createMenuItem("Add Layer", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addLayer(new Layer(painter, "Layer: " + layerList.size()));
			}
		}));
		menubar.add(temp);
		frame.setJMenuBar(menubar);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(layerList.size() > 0) layerList.get(0).keyPressed(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		frame.setVisible(true);
	}
	
	private static JMenuItem createMenuItem(String title, ActionListener listener) {
		JMenuItem temp = new JMenuItem(title);
		temp.addActionListener(listener);
		return temp;
	}
	
	private static void revaluate(Component c) {
		c.revalidate();
		c.repaint();
	}
	
	private static void addLayer(Layer layer) {
		layerList.add(layer);
		layerContainer.add(layer.getButton());
		layer.getButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectLayer(layer);
			}
		});
		revaluate(layerContainer);
		selectLayer(layer);
	}
	
	private static void selectLayer(Layer layer) {
		if(layerList.indexOf(layer) > -1) {
			for(Layer layer2 : layerList) layer2.getButton().setEnabled(true);
			layer.getButton().setEnabled(false);
			layerList.remove(layerList.indexOf(layer));
			layerList.add(0, layer);
			toolPanel.removeAll();
			layer.drawUI(toolPanel);
			revaluate(toolPanel);
		}
	}
}