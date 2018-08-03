package introos;
import java.awt.Button;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class View extends JPanel
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double height = screenSize.getHeight();
	double width = screenSize.getWidth();
	

	//Train List
    List<Train> trains;
    
	// Specifications
	final int INCREMENT = 5; // Speed
	int offsetR = 500, offsetB = 250;
	
	// Station Coordinates
	final int
	s1X = 250, s1Y = 150,
	s2X = (int) ((width-offsetR)/2), s2Y = 150,
	s3X = (int) ((width-offsetR)), s3Y = 150,
	s4X = (int) ((width-offsetR)), s4Y = (int) ((height-offsetB)/2),
	s5X = (int) ((width-offsetR)), s5Y = (int) (height-offsetB),
	s6X = (int) ((width-offsetR)/2), s6Y = (int) (height-offsetB),
	s7X = 250, s7Y = (int) (height-offsetB),
	s8X = 250, s8Y = (int) ((height-offsetB)/2);
	
    public View() 
    {
    	trains = new ArrayList<>();
    	trains.add(new Train(250, 150));
        ActionListener animate = new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
//                for (Train train : trains) 
//                {
                    trains.get(0).move();
                    repaint();
                    trains.get(0).bottomStop();
                    repaint();
                //    trains.get(0).startMove();
                 //   repaint();
//                }
            }
        };
        // Time to update view in milliseconds
        Timer timer = new Timer(2, animate);
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
      	g.drawRect(250, 150, (int) width-offsetR-130, (int) height-offsetB-155);
  		//Draw Inner Line
  		g.drawRect(325, 255, (int) width-offsetR-275, (int) height-offsetB-360);
  		
  		// Import Station Image Size: 200 x 65 px
  		ImageIcon i = new ImageIcon("C:\\Users\\Dean\\eclipse-workspace\\INTRO-OS\\images\\station.png");
  		
  		// Paint Station Clockwise
  		i.paintIcon(this, g, s1X-100, s1Y-65);
  		i.paintIcon(this, g, s2X, s2Y-65);
  		i.paintIcon(this, g, s3X, s3Y-65);
  		i.paintIcon(this, g, s4X+120, s4Y+65/2);
  		i.paintIcon(this, g, s5X, s5Y);
  		i.paintIcon(this, g, s6X, s6Y);
  		i.paintIcon(this, g, s7X-100, s7Y);
  		i.paintIcon(this, g, s8X-200, s8Y);
  		
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

        public void startMove() {
        	velX = INCREMENT;
        }
        
        public void topStop() {
        	// If at Station 3
    		if (x >= s3X && y == s3Y)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(3);
    			velX = 0;
    			velY = 0;
    		}
    		
    		// If at Wait 3.2
    		else if (x >= s3X-150 && y == s3Y)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(3);
    			velX = 0;
    			velY = 0;
    		}
    		
    		// If at Wait 3.1
    		else if (x >= s3X-300 && y == s3Y)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(3);
    			velX = 0;
    			velY = 0;
    		}
    		
    		// If at Station 2
    		else if (x >= s2X && y == s2Y)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(2);
    			velX = 0;
    		}
    		
    		// If at Wait 2.2
    		else if (x >= s2X-150 && y == s2Y)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(2);
    			velX = 0;
    		}
    		// If at Wait 2.1
    		else if (x >= s2X-300 && y == s2Y)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(2);
    			velX = 0;
    		}
    		
    		// If at Station 1
    		else if (x >= s1X && y == s1Y)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(1);
    			velX = 0;
    			velY = 0;
    		}
        }

        public void rightStop()
        {
        	// If at Station 5
    		if (x == s5X && y >= s5Y-105)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(5);
    			velX = 0;
    			velY = 0;
    		}
    		
    		// If at Wait 5.2
    		else if (x == s5X && y >= s5Y-105-150)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(5);
    			velX = 0;
    			velY = 0;
    		}
    		
    		// If at Station 5.1
    		else if (x == s5X && y >= s5Y-105-300)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(5);
    			velX = 0;
    			velY = 0;
    		}
    		
    		// If at Station 4
    		else if (x == s4X+50 && y >= s4Y)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(4);
    			velY = 0;
    		}
    		
    		// If at Wait 4.2
    		else if (x == s4X+50 && y >= s4Y-150)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(4);
    			velY = 0;
    		}
    		
    		// If at Station 4.1
    		else if (x == s4X+50 && y >= s4Y-300)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(4);
    			velY = 0;
    		}
        }
        
        public void bottomStop()
        {
        	// If at Station 7
    		if (x <= s7X && y == s7Y-105)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(7);
    			velX = 0;
    		}
    		// If at Wait 7.2
    		else if (x <= s7X+150 && y == s7Y-105)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(7);
    			velX = 0;
    		}
    		// If at Wait 7.1
    		else if (x <= s7X+300 && y == s7Y-105)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(7);
    			velX = 0;
    		}
    		// If at Station 6
    		else if (x <= s6X &&  y == s6Y-105)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(6);
    			velX = 0;
    		}
    		// If at Wait 6.2
    		else if (x <= s6X+150 &&  y == s6Y-105)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(6);
    			velX = 0;
    		}
    		// If at Wait 6.1
    		else if (x <= s6X+300 &&  y == s6Y-105)
    		{
    			System.out.print("Waiting for station");
    			System.out.println(6);
    			velX = 0;
    		}
        }
        
        public void leftStop() 
        {
        	// If at Wait 1.2
        	if (x == s1X && y == s1Y)
    		{
        		System.out.print("Waiting for station");
    			System.out.println(1);
    			velX = 0;
    			velY = 0;
    		}
        	// If at Wait 1.1
        	else if (x == s1X && y <= s1Y)
    		{
        		System.out.print("Waiting for station");
    			System.out.println(1);
    			velX = 0;
    			velY = 0;
    		}
        	// If at Station 8
        	else if (x == s8X && y <= s8Y)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(8);
    			velY = 0;
    		}
        	// If at Wait 8.2
    		else if (x == s8X && y <= s8Y+150)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(8);
    			velY = 0;
    		}
    		// If at Wait 8.1
    		else if (x == s8X && y <= s8Y+300)
    		{
    			System.out.print("I'm at station: ");
    			System.out.println(8);
    			velY = 0;
    		}
        }
        
        public void move() 
        {
    		// If max horizontal right
    		if (x >= (int) width-offsetR+50 && y <= 150)
    		{
    			velX = 0;
    			velY = INCREMENT;
    			x = (int) width-offsetR+50;
    			y = 150;
    		}
    		// If max vertical down
    		else if (y >= (int) height-offsetB-105 && x >= (int) width-offsetR+50)
    		{
    			velX = -INCREMENT;
    			velY = 0;
    			y = (int) height-offsetB-105;
    			x = (int) width-offsetR+50;
    		}
    		// If max horizontal left
    		else if (x < 250 && y >= (int) height-offsetB-105)
    		{
    			velX = 0;
    			velY = -INCREMENT;
    			x = 250;
    			y = (int) height-offsetB-105;
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
                frame.add(new View());
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