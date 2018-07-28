package introos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class multichoochoo extends JPanel
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double height = screenSize.getHeight();
	double width = screenSize.getWidth();
	final int INCREMENT = 5; // Speed
    List<Train> trains;
    
    public multichoochoo() 
    {
        trains = new ArrayList<>();

    	new Timer(1000, new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	trains.add(new Train(200, 200));
            }
          }).start();
        ActionListener animate = new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                for (Train train : trains) 
                {
                    train.move();
                    repaint();
                }
            }
        };
        // Time to update view in milliseconds
        Timer timer = new Timer(5, animate);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double height = screenSize.getHeight();
    	double width = screenSize.getWidth();
        //Draw Outer Line
      	g.drawRect(200, 200, (int) width-650, (int) height-420);
  		//Draw Inner Line
  		g.drawRect(250, 230, (int) width-750, (int) height-480);
        for (Train train: trains) 
        {
            train.drawTrain(g);
        }
    }

    public class Train 
    {
        int x = 200, y = 200, // Position
        	velX = INCREMENT, velY = INCREMENT; // Speed
        
        public Train(int x, int y) 
        {
            this.x = x;
            this.y = y;
        }
       
        public void drawTrain(Graphics g) 
        {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 50, 30);
        }

        public void move() 
        {
        	// If max horizontal right
    		if (x >= (int) width-500 && y <= 200)
    		{
    			velX = 0;
    			velY = INCREMENT;
    			x = (int) width-500;
    			y = 200;
    		}
    		// If max vertical down
    		else if (y >= (int) height-250 && x >= (int) width-500)
    		{
    			velX = -INCREMENT;
    			velY = 0;
    			y = (int) height-250;
    			x = (int) width-500;
    		}
    		// If max horizontal left
    		else if (x < 200 && y >= (int) height-250)
    		{
    			velX = 0;
    			velY = -INCREMENT;
    			x = 200;
    			y = (int) height-250;
    		}
    		// If max vertical left
    		else if (y <= 200 && x <= 200)
    		{
    			velX = INCREMENT;
    			velY = 0;
    			x = 200;
    			y = 200;
    		}
    		x = x + velX;
    		y = y + velY;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new multichoochoo());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(700,700);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}