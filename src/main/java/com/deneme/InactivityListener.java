package com.deneme;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Inactivity Listener
 */
public class InactivityListener {

    private final Runnable action;
    private volatile int timeout = 15_000; // ms
    private ScheduledExecutorService service;
    private ScheduledFuture<?> lastTimer;
    private IdleTimeDetector idleTimeDetector;

    public InactivityListener(Runnable action, IdleTimeDetector idleTimeDetector) {
        this.action = action;
        this.idleTimeDetector = idleTimeDetector;
        service = Executors.newSingleThreadScheduledExecutor();
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
        scheduleAction(getTimeout());
    }

    private void scheduleAction(long diff) {
        lastTimer = service.schedule(() -> {
            long diff1 = getTimeout() - idleTimeDetector.getIdleTime();
            if (diff1 <= 0) {
                action.run();
            }
            else {
                scheduleAction(diff1);
            }
        }, diff, TimeUnit.MILLISECONDS);
    }
}
