package trafficlightsynchronisation;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TrafficLightModel
{
    // The 'lightSemaphore' is the shared resource that the lock manages. It represents the intersection the lights are managing.
    private int lightSemaphore;      
    private final TrafficLight firstLight;
    private final TrafficLight secondLight;
    private final TrafficLightGUI gui;
    
    public TrafficLightModel(TrafficLightGUI gui)
    {
        this.gui = gui;
        this.lightSemaphore = 1;
        this.firstLight = new TrafficLight(this);
        this.secondLight = new TrafficLight(this);
    }
    
    // the changeLight method will first aquire a semaphore lock before proceeding.
    // once the lock is aquired, the method disables the input buttons and starts the relevant threads
    // this is synchronised on the 'this' object so that only the current 'model' object can access this method synchronously
    // by doing this, it prevents the ability for the same object to change the light semaphore at the same time, which might result in more than
    // one light obtaining the lock
    protected synchronized void changeLight(int lightNo)
    {
        aquireLock();
        gui.disableButtons();
        
        if(lightNo == 0)
        {
            Thread a = new Thread(getFirstLight());
            a.start();
        }
        else
        {
            Thread b = new Thread(getSecondLight());
            b.start();
        }
    }
    
    // This method first notifys all waiting threads that have been blocked, this will allow 
    // any waiting threads to wake up so they can try aquire the lock using the 'aquireLock()' method.
    // The releaseLock() method will then increase the semaphore counter which increases the shared resources back to one
    // this allows the threads that were previously waiting in the 'aquireLock()' method to successfully continue with the new lock.
    private synchronized void releaseLock(){
        notifyAll();
        System.out.println("lock released!");
        lightSemaphore++;
        gui.enableButtons();
    }
    
    // The 'aquireLock()' method is a blocking execution lock based on a binary semaphore. If the shared resource is not 
    // availiable (when the light semaphore is 0), the method will 'wait()' until notified.
    // Once notified, the lightSemaphore condition is checked to ensure the shared resource is available, and if it is, the
    // lightSemaphore will be decreased to 0 again, meaning that a lock has been obtained.
    private synchronized void aquireLock()
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
    
    // encapsulating access to the lights
    public TrafficLight getFirstLight() {
        return firstLight;
    }

    public TrafficLight getSecondLight() {
        return secondLight;
    }

    // An enum discribing the colour of the light
    private static enum LightColour{
        RED, YELLOW, GREEN;
    }
    
    // There are two traffic lights in my implementation. To update the lights colour, the thread needs
    // to be started. This will execute the run method once, which will actually change the colour of the light.
    // Once the run method has finished, the thread dies and the 'releaseLock()' method is called. This allows another
    // light to be activated.
    protected class TrafficLight implements Runnable
    {
        // Each Light has a current colour as well as an instance of the model (so the light can release the lock)
        private LightColour currentState;
        private final TrafficLightModel model;
        
        private TrafficLight(TrafficLightModel model){
            this.model = model;
            this.currentState = LightColour.RED;
        }
        
        private void enableLight(){
            this.currentState = LightColour.GREEN;
        }
        
        private void disableLight(){
            this.currentState = LightColour.RED;
        }
        
        public LightColour getCurrentState(){
            return currentState;
        }

        @Override
        public void run() 
        {
            try 
            {
                // this enables the light for 5000ms (5s) and then releases the lock allowing another thread to run
                enableLight();
                Thread.sleep(3000);
                currentState = LightColour.YELLOW;
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
