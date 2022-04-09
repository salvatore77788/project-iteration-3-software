package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.TouchScreenObserver;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;

public class EnterMemberCardTest {
    private TouchScreen screen;
    JFrame frame;
    private volatile int found;
    //private 
    private SelfCheckoutStationSoftware theSoftware;

    @Before
    public void setup() throws Exception {
    	
    	
    	TestHardware hardware = new TestHardware();
    	theSoftware = new SelfCheckoutStationSoftware(hardware.scs);
    	
        screen = theSoftware.scs.screen;//new TouchScreen();
        frame = screen.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        found = 0;
        
    }

    @Test
    public void testBasic() {
        screen.attach(new TouchScreenObserver() {
            @Override
            public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}

            @Override
            public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {}
        });
        screen.detach(null);
        screen.detachAll();
        screen.disable();
        screen.enable();
    }

    @Test
    public void testFrameAutomatic() {
        JFrame f = screen.getFrame();
        JButton button = new JButton("foo");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                found++;
            }
        });

        f.add(button);

        screen.setVisible(false);
        button.doClick();

        assertEquals(1, found);
    }

    // Note that this is not a proper automated test. An automated test does not
    // force user interaction. Trust me: clicking repeatedly on buttons is tedious
    // and error-prone. When you suddenly discover a bug on your hundredth attempt,
    // what did you do? You stopped paying attention.  This is only a demo.
    //
    // Look at FrameDemo2 for a more detailed example of a standalone version.
    @Test
    public void testFrameManual() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setPreferredSize(new Dimension(500, 500));
                screen.setVisible(true);
                JButton foo = new JButton("Press to Close");
                foo.setPreferredSize(new Dimension(200, 100));
                foo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        found++;
                        frame.dispose();
                    }
                });

                frame.getContentPane().add(foo);
                frame.pack();
            }
        });

        // This loop is only needed to prevent the JUnit runner from closing the window
        // before you have a chance to interact with it. If you look at FrameDemo2,
        // which gets run as a standalone application, you will see that this is not
        // necessary.
        while(found < 1)
            ;

        assertEquals(1, found);
    }
    
    
    @Test
    public void enterMemberNumber() {
    
    	
    }
    
}
