package com.deneme;

import java.awt.*;
import java.awt.event.AWTEventListener;

/**
 * Created by a on 4.10.2015.
 */
public class PureIdleTimeDetector implements IdleTimeDetector, AWTEventListener {

    private long EVENT_MASK = AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK |
            AWTEvent.MOUSE_MOTION_EVENT_MASK |
            AWTEvent.MOUSE_WHEEL_EVENT_MASK;

    private long lastActivity = System.currentTimeMillis();

    public PureIdleTimeDetector() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this, EVENT_MASK);
    }


    public long getIdleTime() {
        long lastActivity = this.lastActivity;
        return System.currentTimeMillis() - lastActivity;
    }

    public void eventDispatched(AWTEvent event) {
        lastActivity = System.currentTimeMillis();
    }
}
