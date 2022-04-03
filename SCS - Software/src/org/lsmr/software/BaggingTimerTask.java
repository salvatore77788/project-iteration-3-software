package org.lsmr.software;

import java.util.TimerTask;

public class BaggingTimerTask extends TimerTask
{
private Data data = Data.getInstance();
@Override
public void run()
    {                
    if (!data.getItemAdded())
        {
        System.out.println("5 second notice: Please add item to bagging area");
        data.setIsDisabled(true);
        //Let attendant know to render assistance 
        }                   
    }
};