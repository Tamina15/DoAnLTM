package multiplechoiceserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.smtp.SMTPTransport;
import BLL.*;
import DTO.Users;
import java.io.ObjectOutputStream;

public class Worker implements Runnable {

    /* Info for this Thread */
    /////////////////////////
    // Id of this thread, use by Server only
    int id;

    // Socket for this thread
    private Socket socket = null;

    // To read incoming message
    private BufferedReader in = null;

    // Variable for imcoming message
    private String input = null;

    // To write message for client
    private BufferedWriter out = null;

    // Name of this thread
    private String name;

    // Score of this Thread, to be written to database
    private int score;
    /* End Info for this Thread */
    /////////////////////////

    /* Info for the opponent Thread */
    /////////////////////////
    // A pair governs the game, each Server Thread must belong to one Pair.
    // A game cannot start if the pair is NOT fully completed
    private Pair pair = null;

    // ServerThread belong to the opponent client
    // private ServerThread opponent = null;
    /* End Info for opponent Thread */
    /////////////////////////
    public String myName;
    SecretKey secKey;
    int status = -1;

    private UsersBLL usersBLL;

    ObjectOutput objectOutput;
//    ObjectInputStream objectInput;
    // A Flag to stop this thread
    boolean flag = true;

    public Worker(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        // objectInput = new ObjectInputStream(socket.getInputStream());
    }

    public Worker(int id, Socket s, BufferedReader in, BufferedWriter out) throws IOException {
        this.socket = s;
        this.id = id;
        name = id + " : " + s.getInetAddress() + " : ";
        this.in = in;
        this.out = out;
        score = 0;
        usersBLL = new UsersBLL();
        this.myName = "anonymos";
        this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        System.out.println("Client number " + id + " accepted");
        try {
            while (flag && !socket.isClosed()) {
                input = (in.readLine());
                System.out.println("Server received: " + input + " from " + socket.toString() + " # Client " + myName);
                if (input.equals("exit")) {
                    send("bye");
                    Close();
                    return;
                }
                checkfunction(input);
            }
            System.out.println("Closed socket for client " + myName + " " + socket.toString());
            this.myName = "anonymos";
        } catch (IOException ex) {
            Close();
        } finally {
            Close();
        }
    }

//ki???m tra request client
    public String checkfunction(String line) throws IOException {
        String kq = "error";
        String[] mang;
        mang = line.split("\\]\\:\\$\\:\\[");
        for (int i = 0; i < mang.length; i++) {
            mang[i] = mang[i].replace("'", "''");
        }
        switch (mang[0]) {
            // client y??u c???u login
            case "login":
                CheckLogIn(mang[1], mang[2]);
                break;
            // client t???o t??i kho???n
            case "signup":
                CheckSignUp(mang[1], mang[2], mang[3], mang[4], mang[5]);
                break;
            case "OTP":
                CheckOTP(mang[1]);
                break;
            // T???i th??ng tin user
            case "UserInfo":
                UserInfo();
                break;
            // Thay ?????i th??ng tin
            case "editInfo":
                UpdateInfo(mang[1], mang[2], mang[3], mang[4]);
                break;
            // B???t ?????u game
            case "startgame":
                CreateGame();
                break;
            // Th???ng k??
            case "Statistic":
                loadRankingTable();
                break;
            case "PercentRank":
                loadWinPercentRanking();
                break;
            case "SearchRank":
                loadSearchRanking(mang[1]);
                break;
            // Tho??t
            case "LogOut":
                this.myName = "anonymos";

                break;

            // N???u client nh???n v??o m???t s??? trong tr?? ch??i
            case "click":
                if (pair.isGameStarted()) {
                    if (Integer.parseInt(mang[1]) == pair.currentNumber) {
                        pair.Click(this);
                    }
                }
                break;
            // N???u client d??ng che ??i???m s???
            case "UsePower":
                pair.UsePower(this);
                break;
            // N???u client nh???n thua, k???t th??c gi???a tr???n ?????u
            case "surrender":
                if (pair.isGameStarted()) {
                    pair.Surrender(this);
                }
                break;
            case "AbortGame":
                AbortGame();
                break;
            default:
                System.out.println("nothing to process");
                send("nothing");
                break;
        }
        return kq;
    }

//ki???m tra log in
    public void CheckLogIn(String email, String pass) {
        try {
            boolean checkuserlogin = false;
            for (Worker worker : Server.workers) {
                if (email.equals(worker.myName)) {
                    checkuserlogin = true;
                }
            }
            if (checkuserlogin) {
                send("loginfail]:$:[alreadylogin");
            } else {

                if (email.equals("admin@gmail.com") && pass.equals("admin")) {
                    this.myName = email;
                    send("loginsuccess]:$:[" + email);
                    return;
                }
                if (email.equals("user@gmail.com") && pass.equals("user")) {
                    this.myName = email;
                    send("loginsuccess]:$:[" + email);
                    return;
                }
                
                
                Connection con = ConnectionDB.Connect();
                String sql = "SELECT * FROM user where Email = '" + email + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                if (rs.getString("Email").equals(email) && rs.getString("Password").equals(pass)) {
                    this.myName = email;
                    send("loginsuccess]:$:[" + rs.getString("Email"));
                } else {
                    send("loginfail]:$:[wrong email or pass");
                }
            }
        } catch (SQLException | ClassCastException ex) {
            checksqlerror(ex.toString());
            send("loginfail]:$:[wrong username or pass");
        }
    }
    private long timeEnd;
    String OTP;
    String[] user = new String[5];
//ki???m tra ????ng k??

