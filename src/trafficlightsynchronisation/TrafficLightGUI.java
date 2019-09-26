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
    TrafficLightModel model;
    Timer timer;
    JButton enableFirstLight, enableSecondLight;
    
    public TrafficLightGUI()
    {
        super();
        super.setBackground(Color.WHITE);
        super.setPreferredSize(new Dimension(200,300));
        
        enableFirstLight = new JButton("Enable first light!");
        enableFirstLight.setLocation(100, 100);
        enableFirstLight.setVisible(true);
        enableFirstLight.addActionListener(this);
        
        enableSecondLight = new JButton("Enable second light!");
        enableSecondLight.addActionListener(this);
        enableSecondLight.setVisible(true);
        enableSecondLight.setLocation(100, 200);
        
        this.add(enableFirstLight);
        this.add(enableSecondLight);
        
        // model initialization, pass this view into the chat client so it can alter the chat windows
        model = new TrafficLightModel(this);
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
        
        switch (model.firstLight.currentState) 
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
        
        switch (model.secondLight.currentState) 
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
    
    public void enableButtons(){
        enableFirstLight.setEnabled(true);
        enableSecondLight.setEnabled(true);
    }
    
    public void disableButtons(){
        enableFirstLight.setEnabled(false);
        enableSecondLight.setEnabled(false);
    }

    @Override
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
        
        repaint();
    }
}
