package GUI;

import static Utils.Constant.FPS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class mainFrame extends JFrame implements Runnable {

    static Client client;

    GamePanel gamePanel;
    public PointPanel pointPanel;
    Thread thread;
    public String FPScount = "NaN";

    public double delta = 0;

    public boolean ready;

    public String Id_Player = "";

    public String result = "S";

    public static Container c;
    public static int currentNumber;
    static boolean blackout = false;

    public static int blackoutTime;
    public static int amount;

    mainFrame() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        c = this.getContentPane();
        delta = 0;
        ready = false;

        //gamePanel = new GamePanel();
        pointPanel = new PointPanel();

        getContentPane().setLayout(new BorderLayout());

        c.add(pointPanel, BorderLayout.NORTH);
        //c.add(gamePanel, BorderLayout.CENTER);

        this.setSize(1300, 730);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.black);
        this.setVisible(true);

        newThread();
        ready = true;
    }

    mainFrame(Client client) {
        this.client = client;

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setUndecorated(false);
        c = this.getContentPane();
        delta = 0;
        ready = false;

        //gamePanel = new GamePanel();
        pointPanel = new PointPanel();

        getContentPane().setLayout(new BorderLayout());

        c.add(pointPanel, BorderLayout.NORTH);
        //c.add(gamePanel, BorderLayout.CENTER);

        this.setSize(1300, 730);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.black);
        this.setVisible(true);

        newThread();
    }

    private void newThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        delta = 0;
        double drawInterval = 1000000000 / FPS;
        long timer = 0;
        int count = 0;
        long lastTime = System.nanoTime();

        //Dem nguoc 3s
        JLabel lbl_Count = new JLabel("");
        lbl_Count.setOpaque(true);
        lbl_Count.setForeground(Color.MAGENTA);
        lbl_Count.setBackground(Color.BLACK);
        lbl_Count.setFont(new Font("MV Boli", Font.BOLD, 70));

        lbl_Count.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Count.setBounds(212, 104, 250, 125);
        this.add(lbl_Count);

        // Cho du so luong nguoi choi
        while (!ready) {
            lbl_Count.setText("Waiting to find another Player");
        }

        //Dem nguoc 3s
        lbl_Count.setFont(new Font("MV Boli", Font.BOLD, 90));
        try {
            lbl_Count.setText("3");
            System.out.println("Start: 3");
            Thread.sleep(1000);

            lbl_Count.setText("2");
            System.out.println("Start: 2");
            Thread.sleep(1000);

            lbl_Count.setText("1");
            System.out.println("Start: 1");
            Thread.sleep(1000);

            lbl_Count.setText("Start Game");
            System.out.println("Start Game");
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        } finally {
            gamePanel = new GamePanel();
            c.add(gamePanel, BorderLayout.CENTER);
            this.pack();
        }
        this.remove(lbl_Count);

        while (thread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                if (!blackout) {
                    gamePanel.repaint();
                } else {
                    gamePanel.setBackground(Color.BLACK);
                    gamePanel.setForeground(Color.BLACK);
                }
                pointPanel.repaint();
                delta--;
                count++;
            }
            if (timer >= 1000000000) {
                if (blackout) {
                    blackoutTime--;
                }
                if (blackoutTime <= 0) {
                    blackout = false;
                }
                FPScount = "" + count;
                count = 0;
                timer = 0;
            }
        }
    }

    public void setTime(String time) {
        pointPanel.setTime(time);
    }

    public void setNextNumber(String number) {
        currentNumber = Integer.parseInt(number);
        pointPanel.setNumber(number);
    }

    public void setNumber(String string, Color c) {
        gamePanel.Fill(currentNumber, c);
    }

    public void setLuckyNumber(String number) {
        currentNumber = Integer.parseInt(number);
        pointPanel.setLuckyNumber(number);
    }

    public static void Click(int number) {
        client.send("click", String.valueOf(number));
    }

    public static void Surrender() {
        client.sendOnlyCmd("surrender");
    }

    public static void Abort() {
        client.sendOnlyCmd("AbortGame");
    }

    public void Result(String result) {
        System.out.println("Result = " + result);
        AfterMatchFrame afterMatch = new AfterMatchFrame(client, result);
        this.dispose();
    }

    public static void PowerUp() {
        client.sendOnlyCmd("UsePower");
    }

    public void Blackout() {
        blackout = true;
        blackoutTime = 3;
    }

}
