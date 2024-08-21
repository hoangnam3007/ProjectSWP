/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import DTO.RegisterDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author Admin
 */
public class RegisterDao extends DBContext {

    public void insertAccount(RegisterDTO rg) {

        //Insert userName, email, password, created Date to database.
        // Object User is information of User to save data when get in servlet.
        String sql = "INSERT INTO [dbo].[user]\n"
                + "           ([userName]\n"
                + "           ,[email]\n"
                + "           ,[password]\n"
                + "           ,[created_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,GETDATE())";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, rg.getUserName());
            ps.setString(2, rg.getEmail());
            ps.setString(3, rg.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean insertAccount(User u) {

        //Insert userName, email, password, created Date to database.
        // Object User is information of User to save data when get in servlet.
        String sql = "INSERT INTO [dbo].[user]\n"
                + "           ([phone]\n"
                + "           ,[email]\n"
                + "           ,[username]\n"
                + "           ,[gender]\n"
                + "           ,[fullName]\n"
                + "           ,[password]\n"
                + "           ,[created_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,GETDATE())";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, u.getPhone());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getUserName());
            ps.setInt(4, u.getGender());
            ps.setString(5, u.getFullName());
            ps.setString(6, u.getPassword());
            int s = ps.executeUpdate();
            if (s > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    //Check userId and email if user reuse the userID or email.
    public boolean checkExistedRegister(RegisterDTO rd) {
        String sql = "SELECT email \n"
                + "  FROM [SWP391_G5].[dbo].[user]\n"
                + "  where email = ? or userName = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, rd.getEmail());
            ps.setString(2, rd.getUserName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkExistedEmail(String email) {
        String sql = "SELECT email \n"
                + "  FROM [SWP391_G5].[dbo].[user]\n"
                + "  where email = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public int getLastId() {
        int lastId = -1; // Default value if no user exists
        String sql = "SELECT MAX(user_id) AS last_id FROM [dbo].[user]";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return lastId;
    }

    public static void main(String[] args) {
        RegisterDao d = new RegisterDao();
        System.out.println(d.checkExistedEmail("namhxhe173472@fpt.edu.vn"));
    }
}