    public void CheckSignUp(String email, String pass, String name, String gender, String dob) {
        try {
            Connection con = ConnectionDB.Connect();
            String sql = "SELECT Name FROM user where Email = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                send("emailkhonghople");
            } else {
                user[0] = email;
                user[1] = pass;
                user[2] = name;
                user[3] = gender;
                user[4] = dob;
                send("emailhople");
                OTP = createOTP(6);
                SendEmail(email, "OTP l??: " + OTP);
                long start = System.currentTimeMillis();
                timeEnd = start + 10 * 60000;
            }
        } catch (SQLException ex) {
            send("signupfail");
        }
    }

    public void CheckOTP(String o) {
        if (System.currentTimeMillis() > timeEnd) {
            send("otphethan");
            return;
        }
        if (OTP.equals(o)) {
            try {
                String date = user[4].substring(0, 10);
                String sql1 = "insert into user values('" + user[2] + "','" + user[1] + "','" + user[0]
                        + "','" + 0 + "','" + user[3] + "','" + date + "')";
                Connection con = ConnectionDB.Connect();
                Statement st = con.createStatement();
                st.execute(sql1);
                send("signupsuccess");
            } catch (SQLException ex1) {
                checksqlerror(ex1.toString());
                send("signupfail");
            }
        } else {
            send("otpsai");
        }
    }
////g???i th??ng tin ng?????i ????ng nh???p

    public void UserInfo() {
        try {
            String sql = "select * from user where Email like '" + this.myName + "'";
            Connection con = ConnectionDB.Connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String kq = "UserInfo]:$:[" + rs.getString("Name") + "]:$:[" + rs.getString("Password") + "]:$:["
                    + rs.getString("Email") + "]:$:[" + rs.getString("Gender") + "]:$:[" + rs.getString("Birth");
            send(kq);
        } catch (SQLException ex) {
            checksqlerror(ex.toString());
            send("userinfoerror");
        }
    }

    // ch???nh s???a th??ng tin ng?????i ????ng nh???p
    public void UpdateInfo(String name, String gender, String Dob, String pass) {
        try {
            String date = Dob.substring(0, 10);
            System.out.println(date);
            String sql = "update user " + "set Name = N'" + name + "'" + ",Gender ='" + gender + "'" + ",Birth ='"
                    + date + "'" + ",Password ='" + pass + "'" + "where Email = '" + this.myName + "'";
            if (pass.equals("nochange")) {
                sql = "update user " + "set Name = N'" + name + "'" + ",Gender ='" + gender + "'" + ",Birth ='" + date
                        + "'" + "where Email = '" + this.myName + "'";
            }
            Connection con = ConnectionDB.Connect();
            Statement st = con.createStatement();
            st.execute(sql);
            send("updateinfosuccess");
        } catch (SQLException ex) {
            checksqlerror(ex.toString());
            send("updateinfoerror");
        }
    }
//t???o game	

    public void CreateGame() {
        Server.JoinAPair(this);
        if (pair.isFull()) {
            pair.StartGame();
        }
    }
// h???y game

    public void AbortGame() {
        pair.RemoveClient(this);
    }
//g???i dl cho client

    public void send(String data) {
        try {
            out.write(data + '\n');
            out.flush();
            // System.out.println("Server write: " + data + " to " + id);
        } catch (IOException ex) {
            System.err.println(ex);
            Close();
        }
    }

    public void sendObj(Vector<Vector<String>> result) {
        try {
//                synchronized (objectOutput) {
            objectOutput.writeObject(result);
            objectOutput.flush();
            // }
        } catch (IOException ex) {
            System.err.println(ex);
            Close();
        }

    }

//tr???n
    public void shuffle(ArrayList data) {
        Collections.shuffle(data);
    }

//ki???m tra v??i l???i sql
    public String checksqlerror(String ex) {
        System.out.println(ex);
        if (ex.contains("Violation of PRIMARY KEY constraint")) {
            return "key duplicate";
        }
        if (ex.contains("Invalid object name")) {
            return "unexist table";
        }
        if (ex.contains("The result set has no current row")) {
            return "table has no data";
        }
        return "something wrong";
    }

//ki???m tra l???i
    public String cheninput(String input) {
        input = input.replace("\\", "\\\\");
        input = input.replace("\"", "\\\"");
        return input;
    }

//t???ng s??? tr???n th???ng c???a ng?????i ch??i
    public int getSumWinerbyUser() throws SQLException {
        String sql = "select count(Winner) as sum from game where Winner like '" + this.myName + "'";
        Connection con = ConnectionDB.Connect();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int kq = 0;
        while (rs.next()) {
            kq = rs.getInt("sum");
        }
        return kq;
    }

