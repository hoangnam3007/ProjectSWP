/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import DTO.RegisterDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class forgetPasswordDao extends DBContext {

    public void updatePassword(String email, String password) {
        String sql = "UPDATE [dbo].[user]\n"
                + "   SET [password] = ?\n"
                + " WHERE email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Check if email is not exist:
    public boolean checkExistedRegister(String email) {
        String sql = "SELECT [email]\n"
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

    public RegisterDTO getUserByEmail(String email) {
        String sql = "SELECT [userName]\n"
                + "      ,[email]\n"
                + "      ,[password]\n"
                + "  FROM [SWP391_G5].[dbo].[user]\n"
                + "  where email = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RegisterDTO rd = new RegisterDTO(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                return rd;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
