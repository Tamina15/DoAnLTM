/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.GamePanel;
import Client.PointPanel;
import static Utils.Constant.COMPLETE;
import static Utils.Constant.FPS;
import static Utils.Constant.PAUSE;
import static Utils.Constant.REMAINING_TIME;
import static Utils.Constant.START_TIME_BY_SECOND;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author Tran Minh Nhat
 */
public class mainFrame extends JFrame implements Runnable {

    GamePanel gamePanel;
    PointPanel pointPanel;
    Thread thread;
    public static String FPScount = "NaN";

    public static double delta = 0;
    public static int time = START_TIME_BY_SECOND;

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
        long timer = 0;
        int count = 0;
        long lastTime = System.nanoTime();
        while (thread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                if (!PAUSE) {
                    gamePanel.repaint();
                    pointPanel.update();
                }
                if (COMPLETE) {
                    AfterMatchFrame afterMatch = new AfterMatchFrame();
                    this.dispose();
                    return;
                }
                delta--;
                count++;
            }
            if (timer >= 1000000000) {
                if (!PAUSE) {
                    time--;
                }
                //System.out.println((time / 60) + ":" + (time % 60));
                REMAINING_TIME = String.format(" %02d:%02d", TimeUnit.SECONDS.toMinutes(time), TimeUnit.SECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(time)));
                FPScount = "" + count;
                count = 0;
                timer = 0;
            }
        }
    }
}
