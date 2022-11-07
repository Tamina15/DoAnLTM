/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author Tran Minh Nhat
 */
public class mainFrame extends JFrame implements Runnable {

    GamePanel gamePanel;
    PointPanel pointPanel;
    Thread thread;
    public final int FPS = 60;
    public static String FPScount = "NaN";

    public static double delta = 0;

    mainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = this.getContentPane();
        
        gamePanel = new GamePanel();
        pointPanel = new PointPanel();

        getContentPane().setLayout(new BorderLayout());

        c.add(pointPanel, BorderLayout.NORTH);
        c.add(gamePanel, BorderLayout.CENTER);
        
        this.pack();
       // this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.black);
        this.setVisible(true);

        newThread();
    }

    public final void newThread() {
        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        mainFrame mainFrame = new mainFrame();
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
                pointPanel.update();
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
