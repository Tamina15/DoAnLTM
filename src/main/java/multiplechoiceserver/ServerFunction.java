/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplechoiceserver;

import static Utils.Class.SERVER;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class ServerFunction implements Runnable {

    @Override
    public void run() {
        Scanner bai = new Scanner(System.in);
        while (true) {
            System.out.println("input function: ");
            String c = bai.nextLine();
            String[] mang;
            mang = c.split(":");
            int num = c.replaceAll("[^:]", "").length();
            if (num == 0) {
                switch (mang[0]) {
                    case "UserStatistic":
                        UserStatistic();
                        break;
                    case "help":
                        help();
                        break;
                    case "NumMatch":
                        NumMatch(mang[1]);
                        break;
                    case "TimeMatch":
                        TimeMatch(mang[1]);
                        break;
                    case "exit":
                        //SERVER.Close();
                        return;
                    default:
                        System.out.println("wrong syntax: ");
                        break;
                }
            } else {
                System.out.println("wrong syntax: ");
            }
        }

    }

    public void UserStatistic() {
        int onlineuser = 0;
        for (Worker worker : Server.workers) {
            if (!worker.myName.equals("anonymos")) {
                onlineuser++;
            }
        }
        System.out.println("Online Users: " + onlineuser);
    }

    public void NumMatch(String new_amount) {
        try {
            int amount = Integer.parseInt(new_amount);
            if (amount <= 0) {
                System.out.println("Error: Amount of Number per Match must be a larger than 0 integer");
                return;
            }
            Utils.Constant.NUMBER_AMOUNT = amount;
            System.out.println("Changed the number/game to " + Utils.Constant.NUMBER_AMOUNT + " numbers");
        } catch (NumberFormatException e) {
            System.out.println("Wrong Input. Amount of Number per Match must be a larger than zero Integer.");
        }
    }

    public void TimeMatch(String new_time) {
        try {
            int time = Integer.parseInt(new_time);
            if (time <= 0) {
                System.out.println("Error: Time per Match must be a larger-than-zero integer.");
                return;
            }
            Utils.Constant.MATCH_LENGTH = time;
            System.out.println("Changed the time/game to " + Utils.Constant.MATCH_LENGTH + " seconds.");
        } catch (NumberFormatException e) {
            System.out.println("Wrong Input. Time per Match must be a larger-than-zero Integer.");
        }
    }

    public void help() {
        System.out.println("--To know how many users are online, use: UserStatistic");
        System.out.println("--To change Number/Match, use: NumMatch:\"amount of numbers per match\" ...");
        System.out.println("--To change Time/Match, use: TimeMatch:\"amount of seconds per match\" ...");

    }

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

}
