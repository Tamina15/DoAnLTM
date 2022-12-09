package GUI;

import static Utils.Class.*;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;

public class OTPForm extends JFrame {

    private Client cl;
    private JPanel contentPane;
    private JTextField txtOTP;

    Timer timer;
    int second = 0;
    int minute = 10;
    String ddSecond, ddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    private JLabel lblTime;

//    public void countdownTimer() {
//        timer = new Timer(1000, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                second--;
//                ddSecond = dFormat.format(second);
//                ddMinute = dFormat.format(minute);
//                lblTime.setText(ddMinute + ":" + ddSecond);
//
//                if (second == -1) {
//                    second = 59;
//                    minute--;
//                    ddSecond = dFormat.format(second);
//                    ddMinute = dFormat.format(minute);
//                    lblTime.setText(ddMinute + ":" + ddSecond);
//                }
//
//                if (minute == 0 && second == 0) {
//                    timer.stop();
//                }
//            }
//        });
//    }

    public OTPForm(Client cl) {
        this.cl = cl;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 100, 300, 200);
        setUndecorated(true);
        setLocationRelativeTo(null);
        initComponents(cl);
    }

    private void initComponents(Client cl) {
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Vui lòng nhập mã OTP từ gmail bạn vừa đăng ký");
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setBackground(new Color(255, 153, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(10, 24, 262, 44);
        contentPane.add(lblNewLabel);

        txtOTP = new JTextField();
        txtOTP.setBounds(97, 101, 96, 20);
        contentPane.add(txtOTP);
        txtOTP.setColumns(10);

        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBounds(201, 149, 89, 23);
        btnXacNhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cl.send("OTP",String.valueOf(txtOTP.getText()));
            }
        });
        contentPane.add(btnXacNhan);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setBounds(10, 149, 89, 23);
        btnXacNhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CloseThisFrame();
            }
        });
        contentPane.add(btnHuy);
    }

    public void CloseThisFrame() {
        this.setVisible(false);
        this.setEnabled(false);
        this.dispose();
        REGISTER_FORM.setVisible(true);
    }

    public void WrongOTP() {
        JOptionPane.showMessageDialog(contentPane, "Wrong OTP! Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void TimeoutOTP() {
        JOptionPane.showMessageDialog(contentPane, "OTP code was expired! Please try again!", "Error",JOptionPane.ERROR_MESSAGE);
        CloseThisFrame();
    }
    
    public void OTPhopLe() {
    	LOGIN_FORM = new LoginForm(cl);
    	LOGIN_FORM.setVisible(true);
    	CloseThisFrame();
    }
}
