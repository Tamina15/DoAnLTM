package src.GUI;

import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;

import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextField;

import javax.swing.border.MatteBorder;

import javax.swing.JPasswordField;

public class LoginForm extends JFrame {
	int posX, posY;
	int widthLeft;
	private JPanel contentPane;
	private JPanel pnBtnBar;
	private JLabel lblClose, lblMinimize, lbl_IconBack;
	JPanel pnHome;
	private JPanel pnInput;
	private JLabel lblNewLabel, lblMtKhu;
	private JPasswordField txt_pass;
	private JPanel pnBtn;
	private JLabel lbl_IconDeliveryman;
	private JButton btnLogin, btnBack;
	private JTextField txt_username;

	public LoginForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 100, 633, 350);
		setUndecorated(true);
		setLocationRelativeTo(null);
		initComponens(); // initComponens
//		addEvents();
	}

	public void initComponens() {
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		lbl_IconBack = new JLabel("");
		lbl_IconBack.setBounds(10, 10, 40, 30);
		lbl_IconBack.setBackground(new Color(255, 255, 255));
		lbl_IconBack.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IconBack.setIcon(new ImageIcon(LoginForm.class.getResource("/images/iconback.png")));
		contentPane.add(lbl_IconBack);
		
		lblMtKhu = new JLabel("M\u1EADt kh\u1EA9u");
		lblMtKhu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblMtKhu.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblMtKhu.setForeground(Color.WHITE);
		lblMtKhu.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		lblMtKhu.setBounds(210, 107, 206, 25);
		contentPane.add(lblMtKhu);
		
		lblNewLabel = new JLabel("Email");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		lblNewLabel.setBounds(210, 38, 100, 25);
		contentPane.add(lblNewLabel);
		
		lbl_IconDeliveryman = new JLabel("");
		lbl_IconDeliveryman.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IconDeliveryman.setIcon(new ImageIcon(LoginForm.class.getResource("/images/background_home.png")));
		lbl_IconDeliveryman.setBounds(0, 0, 633, 350);
		contentPane.add(lbl_IconDeliveryman);
		widthLeft = (int) contentPane.getPreferredSize().getWidth();

		JPanel pnContent = new JPanel();
		pnContent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		pnContent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnContent.setBackground(new Color(255, 255, 255));
		pnContent.setBounds(0, 0, 634, 350);//308, 0, 326, 30 0, 0, 308, 350
		contentPane.add(pnContent);
		pnContent.setLayout(null);

		txt_username = new JTextField();
		txt_username.setOpaque(false);
		txt_username.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txt_username.setDisabledTextColor(new Color(0, 139, 139));
		txt_username.setCaretColor(new Color(255, 255, 255));
		txt_username.setBounds(210, 63, 206, 30);
		
		pnContent.add(txt_username);
		txt_username.setColumns(10);

		txt_pass = new JPasswordField();
		txt_pass.setOpaque(false);
		txt_pass.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(255, 255, 255)));
		txt_pass.setCaretColor(new Color(255, 255, 255));
		txt_pass.setBounds(210, 132, 206, 30);
		pnContent.add(txt_pass);                                 

		btnLogin = new JButton("Đăng nhập");
		btnLogin.setForeground(new Color(255, 255, 255));

		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setBackground(new Color(51, 51, 102));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setBounds(210, 192, 206, 40);
		pnContent.add(btnLogin);
		

		


		

	}

	// Event
//	public void addEvents() {
//		lblClose.addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.exit(0);
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				lblClose.setBackground(new Color(47, 79, 79));
//				lblClose.setOpaque(true);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
////				lblClose.setOpaque(false);
//				lblClose.setBackground(new Color(0, 128, 128));
//			}
//		});
//
//		lblMinimize.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				setState(Frame.ICONIFIED);
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				lblMinimize.setBackground(new Color(47, 79, 79));
//				lblMinimize.setOpaque(true);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
////				lblMini.setOpaque(false);
//				lblMinimize.setBackground(new Color(0, 128, 128));
//			}
//		});
//
//		pnBtnBar.addMouseMotionListener(new MouseMotionAdapter() {
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				int positionX = e.getXOnScreen();
//				int positionY = e.getYOnScreen();
//				moveWindow(positionX - posX - widthLeft - 100, positionY - posY);
//			}
//		});
//
//		// Login
//		
//		btnLogin.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				String username = txt_username.getText();
//				String password = String.valueOf(txt_pass.getPassword());
//				loadHomeForm();
//			}
//		});
//	}

	public void moveWindow(int positionX, int positionY) {
		this.setLocation(positionX, positionY);
	}

	public void closeThisWindow() {
		this.dispose();
	}
	
	
	 public void loadHomeForm() {
		 HomeSelecttion homeForm = new HomeSelecttion();
		 homeForm.setVisible(true);
		 closeThisWindow();
	 }
}
