package trafficlightsynchronisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TrafficLightGUI extends JPanel implements ActionListener
{
    private final TrafficLightModel model;
    private final Timer timer;
    private final JButton enableFirstLight, enableSecondLight;
    
    public TrafficLightGUI()
    {
        super();
        super.setBackground(Color.WHITE);
        super.setPreferredSize(new Dimension(200,300));
        
        // first light button
        enableFirstLight = new JButton("Enable first light!");
        enableFirstLight.setLocation(100, 100);
        enableFirstLight.setVisible(true);
        enableFirstLight.addActionListener(this);
        
        // second light button
        enableSecondLight = new JButton("Enable second light!");
        enableSecondLight.addActionListener(this);
        enableSecondLight.setVisible(true);
        enableSecondLight.setLocation(100, 200);
        
        // adding the buttons to the GUI
        this.add(enableFirstLight);
        this.add(enableSecondLight);
        
        // model initialization, pass the view(this) into the model, so that the model can directly change the view.
        model = new TrafficLightModel(this);
        
        // the timer that is used to repaint my GUI every 10ms
        timer = new Timer(10, this);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);       
        int size = 50;

        // first light
        g.setColor(Color.BLACK);
        g.fillRect(20, 100, size, size * 3);
        g.setColor(Color.WHITE);
        g.fillOval(20, 100, size / 2, size / 2);
        g.fillOval(20, 100 + size, size / 2, size / 2);
        g.fillOval(20, 100 + (size * 2), size / 2, size / 2);
        
        // first lights colour
        switch (model.getFirstLight().getCurrentState()) 
        {
            case RED:
                g.setColor(Color.RED);
                g.fillOval(20, 100, size / 2, size / 2);
                break;
            case YELLOW:
                g.setColor(Color.ORANGE);
                g.fillOval(20, 100 + size, size / 2, size / 2);
                break;
            case GREEN:
                g.setColor(Color.GREEN);
                g.fillOval(20, 100 + (size * 2), size / 2, size / 2);
                break;
            default:
                break;
        }
        
        // second light
        g.setColor(Color.BLACK);
        g.fillRect(130, 100, size, size * 3);
        g.setColor(Color.WHITE);
        g.fillOval(130, 100, size / 2, size / 2);
        g.fillOval(130, 100 + size, size / 2, size / 2);
        g.fillOval(130, 100 + (size * 2), size / 2, size / 2);
        
        // second lights colour
        switch (model.getSecondLight().getCurrentState()) 
        {
            case RED:
                g.setColor(Color.RED);
                g.fillOval(130, 100, size / 2, size / 2);
                break;
            case YELLOW:
                g.setColor(Color.ORANGE);
                g.fillOval(130, 100 + size, size / 2, size / 2);
                break;
            case GREEN:
                g.setColor(Color.GREEN);
                g.fillOval(130, 100 + (size * 2), size / 2, size / 2);
                break;
            default:
                break;
        }
    }
    
    // enables both buttons
    public void enableButtons(){
        enableFirstLight.setEnabled(true);
        enableSecondLight.setEnabled(true);
    }
    
    // disables both buttons
    public void disableButtons(){
        enableFirstLight.setEnabled(false);
        enableSecondLight.setEnabled(false);
    }

    @Override
    // This is the method that is called once a button is pressed, or is executed
    // every 10ms (due to the timer above - this repaints the lights based on the current state/model)
    public void actionPerformed(ActionEvent e) 
    {
        Object source = e.getSource();
        
        if(source == enableFirstLight)
        {
            System.out.println("changing first light!");
            model.changeLight(0);
            System.out.println("changed first light!");
        }
        else if(source == enableSecondLight)
        {
            System.out.println("changing second light!");
            model.changeLight(1);
            System.out.println("changed second light!");
        }
        
        // the repaint method gets called every 10ms to represent the current state of the model
        repaint();
    }
}
