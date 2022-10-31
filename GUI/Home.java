package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import GUI.LoginForm;

public class Home extends JFrame {

	private JPanel contentPane;
	private JLabel lbl_ImageBackground;
	private JButton btnLogin,btnRegister, btnQuit;
	int widthLeft;
	private JPanel pnBtn1,pnBtn2, pnBtn3;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 100, 633, 350);
		setUndecorated(true);
		setLocationRelativeTo(null);
		initComponens(); // initComponens
		addEvents();
	}

	public void initComponens() {
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		lbl_ImageBackground = new JLabel("");
		lbl_ImageBackground.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_ImageBackground.setIcon(new ImageIcon(LoginForm.class.getResource("/images/background_home.png")));
		lbl_ImageBackground.setBounds(0, 0, 633, 350);
		contentPane.add(lbl_ImageBackground);
		widthLeft = (int) contentPane.getPreferredSize().getWidth();;
		
		JPanel pnContent = new JPanel();
		pnContent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		pnContent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnContent.setBackground(new Color(0, 139, 139));
		pnContent.setBounds(0, 0, 634, 350);//308, 0, 326, 30 0, 0, 308, 350
		contentPane.add(pnContent);
		pnContent.setLayout(null);
		pnBtn1 = new JPanel();
		pnBtn1.setOpaque(false);
		pnBtn1.setBounds(200, 75, 234, 40);
		pnContent.add(pnBtn1);
		pnBtn1.setLayout(null);

		btnLogin = new JButton("\u0110\u0103ng nh\u1EADp");
		btnLogin.setForeground(new Color(255, 255, 255));

		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setBackground(new Color(51, 51, 102));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setBounds(0, 0, 234, 40);
		pnBtn1.add(btnLogin);
		
		pnBtn2 = new JPanel();
		pnBtn2.setOpaque(false);
		pnBtn2.setBounds(200, 130, 234, 40);
		pnContent.add(pnBtn2);
		pnBtn2.setLayout(null);
		pnContent.add(pnBtn2);
		
		btnRegister = new JButton("Đăng ký");
		btnRegister.setForeground(new Color(255, 255, 255));
		btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegister.setBackground(new Color(51, 51, 102));
		btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnRegister.setBounds(0, 0, 234, 40);
		pnBtn2.add(btnRegister);
		
		pnBtn3 = new JPanel();
		pnBtn3.setOpaque(false);
		pnBtn3.setBounds(200, 193, 234, 40);
		pnContent.add(pnBtn3);
		pnBtn3.setLayout(null);
		pnContent.add(pnBtn3);
		
		btnQuit = new JButton("Thoát");
		btnQuit.setForeground(new Color(255, 255, 255));
		btnQuit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQuit.setBackground(new Color(51, 51, 102));
		btnQuit.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnQuit.setBounds(0, 0, 234, 40);
		pnBtn3.add(btnQuit);
		
	}
	
	public void addEvents() {
		
		// Login
		
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadLoginForm();
			}		
		});
		
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				loadHomeForm();
			}		
		});
		
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}	
		});
	}
	
	public void closeThisWindow() {
		this.dispose();
	}
	public void LoadRegisterForm() {
//		ReisterForm register = new RegisterForm() {
//			register.setVisible(true);
//			closeThisWindow();
//		}
	}
	
	
	public void loadLoginForm() {
		 LoginForm login = new LoginForm();
		 login.setVisible(true);
		 closeThisWindow();
	 }
}
