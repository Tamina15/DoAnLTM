/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Client;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Tran Minh Nhat
 */
public class mainFrame extends JFrame implements Runnable {

    GamePanel gamePanel;
    Thread thread;
    public final int FPS = 60;
    public static String FPScount = "NaN";

    public static double delta = 0;

    mainFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.black);
        frame.setVisible(true);
        newThread();
    }

    public void newThread() {
        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        new mainFrame();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        long lastTime = System.nanoTime();
        long timer = 0;
        int count = 0;
        while (thread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                gamePanel.repaint();
                delta--;
                count++;
            }
            if (timer >= 1000000000) {
                FPScount = "" + count;
                count = 0;
                timer = 0;
            }
        }
    }
}
