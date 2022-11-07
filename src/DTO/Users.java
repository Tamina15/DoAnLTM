package src.DTO;

import java.util.Date;

public class Users {
	private int user_id;
	private String email;
	private String password;
	private String name;
	private String gender;
	private Date DoB;
	private int status; // 0 - chưa verify hoặc server block; 1 - là bth; 2 - ko cho tạo đề thi; 3 - không cho thi;
	
	
	
	public Users(int user_id, String email, String password, String name, String gender, Date doB, int status) 
	{
		this.user_id = user_id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.DoB = doB;
		this.status = status;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDoB() {
		return DoB;
	}
	public void setDoB(Date doB) {
		DoB = doB;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
