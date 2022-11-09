/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Server;
import static Utils.Constant.PANEL_WIDTH;
import static Utils.Constant.TILE_SIZE;
import static Utils.Constant.PAUSE;
import static Utils.Constant.REMAINING_TIME;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tran Minh Nhat
 */
public class PointPanel extends JPanel {
    
    static int height = TILE_SIZE;
    private JButton pause, sound;
    
    private JLabel number, time, player1, player2;
    ImageIcon ic;
    Color background = new Color(0, 70, 80);
    
    public PointPanel() {
        //this.setBounds(0, 0, PANEL_WIDTH, height);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, height));
        this.setBackground(background);
        this.setLayout(new GridLayout(1, 6));
        init();
        this.setVisible(true);
    }
    
    protected final void init() {
        ic = new ImageIcon();
        pause = new JButton();
        sound = new JButton();
        number = new JLabel();
        time = new JLabel();
        player1 = new JLabel();
        player2 = new JLabel();
        Font font = new Font("Time New Roman", Font.PLAIN, 30);
        //this.setLayout(new FlowLayout(1, 50, 10));
        
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(PointPanel.class.getResource("/images/pause.png")).getImage());
        pause.setIcon(imageIcon);
        pause.setBackground(background);
        pause.setBorderPainted(false);
        pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PAUSE = !PAUSE;
            }
        });
        
        imageIcon = new ImageIcon(new ImageIcon(PointPanel.class.getResource("/images/volume.png")).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        sound.setIcon(imageIcon);
        sound.setBackground(background);
        sound.setBorderPainted(false);
        
        imageIcon = new ImageIcon(new ImageIcon(PointPanel.class.getResource("/images/timer-icon.png")).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        time.setIcon(imageIcon);
        time.setFont(font);
        time.setForeground(Color.WHITE);
        
        number.setForeground(Color.WHITE);
        number.setFont(font);
        number.setBackground(background);
        
        player1.setForeground(Color.GREEN);
        player1.setText("Player 1: ");
        player2.setText("Player 2: ");
        
        this.add(pause);
        this.add(sound);
        this.add(player1);
        this.add(number);
        this.add(player2);
        this.add(time);
        
    }
    
    public void update() {
        number.setText("" + (Server.currentNumber + 1));
        time.setText("" + REMAINING_TIME);
    }
    
}
