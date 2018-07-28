package introos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI extends JPanel implements ActionListener {
	Timer tm = new Timer(5, this);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double height = screenSize.getHeight();
	double width = screenSize.getWidth();
	int x1 = 200, y1 = 200, x2 = (int) width-500, y2 = (int) height-250, // Position
		velX1 = 3, velY1 = 3, velX2 = 3, velY2 = 3; // Speed

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// fill( start x, start y, 50, 30);
		// to pause horizontal halfway, insert formula below and insert delay
		// x1 = (int) (width-500)/2;
		// x2 = (int) (width-500)/2;

		// Horizontal Right
		g.setColor(Color.red);
		g.fillRect(x1, 200, 50, 30);
		// Vertical Down
		g.setColor(Color.blue);
		g.fillRect(200, y1, 50, 30);

		// Horizontal Left
		g.setColor(Color.green);
		g.fillRect(x2, (int) height-250, 50, 30);
		// Vertical Up
		g.setColor(Color.yellow);
		g.fillRect((int) width-500, y2, 50, 30);
		tm.start();
	}

	public void actionPerformed(ActionEvent e)
	{
		// Horizontal
		if (x1 < 200 || x1 >= (int) width-500)
			velX1 = -velX1; // Change direction
		if (y1 < 200 || y1 >= (int) height-250)
			velY1 = -velY1; // Change direction
		if (x2 < 200 || x2 >= (int) width-500)
			velX2 = -velX2; // Change direction
		if (y2 < 200 || y2 >= (int) height-250)
			velY2 = -velY2; // Change direction

		x1 = x1 + velX1;
		y1 = y1 + velY1;
		x2 = x2 + velX2;
		y2 = y2 + velY2;
		repaint();
	}

	public static void main(String[] args)
	{
		sample s = new sample();

		JFrame jf = new JFrame();
		jf.setTitle("sample");
		jf.setSize(700, 700);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(s);
	}
}
