/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 * Create DTO to save the data when user sign in and
 * save code when sever sent to clients
 */
public class RegisterDTO {

    private String userName;
    private String email;
    private String password;
    
    // The code sever sent to client to verify:
    private String code;

    public RegisterDTO() {
    }

    public RegisterDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    

    public RegisterDTO(String userName, String email, String password, String code) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