//t??? l??? th???ng c???a t???ng ng?????i ch??i
    public float getPercentWinUser() throws SQLException {
        String kq = "";
        String sql = "select * from user where Email like'" + this.myName + "'";
        Connection con = ConnectionDB.Connect();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            kq = rs.getString("match");
        }
        return (float) (getSumWinerbyUser() / Integer.parseInt(kq));
    }

    // b???ng x???p h???ng
    public void loadRankingTable() {
        Vector<Users> list = usersBLL.getRanking();
        int i = 1;
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        for (Users usersDTO : list) {
            Vector<String> rowData = new Vector<String>();
            rowData.add(Integer.toString(i++));
            rowData.add(Integer.toString(usersBLL.getPoint(usersDTO.getEmail())));
            rowData.add(usersDTO.getName());
            // rowData.add(usersDTO.getEmail());
            rowData.add(Integer.toString(usersDTO.getTotalMatch()));
            result.add(rowData);
        }
        send("rankloaded");
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            System.out.println("Thread  interrupted.");
        }
        sendObj(result);

    }
    // t??? l??? th???ng

    public void loadWinPercentRanking() {
        Vector<Users> list = usersBLL.getWinPercentRanking();
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        for (Users usersDTO : list) {
            Vector<String> rowData = new Vector<String>();
            rowData.add(Integer.toString(usersBLL.getWinPercentByMail(usersDTO.getEmail())));
            rowData.add(usersDTO.getName());
            // dfm.addRow(rowData);
            result.add(rowData);
        }
        send("percentloaded");
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            System.out.println("Thread  interrupted.");
        }
        sendObj(result);

    }

    // tra c???u x???p h???ng
    public void loadSearchRanking(String key) {
        Vector<Users> list = usersBLL.searchRank(key);
        Vector<Vector<String>> result = new Vector<Vector<String>>();
        for (Users usersDTO : list) {
            Vector<String> rowData = new Vector<String>();
            rowData.add(Integer.toString(usersBLL.getRankByMail(usersDTO.getEmail())));
            rowData.add(Integer.toString(usersBLL.getPoint(usersDTO.getEmail())));
            rowData.add(usersDTO.getName());
            // rowData.add(usersDTO.getEmail());
            rowData.add(Integer.toString(usersDTO.getTotalMatch()));
            result.add(rowData);
        }
        if (result.isEmpty()) {
            send("searcherror");
        } else {
            send("searchloaded");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Thread  interrupted.");
            }
            sendObj(result);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void setOut(BufferedWriter out) {
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public SecretKey getSecKey() {
        return secKey;
    }

    public void setSecKey(SecretKey secKey) {
        this.secKey = secKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UsersBLL getUsersBLL() {
        return usersBLL;
    }

    public void setUsersBLL(UsersBLL usersBLL) {
        this.usersBLL = usersBLL;
    }

    public ObjectOutput getObjectOutput() {
        return objectOutput;
    }

    public void setObjectOutput(ObjectOutput objectOutput) {
        this.objectOutput = objectOutput;
    }

//    public ObjectInputStream getObjectInput() {
//        return objectInput;
//    }
//
//    public void setObjectInput(ObjectInputStream objectInput) {
//        this.objectInput = objectInput;
//    }
    public void Close() {
//        send("bye");
        flag = false;
        if (pair != null) {
            if (pair.isGameStarted()) {
                pair.CallForStop();
            }
            pair.RemoveClient(this);
        }

        try {
            System.out.println(name + "Connection Closing..");
            if (in != null) {
                in.close();
                System.out.println(name + " Socket Input Stream Closed ");
            }

            if (out != null) {
                out.close();
                System.out.println(name + "Socket Out Closed ");
            }
            if (objectOutput != null) {
                objectOutput.close();
            }
            if (socket != null) {
                socket.close();
                System.out.println(name + "Socket Closed ");
            }

        } catch (IOException ie) {
            System.out.println(name + "Socket Close Error ");
        }
        Server.workerMap.remove(id);
        Server.workers.remove(this);
    }

    // g???i gmail
    public static void SendEmail(String EMAIL_TO, String text) {
        String SMTP_SERVER = "smtp.gmail.com";
        String USERNAME = "nhichap1202@gmail.com";
        String PASSWORD = "kjjcewgwxkqkfibj";

        String EMAIL_FROM = "nhichap1202@gmail.com";
        String EMAIL_TO_CC = "";

        String EMAIL_SUBJECT = "Th??ng tin t??? KTTN ";
        // String EMAIL_TEXT = "M?? OTP c???a b???n l??: ";
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); // Thi???t l???p smtp, smtp c???a gmail l?? smtp.gmail.com
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.port", "465"); // default port gmail 465
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.starttls.required", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));

            // cc
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(EMAIL_TO_CC, false));

            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content
            msg.setText(text);

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            // System.out.println("Response: " + t.getLastServerResponse());
            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String createOTP(int len) {
        String otp = "";
        String str = "0123456789";
        int n = str.length();
        for (int i = 1; i <= len; i++) {
            otp += (str.charAt((int) ((Math.random() * 10) % n)));
        }

        return otp;
    }
}
