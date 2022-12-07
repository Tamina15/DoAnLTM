package GUI;

import static Utils.Class.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class OTPForm extends JFrame {

	public Client client;
	private JPanel contentPane;
	int posX, posY;
	int widthLeft;
	private JTextField txtOTP;
	private JButton btnSubmit;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public String mailregex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	private JButton btnHuy;

	public OTPForm(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 100, 633, 350);
		setUndecorated(true);
		setLocationRelativeTo(null);
		initComponens(client); // initComponens
//		addEvents();
	}

	private void initComponens(Client client) {
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblSignUp = new JLabel("Mã OTP đã được gửi vào email của bạn");
		lblSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblSignUp.setForeground(new Color(0, 0, 0));
		lblSignUp.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblSignUp.setBounds(129, 10, 350, 30);
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSignUp);

		txtOTP = new JTextField();
		txtOTP.setBackground(new Color(0, 0, 0));
		txtOTP.setForeground(new Color(0, 0, 0));
		txtOTP.setOpaque(false);
		txtOTP.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtOTP.setDisabledTextColor(new Color(0, 139, 139));
		txtOTP.setCaretColor(new Color(0, 0, 0));
		txtOTP.setBounds(223, 112, 206, 30);
		contentPane.add(txtOTP);
		txtOTP.setColumns(10);

		UtilDateModel modeStart = new UtilDateModel();
		JDatePanelImpl datePanelStart = new JDatePanelImpl(modeStart);

		btnSubmit = new JButton("Xác nhận");
		btnSubmit.setForeground(new Color(255, 255, 255));
		btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSubmit.setBackground(new Color(51, 51, 102));
		btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnSubmit.setBounds(349, 278, 150, 40);
		btnSubmit.addActionListener((ActionEvent e) -> {
			btnSubmitActionPerform(e);
		});
		contentPane.add(btnSubmit);

		btnHuy = new JButton("Hủy");
		btnHuy.setBounds(113, 289, 89, 23);
		btnHuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeThisWindow();
			}
		});
		contentPane.add(btnHuy);
		widthLeft = (int) contentPane.getPreferredSize().getWidth();
	}

	public void btnSubmitActionPerform(ActionEvent e) {
		try {
			closeThisWindow();
			client.sendOTP(String.valueOf(txtOTP.getText()));

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(contentPane, "Somthing wrong", "Error Message", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void closeThisWindow() {
		this.dispose();
	}

	public void OTPHopLe() {
		LoginForm lg = new LoginForm(client);
		lg.setVisible(true);
	}

}
