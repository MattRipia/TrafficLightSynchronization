package trafficlightsynchronisation;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class TrafficLightMain {

    public static void main(String[] args) 
    {
        // the main jframe that the chatGUI panel connects to
        JFrame frame = new JFrame("Lights");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // pass the TrafficLightGUI panel to the frame
        frame.getContentPane().add(new TrafficLightGUI());
        frame.pack();
        
        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width-frameDimension.width)/2,
			(screenDimension.height-frameDimension.height)/2);
	frame.setVisible(true);
        frame.setResizable(false);
    }
}
