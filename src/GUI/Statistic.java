package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JProgressBar;
import javax.swing.JMenu;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Cursor;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import javax.swing.ImageIcon;

public class Statistic extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_checkSTT_date;
	private JTextField tf_checkWin;
	private JTextField txt_date;
	private JTable table;
	private JTextField tf_checkScore;
	private JTextField tf_checkSTT;
	private JTable table_win;
	private JTextField tf_searchScore;
	private JTextField textField;
	private JTable table_1;
	private JTable table_checkSTT;
	private JTextField tf_title;
        private JLabel lbl_ImageBackground;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Statistic frame = new Statistic();
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
	public Statistic() {
		setTitle("Thống kê \r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1201, 713);
                //setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_xephang = new JPanel();
		panel_xephang.setBounds(10, 54, 368, 612);
		panel_xephang.setFont(UIManager.getFont("TitledBorder.font"));
		panel_xephang.setBackground(SystemColor.control);
		
//		 private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
//			    ((javax.swing.border.TitledBorder) jPanel1.getBorder()).
//
//			    jPanel1.repaint();
//			}
//		
		panel_xephang.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel_xephang);
		panel_xephang.setLayout(null);
		
		tf_checkSTT_date = new JTextField();
		tf_checkSTT_date.setBounds(10, 10, 348, 34);
		tf_checkSTT_date.setBackground(Color.LIGHT_GRAY);
		tf_checkSTT_date.setEditable(false);
		panel_xephang.add(tf_checkSTT_date);
		tf_checkSTT_date.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tf_checkSTT_date.setHorizontalAlignment(SwingConstants.CENTER);
		tf_checkSTT_date.setText("Xếp hạng");
		tf_checkSTT_date.setColumns(10);
		
		JDateChooser date_start = new JDateChooser();
		date_start.setBounds(10, 54, 73, 19);
		date_start.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_xephang.add(date_start);
		
		JDateChooser date_and = new JDateChooser();
		date_and.setBounds(285, 54, 73, 19);
		panel_xephang.add(date_and);
		
		txt_date = new JTextField();
		txt_date.setBounds(148, 54, 89, 19);
		txt_date.setBackground(Color.LIGHT_GRAY);
		txt_date.setEditable(false);
		txt_date.setText("đến");
		txt_date.setHorizontalAlignment(SwingConstants.CENTER);
		txt_date.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txt_date.setColumns(10);
		panel_xephang.add(txt_date);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 348, 519);
		panel_xephang.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"STT","Score", "Name", "ID" 
			}
		));
		
		JPanel panel_tracuudiem = new JPanel();
		panel_tracuudiem.setBounds(901, 54, 276, 612);
		panel_tracuudiem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel_tracuudiem);
		panel_tracuudiem.setLayout(null);
		
		tf_checkSTT = new JTextField();
		tf_checkSTT.setText("Tra cứu xếp hạng");
		tf_checkSTT.setHorizontalAlignment(SwingConstants.CENTER);
		tf_checkSTT.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tf_checkSTT.setEditable(false);
		tf_checkSTT.setColumns(10);
		tf_checkSTT.setBackground(Color.LIGHT_GRAY);
		tf_checkSTT.setBounds(10, 10, 256, 30);
		panel_tracuudiem.add(tf_checkSTT);
		
		JPanel panel_CheckSTT = new JPanel();
		panel_CheckSTT.setLayout(null);
		panel_CheckSTT.setBounds(10, 50, 256, 30);
		panel_tracuudiem.add(panel_CheckSTT);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(0, 0, 181, 30);
		panel_CheckSTT.add(textField);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(Statistic.class.getResource("/images/search-icon.png")));
		btnNewButton_1.setBounds(191, 0, 66, 30);
		panel_CheckSTT.add(btnNewButton_1);
		
		JScrollPane scrollPane_checkSTT = new JScrollPane();
		scrollPane_checkSTT.setBounds(10, 90, 256, 512);
		panel_tracuudiem.add(scrollPane_checkSTT);
		
		table_checkSTT = new JTable();
		scrollPane_checkSTT.setViewportView(table_checkSTT);
		table_checkSTT.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"STT", "Name", "ID"
			}
		));
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(388, 54, 228, 612);
		contentPane.add(panel);
		panel.setLayout(null);
		
		tf_checkWin = new JTextField();
		tf_checkWin.setBackground(Color.LIGHT_GRAY);
		tf_checkWin.setEditable(false);
		tf_checkWin.setBounds(10, 10, 208, 30);
		panel.add(tf_checkWin);
		tf_checkWin.setText("Tỷ lệ thắng");
		tf_checkWin.setHorizontalAlignment(SwingConstants.CENTER);
		tf_checkWin.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tf_checkWin.setColumns(10);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(78, 77, 140, 11);
		panel.add(progressBar);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(78, 98, 140, 11);
		panel.add(progressBar_1);
		
		JProgressBar progressBar_1_1 = new JProgressBar();
		progressBar_1_1.setBounds(78, 119, 140, 11);
		panel.add(progressBar_1_1);
		
		JProgressBar progressBar_1_1_1 = new JProgressBar();
		progressBar_1_1_1.setBounds(78, 140, 140, 11);
		panel.add(progressBar_1_1_1);
		
		JScrollPane scrollPane_win = new JScrollPane();
		scrollPane_win.setBorder(null);
		scrollPane_win.setBounds(10, 50, 46, 552);
		panel.add(scrollPane_win);
		
		table_win = new JTable();
		scrollPane_win.setViewportView(table_win);
		table_win.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name"
			}
		));
		
		JButton btn_back = new JButton("");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn_back.setIcon(new ImageIcon(Statistic.class.getResource("/images/iconback.png")));
		btn_back.setFocusable(false);
		btn_back.setSelected(true);
		btn_back.setBounds(10, 14, 93, 36);
		contentPane.add(btn_back);
		
		JPanel panel_tracuudiem_1 = new JPanel();
		panel_tracuudiem_1.setBounds(626, 54, 265, 612);
		contentPane.add(panel_tracuudiem_1);
		panel_tracuudiem_1.setLayout(null);
		panel_tracuudiem_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		tf_checkScore = new JTextField();
		tf_checkScore.setText("Tra cứu điểm");
		tf_checkScore.setHorizontalAlignment(SwingConstants.CENTER);
		tf_checkScore.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tf_checkScore.setEditable(false);
		tf_checkScore.setColumns(10);
		tf_checkScore.setBackground(Color.LIGHT_GRAY);
		tf_checkScore.setBounds(10, 10, 245, 30);
		panel_tracuudiem_1.add(tf_checkScore);
		
		JPanel panel_CheckScore = new JPanel();
		panel_CheckScore.setBounds(10, 50, 245, 30);
		panel_tracuudiem_1.add(panel_CheckScore);
		panel_CheckScore.setLayout(null);
		
		tf_searchScore = new JTextField();
		tf_searchScore.setBounds(0, 0, 173, 30);
		panel_CheckScore.add(tf_searchScore);
		tf_searchScore.setColumns(10);
		
		JButton btn_searchWin = new JButton("");
		btn_searchWin.setIcon(new ImageIcon(Statistic.class.getResource("/images/search-icon.png")));
		btn_searchWin.setBounds(183, 0, 62, 30);
		panel_CheckScore.add(btn_searchWin);
		
		JScrollPane scrollPane_CheckScore = new JScrollPane();
		scrollPane_CheckScore.setBounds(10, 90, 245, 512);
		panel_tracuudiem_1.add(scrollPane_CheckScore);
		
		table_1 = new JTable();
		scrollPane_CheckScore.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Total Score", "Name", "ID"
			}
		));
		
		tf_title = new JTextField();
		tf_title.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		tf_title.setText("Thống kê");
		tf_title.setHorizontalAlignment(SwingConstants.CENTER);
		tf_title.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tf_title.setEditable(false);
		tf_title.setColumns(10);
		tf_title.setBackground(Color.LIGHT_GRAY);
		tf_title.setBounds(191, 14, 811, 30);
		contentPane.add(tf_title);
                
	}
}
