package com.deneme;

import examples.imageviewer.Login;
import examples.imageviewer.NavigableImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Main class
 */
public class Main {

    public static void main(String[] args) {
//		if (args.length == 0) {
//			System.out.println("Usage: java NavigableImagePanel imageFilename");
//			System.exit(1);
//		}

        final IdleTimeDetector detector;
        if(args.length > 0 && "-pure".equals(args[0])) {
            detector = new PureIdleTimeDetector();
        }
        else {
            detector = new NativeIdleTimeDetector();
        }

        //final String filename = args[0];
        final String filename = "ff_x10_001.JPG";

        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Navigable Image Panel");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            NavigableImagePanel panel = new NavigableImagePanel();
            try {
                final BufferedImage image = ImageIO.read( Main.class.getClassLoader().getResourceAsStream(filename) );
                panel.setImage(image);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            frame.getContentPane().add(panel, BorderLayout.CENTER);

            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle bounds = ge.getMaximumWindowBounds();
            frame.setSize(new Dimension(bounds.width, bounds.height));
            frame.setVisible(true);

            final Login login = new Login();

            final InactivityListener listener = new InactivityListener(() -> {
                frame.setGlassPane(login);
                login.setVisible(true);
                login.requestFocus();
            }, detector);
            listener.setTimeout(8_000);


            login.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(login.isSuccessful()) {
                        login.setVisible(false);
                        listener.start();
                    }
                }
            });

            listener.start();

        });
    }


}
