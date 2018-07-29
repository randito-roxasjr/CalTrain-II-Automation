package introos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

    	new Timer(1000, new ActionListener() 
    	{
            @Override
			public void actionPerformed(ActionEvent e) 
            {
            	trains.add(new Train(250, 150));
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
      	g.drawRect(250, 150, (int) width-630, (int) height-405);
  		//Draw Inner Line
  		g.drawRect(325, 255, (int) width-770, (int) height-610);
  		
  		// Import Station Image Size: 200 x 65 px
  		ImageIcon i = new ImageIcon("C:\\Users\\Dean\\eclipse-workspace\\INTRO-OS\\images\\station.png");
  		
  		// Paint Clockwise
  		i.paintIcon(this, g, 150, 85);
  		i.paintIcon(this, g, (int) (width-500)/2, 85);
  		i.paintIcon(this, g, (int) (width-500) - 50, 85);
  		i.paintIcon(this, g, (int) (width-500) + 120, (int) ((height-250)/2 + 65/2));
  		i.paintIcon(this, g, (int) (width-500) - 50, (int) height-255);
  		i.paintIcon(this, g, (int) (width-500)/2 + 50, (int) height-255);
  		i.paintIcon(this, g, 150, (int) height-255);
  		i.paintIcon(this, g, 50, (int) ((height-250)/2 + 65/2));
  		
        for (Train train: trains) 
        {
            train.drawTrain(g);
        }
    }

    public class Train 
    {
        int x = 250, y = 150, // Position
        	velX = INCREMENT, velY = INCREMENT; // Speed
        
        public Train(int x, int y) 
        {
            this.x = x;
            this.y = y;
        }
       
        public void drawTrain(Graphics g) 
        {
        	// Import and paint train size 74 x 105
        	BufferedImage train;
			try {
				train = ImageIO.read(new File("C:\\Users\\Dean\\eclipse-workspace\\INTRO-OS\\images\\train.png"));
				g.drawImage(train, x, y, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        public void move() 
        {
        	// If max horizontal right
    		if (x >= (int) width-450 && y <= 150)
    		{
    			velX = 0;
    			velY = INCREMENT;
    			x = (int) width-450;
    			y = 150;
    		}
    		// If max vertical down
    		else if (y >= (int) height-355 && x >= (int) width-450)
    		{
    			velX = -INCREMENT;
    			velY = 0;
    			y = (int) height-355;
    			x = (int) width-450;
    		}
    		// If max horizontal left
    		else if (x < 250 && y >= (int) height-355)
    		{
    			velX = 0;
    			velY = -INCREMENT;
    			x = 250;
    			y = (int) height-355;
    		}
    		// If max vertical left
    		else if (y <= 150 && x <= 250)
    		{
    			velX = INCREMENT;
    			velY = 0;
    			x = 250;
    			y = 150;
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