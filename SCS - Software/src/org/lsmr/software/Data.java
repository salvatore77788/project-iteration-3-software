package org.lsmr.software;

import java.util.Timer;
import java.util.TimerTask;


public class Data
    {
    private static  Data p = new Data();
    private boolean itemAdded = false;
    private boolean isDisabled = false;
    private static  Timer baggingTimer = null;
    private static  TimerTask baggingTimeTask;
    
        
    private Data ()
        {
    
        }
    
    public static Data getInstance() {
    return p;
    }
    
    public boolean getItemAdded()
        {
        return itemAdded;
        }
    
    public void setItemAdded(boolean set)
        {
        itemAdded = set;
        }
    
    public boolean getIsDisabled()
        {
        return isDisabled;
        }
    
    public void setIsDisabled(boolean set)
        {
        isDisabled = set;
        }
    
    public void startBaggingTimer()
        {
        System.out.println("CALLED");
        
        if (baggingTimer == null)
            baggingTimer = new Timer();
        
        baggingTimeTask = new BaggingTimerTask();
        
        baggingTimer.schedule (baggingTimeTask, (long)(5*1000)); //5 seconds
        }
    
    public void cancelBaggingTimerTask()
        {
        baggingTimeTask.cancel();
        }
    
    
    
    }
