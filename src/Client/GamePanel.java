/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Server;
import static Utils.Constant.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Tran Minh Nhat
 */
public class GamePanel extends JPanel implements MouseListener, MouseWheelListener {

    private final ArrayList<Number> numbers = new ArrayList<>();

    private final Random rand;
    private final Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE};

    Double zoomFactor = 1.0d;
    boolean zoomer;
    AffineTransform at;

    public GamePanel() {

        Server.RandomizeArray();
//        this.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);

        rand = new Random();

        int map[][] = new int[ROW][COLUMN];
        for (int i = 0; i < NUMBER_AMOUNT; i++) {

            int mRow = rand.nextInt(ROW);
            int mCol = rand.nextInt(COLUMN);

            while (map[mRow][mCol] == 1) {
                mRow = rand.nextInt(ROW);
                mCol = rand.nextInt(COLUMN);
            }

            map[mRow][mCol] = 1;

            Number n = new Number(i, "" + (i + 1), mRow, mCol, rand.nextInt(TILE_SIZE - ORIGINAL_TILE_SIZE), rand.nextInt(TILE_SIZE - ORIGINAL_TILE_SIZE), ORIGINAL_TILE_SIZE + 10, ORIGINAL_TILE_SIZE + 10, getRandomColor(), TILE_SIZE);
            numbers.add(n);

        }
    }
    int arc = 1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Zooming
        g2.setStroke(new BasicStroke(2f));
        if (zoomer == true) {
            at = new AffineTransform();
            at.scale(zoomFactor, zoomFactor);
            //zoomer = false;
            g2.transform(at);
        }

        if (update()) {
            g2.setColor(Color.green);
            g2.drawString("Game Over", 0, TILE_SIZE / 2);
            g2.dispose();
            return;
        }

//        for (int i = 0; i < COLUMN; i++) {
//            g2.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, panelHeight);
//        }
//        for (int i = 0; i < ROW; i++) {
//            g2.drawLine(0, i * TILE_SIZE, panelWidth, i * TILE_SIZE);
//        }
        g2.setFont(new Font("MV Boli", Font.BOLD, 15));
        FontMetrics metrics = getFontMetrics(g.getFont());
        for (Number n : numbers) {
            g2.setColor(n.getColor());
            if (n.isFill()) {
                g2.fillRoundRect(n.getX(), n.getY(), n.getWidth(), n.getHeight(), 10, 10);
            } else {
                if (Integer.parseInt(n.getName()) == Server.currentNumber + 1) {
                    g2.drawOval(n.getX() - arc / 2, n.getY() - arc / 2, n.getWidth() + arc, n.getHeight() + arc);
                }
                g2.drawRoundRect(n.getX(), n.getY(), n.getWidth(), n.getHeight(), 10, 10);
            }
            g2.drawString(n.getName(), n.getX() + (n.getWidth() - metrics.stringWidth(n.getName())) / 2, n.getY() + n.getHeight() / 2 + metrics.getHeight() / 4);
        }

        g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g2.setColor(Color.green);
        g2.drawString(mainFrame.FPScount, 0, TILE_SIZE / 2);
        g2.drawString("" + mainFrame.delta, TILE_SIZE, TILE_SIZE / 2);
        g2.setColor(Color.white);
        g2.drawString("" + (Server.currentNumber + 1), PANEL_WIDTH / 2, TILE_SIZE);

        arc = (arc + 1) % 20;
        g2.dispose();
    }

    public boolean update() {
        for (Number n : numbers) {
            if (!n.isFill()) {
                return false;
            }
        }
        return true;
    }

    public void checkCollision(int x, int y) {
        for (Number n : numbers) {
            if (n.getHitbox().contains(x, y)) {
                if (n.getId() == Server.currentNumber) {
                    n.setFill(true);
                    Server.NextNumber();
                    arc = 0;
                }
            }
        }
    }

    protected Color getRandomColor() {
        return colors[rand.nextInt(colors.length)];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        checkCollision(x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        checkCollision(x, y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public Double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double factor) {
        if (factor < this.zoomFactor) {
            this.zoomFactor = this.zoomFactor / 1.1;
        } else {
            this.zoomFactor = factor;
        }
        this.zoomer = true;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //Zoom in
        if (e.getWheelRotation() < 0) {
            this.setZoomFactor(1.1 * this.getZoomFactor());

        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            this.setZoomFactor(this.getZoomFactor() / 1.1);
        }

    }

}
