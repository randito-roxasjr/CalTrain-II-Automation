package Randi;
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
	int train_num = 1;
	int global = 0;

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
	Timer timer;
	
    public View() 
    {
    	trains = new ArrayList<>();
    	addTrain();
    	
    	// Time to update view in milliseconds
    	timer = new Timer(2, new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
            	for (Train train: trains) 
                {
            		repaint();
            		global=0;
            	}

            }
        });
        timer.start();
    }
    
    public void addTrain() {
    	trains.add(new Train(250, 150));
    }
    
    public void exitTrain(int i) {
    //	trains.remove(i);
    	trains.get(i).show = false;
    }
    

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double height = screenSize.getHeight();
    	double width = screenSize.getWidth();
    	// Draw H Train Track 1935 x 105
    	ImageIcon htracks = new ImageIcon("C:\\Users\\Dean\\eclipse-workspace\\INTRO-OS\\images\\htraintracks.png");
    	// Top
    	htracks.paintIcon(this, g, 250, 150);
    	// Bottom
    	htracks.paintIcon(this, g, 250, 1245);
    	// Draw V Train Track 80 x 1200
    	ImageIcon vtracks = new ImageIcon("C:\\Users\\Dean\\eclipse-workspace\\INTRO-OS\\images\\vtraintracks.png");
    	// Left
    	vtracks.paintIcon(this, g, 250, 150);
    	// Right
    	vtracks.paintIcon(this, g, 2105, 150);
    	
//    		//Draw Outer Line
//      	g.drawRect(250, 150, (int) width-offsetR-130, (int) height-offsetB-155);
//  		//Draw Inner Line
//  		g.drawRect(325, 255, (int) width-offsetR-275, (int) height-offsetB-360);
  		
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
  			global++;
  			if (train.show)
  				train.drawTrain(g);
        }
    }

    public class Train 
    {

    	BufferedImage train;
    	boolean show;
        int x = 250, y = 150, // Position
        	velX = INCREMENT, velY = INCREMENT; // Speed
	        
        public Train(int x, int y) 
        {
            this.x = x;
            this.y = y;
            this.show = true;
        }
       
        public void drawTrain(Graphics g) 
        {
        	// Import and paint train size 74 x 105
			try {
				String string = "C:\\Users\\Dean\\eclipse-workspace\\INTRO-OS\\images\\train"+global+".png";
				train = ImageIO.read(new File(string));
				train_num++;
				g.drawImage(train, x, y, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}