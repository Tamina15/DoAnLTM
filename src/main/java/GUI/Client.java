/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static Utils.Class.*;

import static Utils.Constant.ADDRESS;
import static Utils.Constant.PORT;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class Client {

    private Socket socket = null;
    public BufferedWriter out = null;
    public BufferedReader in = null;
    private Scanner scanner = null;
    private boolean flag = true;

    byte[] ServerpubKeyByte;
    SecretKey secKey;
    ObjectInputStream objectInput;
    File f;

    public Client() {

    }

    public Client(String address, int port) {
        System.out.println("Use email: admin@gmail.com, password: admin");
        System.out.println("Use email: user@gmail.com, password: user");
        System.out.println("For login without database");
        System.out.println("Warning: No mySQL Database connnected may cause error");
        System.out.println("For database, see libs folder");
        try {
            socket = new Socket(address, port);
            System.out.println("Da ket noi");
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            scanner = new Scanner(System.in);
            objectInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        Receive r = new Receive();
        r.start();
    }

    class Receive extends Thread {

        @Override
        public void run() {
            try {
                System.out.println("Listening");
                Vector<Vector<String>> result = new Vector<Vector<String>>();
                while (flag) {
                    String line = in.readLine();
                    System.out.println("Line :" + line);
                    String[] array = line.split("\\]\\:\\$\\:\\[");
                    String command = array[0];
                    switch (command) {
                        case "rankloaded":
                            result = (Vector<Vector<String>>) objectInput.readObject();
                            STATISTIC.loadRankingTable(result);
                            break;
                        case "percentloaded":
                            result = (Vector<Vector<String>>) objectInput.readObject();
                            STATISTIC.loadWinPercentTable(result);
                            break;
                        case "searchloaded":
                            result = (Vector<Vector<String>>) objectInput.readObject();
                            STATISTIC.loadCheckSTTTable(result);
                            break;
                        case "searcherror":
                            STATISTIC.searchFail();
                            break;
                        // N???u login th??nh c??ng
                        case "loginsuccess":
                            LOGIN_FORM.LoginSuccess();
                            break;
                        // N???u login th???t b???i
                        case "loginfail":
                            LOGIN_FORM.LoginFail();
                            break;
                        // N???u email kh??ng ????ng
                        case "emailkhonghople":
                            REGISTER_FORM.EmailKhongHopLe();
                            break;
                        // N???u email h???p l???
                        case "emailhople":
                            REGISTER_FORM.EmailHopLe();
                            break;
                        // n???u m?? OTP ????ng
                        case "signupsuccess":
                            OTP_FORM.OTPhopLe();
                            break;
                        // N???u sql l???i trong khi ????ng k??
                        case "signupfail":
                            REGISTER_FORM.SignUpFail();
                            break;
                        //N???u otp h???t h???n
                        case "otphethan":
                            OTP_FORM.TimeoutOTP();
                            break;
                        //N???u otp sai
                        case "otpsai":
                            OTP_FORM.WrongOTP();
                            break;
                        // N???u update info l???i
                        case "updateinfoerror":
                            EDIT_INFO_FORM.EditInfoFail();
                            break;
                        // N???u update info th??nh c??ng
                        case "updateinfosuccess":
                            EDIT_INFO_FORM.EditInfoSuccess();
                            break;
                        // t???i d??? li???u user
                        case "UserInfo":
                            EDIT_INFO_FORM.loadInfoInEdit(line);
                            break;
                        //
                        case "wait":
                            break;
                        case "NumMatch":
                            mainFrame.amount = Integer.parseInt(array[1]);
                            break;
                        // B???t ?????u game
                        case "gamestart":
                            MAIN_FRAME.ready = true;
                            break;
                        // th???i gian c???a v??n game
                        case "time":
                            MAIN_FRAME.setTime(array[1]);
                            break;
                        // s??? may m???n
                        case "lucky":
                            MAIN_FRAME.setLuckyNumber(array[1]);
                            break;
                        // N???u ?????i ph????ng nh???n
                        case "opponentClicked":
                            MAIN_FRAME.setNumber(array[1], Color.RED);
                            break;
                        // N???u m??nh nh???n
                        case "IClicked":
                            MAIN_FRAME.setNumber(array[1], Color.BLUE);
                            break;
                        // ??i???m c???a ng?????i ch??i 1
                        case "point1":
                            System.out.println("Point of player 1");
                            MAIN_FRAME.pointPanel.setPlayer1(array[1]);
                            break;
                        // ??i???m c???a ng?????i ch??i 2
                        case "point2":
                            MAIN_FRAME.pointPanel.setPlayer2(array[1]);
                            break;
                        // S??? ti???p theo, kh??ng ph???i l?? s??? may m???n
                        case "next":
                            MAIN_FRAME.setNextNumber(array[1]);
                            break;
                        // D??ng t??ng s???c m???nh
                        case "PowerUp":
                            MAIN_FRAME.pointPanel.getPowerUp().setEnabled(true);
                            break;
                        // B??? ??en m??n h??nh trong 2 gi??y
                        case "blackout":
                            MAIN_FRAME.Blackout();
                            break;
                        // K???t qu??? tr???n ?????u
                        case "result":
                            MAIN_FRAME.Result(array[1]);
                            break;
                        case "nothing":
                            break;
                    }
                    if (line.equals("bye")) {
                        flag = false;
                        return;
                    }

                }
            } catch (IOException ex) {
                System.out.println("Error while processing");
                exit();
                flag = false;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                exit();
            }
        }
    }

    public void send(String cmd, String s) {
        try {
            String kq = cmd + "]:$:[" + s;
            out.write((kq));
            out.newLine();
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendOnlyCmd(String cmd) {
        try {
            out.write((cmd));
            out.newLine();
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String receive() throws IOException {
        return (in.readLine());
    }

    //                                                                                                          ^
    //                                                                                                          |
// Kh??ng tr??? v??? result n???a, client s??? g???i h??m x??? l?? c???a class t????ng ???ng v???i d??? li???u server tr??? v???, xem h??m run()|, xem LoginForm
    public void verifyLogin(String email, char[] passwd) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(passwd);
            String loginInput = email + "]:$:[" + hashPasswordMD5(sb.toString());
            send("login", loginInput);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendOTP(String otp) {
        sendOnlyCmd(otp);
    }

    public void signup(String email, char[] passwd, String name, String gender, String DoB) {
        StringBuilder sb = new StringBuilder();
        sb.append(passwd);
        String input;
        try {
            input = email + "]:$:[" + hashPasswordMD5(sb.toString()) + "]:$:[" + name + "]:$:[" + gender + "]:$:[" + DoB;
            send("signup", input);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editInfo(String name, String gender, String DoB, char[] passwd) {
        StringBuilder sb = new StringBuilder();
        sb.append(passwd);
        String pass;
        try {
            pass = hashPasswordMD5(sb.toString());
            // Tr?????ng h???p ng?????i d??ng ko nh???p g?? v??o field password trong frame edit
            if (sb.toString().equals("")) {
                pass = "nochange";
            }
            String input = name + "]:$:[" + gender + "]:$:[" + DoB + "]:$:[" + pass;
            send("editInfo", input);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getInfoOfUser() throws IOException {
        sendOnlyCmd("UserInfo");
    }
//

    public void getTable() {
        sendOnlyCmd("Statistic");
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Thread  interrupted.");
        }
    }

    public void getPTable() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Thread  interrupted.");
        }
        sendOnlyCmd("PercentRank");
    }

    public void getSearch(String key) {
        send("SearchRank", key);
    }

    public String hashPasswordMD5(String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        // Returns the result
        return sb.toString();
    }

    public void exit() {
        try {
            System.out.println("Connection Closing..");
            if (scanner != null) {
                scanner.close();
                System.out.println("Scanner Closed");
            }
            if (in != null) {
                in.close();
                System.out.println("Socket Input Stream Closed");
            }

            if (out != null) {
                out.close();
                System.out.println("Socket Out Closed");
            }
            if (socket != null) {
                socket.close();
                System.out.println("Socket Closed");
            }

        } catch (IOException ie) {
            System.out.println("Socket Close Error");
        }
    }

    public static void main(String[] args) throws IOException, UnknownHostException, NoSuchAlgorithmException {
        Client client2 = new Client(ADDRESS, PORT);
        HOME = new Home(client2);
        HOME.setVisible(true);

    }

    public static void call() {
        JOptionPane.showMessageDialog(null, "Edit successfully.", "Information", JOptionPane.ERROR_MESSAGE);
    }
}
