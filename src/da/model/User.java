/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.model;

/**
 *
 * @author BNC
 */
public class User {
    private int stt;
    private String userName;
    private String passWord;
    private String role;
    private String ID;
    
    public User(){}

    public User(int stt, String userName, String passWord, String role, String ID){
        this.stt= stt;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
        this.ID = ID;
    }
    
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "User{" + "stt=" + stt + ", userName=" + userName + ", passWord=" + passWord + ", role=" + role + ", ID=" + ID + '}';
    }
    
    
    
}
