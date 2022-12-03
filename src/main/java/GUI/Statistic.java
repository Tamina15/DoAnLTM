package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.ComponentOrientation;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Statistic extends JFrame {

    /**
     *
     */
    private Client client;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tf_checkSTT_date;
    private JTextField tf_checkWin;
    private JTable tbl_xephang;
    private JTextField tf_checkSTT;
    private JButton btnSearchRank;
    private JTable tbl_winPercent;
    private JPanel panelWinPercent;
    private JTextField tf_searchRank;
    private JTable tbl_checkSTT;
    private JTextField tf_title;
    private Vector<JProgressBar> progressBar = new Vector<JProgressBar>();
    private JTextField tf_tyleThang_Thua;

    public Statistic(Client client) throws IOException, ClassNotFoundException {
        setBackground(SystemColor.text);
        this.client = client;
        initComponents();
        addEvents();
        loadRankingTable();
        loadWinPercentTable();
        loadCheckPointTable();
        //loadCheckSTTTable();
    }

    /**
     * Create the frame.
     */
    private void initComponents() {
        setTitle("Leaderboard \r\n");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1201, 713);
        //setUndecorated(true);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 205));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_xephang = new JPanel();
        panel_xephang.setBounds(10, 54, 420, 612);
        panel_xephang.setFont(UIManager.getFont("TitledBorder.font"));
        panel_xephang.setBackground(SystemColor.control);
        panel_xephang.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPane.add(panel_xephang);
        panel_xephang.setLayout(null);

        tf_checkSTT_date = new JTextField();
        tf_checkSTT_date.setBounds(10, 10, 400, 34);
        tf_checkSTT_date.setBackground(SystemColor.activeCaptionBorder);
        tf_checkSTT_date.setEditable(false);
        panel_xephang.add(tf_checkSTT_date);
        tf_checkSTT_date.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tf_checkSTT_date.setHorizontalAlignment(SwingConstants.CENTER);
        tf_checkSTT_date.setText("Ranking");
        tf_checkSTT_date.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 83, 400, 519);
        panel_xephang.add(scrollPane);

        tbl_xephang = new JTable();
        scrollPane.setViewportView(tbl_xephang);

        JPanel panel_tracuudiem = new JPanel();
        panel_tracuudiem.setBounds(818, 54, 359, 612);
        panel_tracuudiem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPane.add(panel_tracuudiem);
        panel_tracuudiem.setLayout(null);

        tf_checkSTT = new JTextField();
        tf_checkSTT.setText("Search rank");
        tf_checkSTT.setHorizontalAlignment(SwingConstants.CENTER);
        tf_checkSTT.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tf_checkSTT.setEditable(false);
        tf_checkSTT.setColumns(10);
        tf_checkSTT.setBackground(SystemColor.activeCaptionBorder);
        tf_checkSTT.setBounds(10, 10, 339, 30);
        panel_tracuudiem.add(tf_checkSTT);

        JPanel panel_CheckSTT = new JPanel();
        panel_CheckSTT.setLayout(null);
        panel_CheckSTT.setBounds(10, 50, 339, 30);
        panel_tracuudiem.add(panel_CheckSTT);

        tf_searchRank = new JTextField();
        tf_searchRank.setColumns(10);
        tf_searchRank.setBounds(0, 0, 275, 30);
        panel_CheckSTT.add(tf_searchRank);

        btnSearchRank = new JButton("");
        btnSearchRank.setBounds(273, 0, 66, 30);
        panel_CheckSTT.add(btnSearchRank);
        btnSearchRank.setIcon(new ImageIcon(Statistic.class.getResource("/images/search-icon.png")));

        JScrollPane scrollPane_checkSTT = new JScrollPane();
        scrollPane_checkSTT.setBounds(10, 90, 339, 512);
        panel_tracuudiem.add(scrollPane_checkSTT);

        tbl_checkSTT = new JTable();
        scrollPane_checkSTT.setViewportView(tbl_checkSTT);

        panelWinPercent = new JPanel();
        panelWinPercent.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelWinPercent.setBounds(440, 54, 368, 612);
        contentPane.add(panelWinPercent);
        panelWinPercent.setLayout(null);

        tf_checkWin = new JTextField();
        tf_checkWin.setBackground(SystemColor.activeCaptionBorder);
        tf_checkWin.setEditable(false);
        tf_checkWin.setBounds(10, 10, 350, 30);
        panelWinPercent.add(tf_checkWin);
        tf_checkWin.setText("Win percent");
        tf_checkWin.setHorizontalAlignment(SwingConstants.CENTER);
        tf_checkWin.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tf_checkWin.setColumns(10);

        JScrollPane scrollPane_win = new JScrollPane();
        scrollPane_win.setBorder(null);
        scrollPane_win.setBounds(10, 50, 90, 552);
        panelWinPercent.add(scrollPane_win);

        tbl_winPercent = new JTable();
        scrollPane_win.setViewportView(tbl_winPercent);

        tf_tyleThang_Thua = new JTextField();
        tf_tyleThang_Thua.setBackground(SystemColor.activeCaptionBorder);
        tf_tyleThang_Thua.setHorizontalAlignment(SwingConstants.CENTER);
        tf_tyleThang_Thua.setFont(new Font("Times New Roman", Font.BOLD, 13));
        tf_tyleThang_Thua.setText("Win percentage");
        tf_tyleThang_Thua.setBounds(100, 50, 240, 19);
        panelWinPercent.add(tf_tyleThang_Thua);
        tf_tyleThang_Thua.setColumns(10);

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

        tf_title = new JTextField();
        tf_title.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tf_title.setText("Leaderboard");
        tf_title.setHorizontalAlignment(SwingConstants.CENTER);
        tf_title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tf_title.setEditable(false);
        tf_title.setColumns(10);
        tf_title.setBackground(SystemColor.activeCaptionBorder);
        tf_title.setBounds(191, 14, 811, 30);
        contentPane.add(tf_title);

    }

    private void addEvents() {
        btnSearchRank.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    loadCheckSTTTable();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Statistic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void loadRankingTable() throws IOException, ClassNotFoundException {
        DefaultTableModel dfm = new DefaultTableModel();
        String[] header = {"Rank", "Point", "Name", "Total match"};
        dfm.setColumnIdentifiers(header);
        Vector<Vector<String>> t = new Vector<Vector<String>>();
        t = client.getRankingTable();
        for (int k = 0; k < t.size(); k++) {
            dfm.addRow(t.get(k));
        }
        tbl_xephang.setModel(dfm);
    }

    private void loadWinPercentTable() throws IOException, ClassNotFoundException {
        DefaultTableModel dfm = new DefaultTableModel();
        int percent = 0;
        int y = 69;
        String[] header = {"Name"};
        dfm.setColumnIdentifiers(header);
        Vector<Vector<String>> t = new Vector<Vector<String>>();
        t = client.getPercentRankingTable();
        for (int k = 0; k < t.size(); k++) {
            Vector<String> b = new Vector<String>();
            percent = Integer.parseInt(t.get(k).get(0));
            b.add(t.get(k).get(1));
            dfm.addRow(b);
            JProgressBar proBar = new JProgressBar();
            proBar.setBounds(100, y, 240, 15);
            proBar.setValue(percent);
            proBar.setStringPainted(true);
            progressBar.add(proBar);
            y = y + 17;
            panelWinPercent.add(progressBar.get(k));
        }
        tbl_winPercent.setModel(dfm);
        //xử lí lấy dữ liệu từ server
    }

    private void loadCheckSTTTable() throws IOException, ClassNotFoundException {
        String value = tf_searchRank.getText();
        if (value.isBlank()) {
            return;
        }
        DefaultTableModel dfm = new DefaultTableModel();
        String[] header = {"Rank", "Point", "Name", "Total match"};
        dfm.setColumnIdentifiers(header);
        Vector<Vector<String>> t = new Vector<Vector<String>>();
        t = client.getSearchRank(value);
        for (int k = 0; k < t.size(); k++) {
            dfm.addRow(t.get(k));
        }
        tf_searchRank.setText("");
        tbl_checkSTT.setModel(dfm);
        //xử lí lấy dữ liệu từ server
    }

    private void loadCheckPointTable() {
        DefaultTableModel dfm = new DefaultTableModel();
        String[] header = {"Point", "Name", "ID",};
        dfm.setColumnIdentifiers(header);
        //xử lí lấy dữ liệu từ server
    }
}
