/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doanltm;

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

    public final int originalTileSize = 16;
    public final int scale = 4;
    public final int tileSize = originalTileSize * scale;
    public final int col = 20;
    public final int row = 10;
    public final int panelWidth = tileSize * col;
    public final int panelHeight = tileSize * row;

    private ArrayList<Num> numbers = new ArrayList<>();
    private int amount = 60;
    private int currentID = 0;
    private Random rand;
    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE};
    
    Double zoomFactor = 1.0d;
    boolean zoomer;
    AffineTransform at;

    public GamePanel() {

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setDoubleBuffered(true);
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);

        rand = new Random();

        int map[][] = new int[row][col];
        if (amount > col * row) {
            amount = col * row;
        }
        for (int i = 0; i < amount; i++) {

            int mRow = rand.nextInt(row);
            int mCol = rand.nextInt(col);

            while (map[mRow][mCol] == 1) {
                mRow = rand.nextInt(row);
                mCol = rand.nextInt(col);
            }

            map[mRow][mCol] = 1;

            Num n = new Num(i, "" + (i + 1), mRow, mCol, rand.nextInt(tileSize - originalTileSize), rand.nextInt(tileSize - originalTileSize), originalTileSize+10, originalTileSize+10, getRandomColor(), tileSize);
            numbers.add(n);

        }
    }

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
        g2.setFont(new Font("MV Boli", Font.BOLD, 10));
        FontMetrics metrics = getFontMetrics(g.getFont());

//        for (int i = 0; i < col; i++) {
//            g2.drawLine(i * tileSize, 0, i * tileSize, panelHeight);
//        }
//        for (int i = 0; i < row; i++) {
//            g2.drawLine(0, i * tileSize, panelWidth, i * tileSize);
//        }
        if (update()) {
            g2.setColor(Color.green);
            g2.drawString("Game Over", 0, tileSize / 2);
            g2.dispose();
            return;
        }
        for (Num n : numbers) {
            g2.setColor(n.getColor());
            if (n.isFill()) {
                g2.fillRect(n.getX(), n.getY(), n.getWidth(), n.getHeight());
            } else {
                g2.drawRect(n.getX(), n.getY(), n.getWidth(), n.getHeight());
            }
            g2.drawString(n.getName(), n.getX() + (n.getWidth() - metrics.stringWidth(n.getName())) / 2, n.getY() + n.getHeight() / 2 + metrics.getHeight() / 4);

        }
        g2.setColor(Color.green);
        g2.drawString(mainFrame.FPScount, 0, tileSize / 2);

        g2.drawString("" + mainFrame.delta, tileSize, tileSize / 2);
        
        g2.drawString("" + currentID, panelWidth/2, tileSize / 2);
        
        g2.dispose();
        

    }

    public boolean update() {
        for (Num n : numbers) {
            if (!n.isFill()) {
                return false;
            }
        }
        return true;
    }

    public void checkCollision(int x, int y) {
        for (Num n : numbers) {
            if (n.getHitbox().contains(x, y)) {
                if (n.getId() == currentID) {
                    n.setFill(true);
                    currentID++;
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
