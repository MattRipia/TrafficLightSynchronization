package trafficlightsynchronisation;

import java.util.logging.Level;
import java.util.logging.Logger;

class TrafficLightModel
{
    private int lightSemaphore;
    public TrafficLight firstLight;
    public TrafficLight secondLight;
    public TrafficLightGUI gui;
    
    public TrafficLightModel(TrafficLightGUI gui)
    {
        this.gui = gui;
        lightSemaphore = 1;
        firstLight = new TrafficLight(this);
        secondLight = new TrafficLight(this);
    }
    
    public synchronized void changeLight(int lightNo)
    {
        aquireLock();
        gui.disableButtons();
        if(lightNo == 0)
        {
            Thread a = new Thread(firstLight);
            a.start();
        }
        else
        {
            Thread b = new Thread(secondLight);
            b.start();
        }
    }
    
    public synchronized void releaseLock(){
        notifyAll();
        System.out.println("lock released!");
        lightSemaphore++;
        gui.enableButtons();
    }
    
    public synchronized void aquireLock()
    {
        while(lightSemaphore == 0)
        {
            try {
                wait();
            } 
            catch (InterruptedException ex) 
            { Logger.getLogger(TrafficLightModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("lock aquired!");
        lightSemaphore--;
    }

    public static enum lightColour{
        RED, YELLOW, GREEN;
    }
    
    public class TrafficLight implements Runnable{
        public lightColour currentState;
        public TrafficLightModel model;
        
        public TrafficLight(TrafficLightModel model){
            this.model = model;
            this.currentState = lightColour.RED;
        }
        
        public void enableLight(){
            this.currentState = lightColour.GREEN;
        }
        
        public void disableLight(){
            this.currentState = lightColour.RED;
        }

        @Override
        public void run() 
        {
            try 
            {
                enableLight();
                Thread.sleep(3000);
                currentState = lightColour.YELLOW;
                Thread.sleep(1500);
                disableLight();
                Thread.sleep(500);
                model.releaseLock();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(TrafficLightModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
