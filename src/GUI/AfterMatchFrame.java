/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static Utils.Constant.PANEL_HEIGHT;
import static Utils.Constant.PANEL_WIDTH;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Tran Minh Nhat
 */
public class AfterMatchFrame extends JFrame{
    AfterMatchPanel afterMatch;
    public AfterMatchFrame(){
        afterMatch = new AfterMatchPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(afterMatch);
        //this.setBackground(Color.yellow);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
}
class AfterMatchPanel extends JPanel{
    public AfterMatchPanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//        this.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        this.setBackground(Color.red);
    }
}
