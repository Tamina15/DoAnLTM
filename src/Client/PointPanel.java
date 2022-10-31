/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Utils.Constant.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Tran Minh Nhat
 */
public class PointPanel extends JPanel {

    int height = TILE_SIZE;

    public PointPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, height));
        this.setBackground(new Color(0, 70, 80));
        this.setVisible(true);
    }
protected void innit(){
    ImageIcon i = new ImageIcon();
}
}
