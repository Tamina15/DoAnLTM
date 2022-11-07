package src.GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;

public class HomeSelecttion extends JFrame {
	
	private JPanel contentPane;
	private JLabel lbl_ImageBackground, lbl_IconInfor, lbl_IconGame, lbl_IconEdit, lbl_IconTop;
	private JButton btnRegister, btnQuit;
	int widthLeft;
	private JPanel pnBtn1,pnBtn2, pnBtn3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	public HomeSelecttion() {
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
		
		//icon vào game
		lbl_IconInfor = new JLabel("");
		lbl_IconInfor.setBackground(new Color(255, 255, 255));
		lbl_IconInfor.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IconInfor.setIcon(new ImageIcon(LoginForm.class.getResource("/images/iconplay.png")));
		lbl_IconInfor.setBounds(212, 104, 250, 125);
		contentPane.add(lbl_IconInfor);
		
		//icon đăng xuất
		
		lbl_IconGame = new JLabel("");
		lbl_IconGame.setBackground(new Color(255, 255, 255));
		lbl_IconGame.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IconGame.setIcon(new ImageIcon(LoginForm.class.getResource("/images/logout.png")));
		lbl_IconGame.setBounds(594, 0, 29, 43);
		contentPane.add(lbl_IconGame);
		
		//icon chỉnh sửa thông tin cá nhân
		
		lbl_IconEdit = new JLabel("");
		lbl_IconEdit.setBackground(new Color(255, 255, 255));
		lbl_IconEdit.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IconEdit.setIcon(new ImageIcon(LoginForm.class.getResource("/images/edit.png")));
		lbl_IconEdit.setBounds(20, 233, 37, 43);
		contentPane.add(lbl_IconEdit);
		
		//icon xếp hạng
		
		lbl_IconTop = new JLabel("");
		lbl_IconTop.setBounds(20, 287, 49, 37);
		lbl_IconTop.setBackground(new Color(255, 255, 255));
		lbl_IconTop.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IconTop.setIcon(new ImageIcon(LoginForm.class.getResource("/images/analysis.png")));
		contentPane.add(lbl_IconTop);
		
		
		//hình nền game
		lbl_ImageBackground = new JLabel("");
		lbl_ImageBackground.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_ImageBackground.setIcon(new ImageIcon(LoginForm.class.getResource("/images/background_home.png")));
		lbl_ImageBackground.setBounds(0, 0, 633, 350);
		contentPane.add(lbl_ImageBackground);
		


		  
		
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeSelecttion frame = new HomeSelecttion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}