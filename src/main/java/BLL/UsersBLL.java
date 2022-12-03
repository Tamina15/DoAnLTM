/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.UsersDAL;
import DTO.Users;
import java.util.Vector;

/**
 *
 * @author king
 */
public class UsersBLL {
    private UsersDAL usersDAL = new UsersDAL();
    
    public Vector<Users> getUsers() {
        Vector<Users> listUsers = usersDAL.getUsers();
        return listUsers;
    }
    public Vector<Users> getRanking(){
        Vector<Users> listUsers = usersDAL.getRanking();
        return listUsers;
    }
    public int getPoint(String email){
        int result = usersDAL.getPointByMail(email);
        return result;
    }
    public Vector<Users> getWinPercentRanking(){
        Vector<Users> listUsers = usersDAL.getPercentRanking();
        return listUsers;
    }
    public int getWinPercentById(String email){
        int result = usersDAL.getWinPercentByMail(email);
        return result;
    }
    public Vector<Users> searchRank(String key){
        Vector<Users> listUsers = usersDAL.searchRank(key);
        return listUsers;
    }
    public int getRankById(String email){
        int result = usersDAL.getRankByMail(email);
        return result;
    }
}
