package com.deneme;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by a on 4.10.2015.
 */
public class InactivityListener {

    private final Runnable action;
    private volatile int timeout = 15_000; // ms
    private int checkInterval = 2_000; // ms
    private ScheduledExecutorService service;
    private ScheduledFuture<?> lastTimer;
    private IdleTimeDetector idleTimeDetector;

    public InactivityListener(Runnable action, IdleTimeDetector idleTimeDetector) {
        this.action = action;
        this.idleTimeDetector = idleTimeDetector;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public synchronized void start() {
        stop();
        startTimerTask();
    }

    public synchronized void stop() {
        if(lastTimer != null) {
            lastTimer.cancel(false);
        }
    }

    private void startTimerTask() {
        service = Executors.newSingleThreadScheduledExecutor();
        lastTimer = service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (idleTimeDetector.getIdleTime() > timeout) {
                    action.run();
                    stop();
                }
            }
        }, 0, checkInterval, TimeUnit.MILLISECONDS);

    }
}
