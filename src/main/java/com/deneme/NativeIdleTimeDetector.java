package com.deneme;

import com.sun.jna.Platform;

/**
 * Created by a on 4.10.2015.
 */
public class NativeIdleTimeDetector implements IdleTimeDetector {
    private static class StaticHolder {
        static final IdleTimeDetector INSTANCE = create();

        private static IdleTimeDetector create() {
            IdleTimeDetector detector = null;
            if(Platform.isWindows()) {
                System.out.println("Using Windows Idle Time Detector");
                detector = new WindowsIdleTimeDetector();
            }
            else if(Platform.isX11()) {
                System.out.println("Using Linux Idle Time Detector");
                detector = new X11LinuxIdleTimeDetector();
            }
            else {
                System.out.println("Using Java Idle Time Detector");
                detector = new PureIdleTimeDetector();
            }
            return detector;
        }
    }

    public long getIdleTime() {
        return StaticHolder.INSTANCE.getIdleTime();
    }
}
