/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class BlogDao extends DBContext {

// display list blog
    public List<Blog> getAll(int index) {
        List<Blog> list = new ArrayList<>();
        String sql = "Select * from blog\n"
                + "Order By blog_id\n"
                + "OffSet ? Rows Fetch Next 6 Rows Only;";

        System.out.println(connection);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, (index - 1) * 6);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog p = new Blog(rs.getInt("blog_id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("description"), rs.getDate("created_at"),
                        rs.getString("blog_img"), rs.getInt("creator_id"), rs.getString("blog_content"));

                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Blog> getTop5Blog() {
        List<Blog> list = new ArrayList<>();
        String sql = "select top 5 * from blog order by blog_id desc";

        System.out.println(connection);
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blog p = new Blog(rs.getInt("blog_id"), rs.getString("title"), rs.getString("author"),
                        rs.getString("description"), rs.getDate("created_at"),
                        rs.getString("blog_img"), rs.getInt("creator_id"), rs.getString("blog_content"));

                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Count all the blog pagination standard
    public int getTatolBlog() {
        String sql = "Select count(*) from blog";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // create blog
    public void createBlog(Blog p) {
        String sql = "INSERT INTO [dbo].[blog]\n"
                + "           ([title]\n"
                + "           ,[author]\n"
                + "           ,[description]\n"
                + "           ,[created_at]\n"
                + "           ,[blog_img]\n"
                + "           ,[creator_id]\n"
                + "           ,[blog_content])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, p.getTitle());
            st.setString(2, p.getAuthor());
            st.setString(3, p.getDescription());
            //// get real time

            st.setDate(4, p.getCreated_at());

            st.setString(5, p.getBlog_img());
            st.setInt(6, p.getCreator_id());
            st.setString(7, p.getBlog_content());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // update Blog
//    public void updateBlog(Blog p) {
//        String sql = "UPDATE [dbo].[blog]\n"
//                + "   SET [title] = ? \n"
//                + "      ,[author] = ? \n"
//                + "      ,[description] = ? \n"
//                + "      ,[created_at] = = ? \n"
//                + "      ,[blog_img] = ? \n"
//                + "      ,[creator_id] = ? \n"
//                + "      ,[blog_content] = ? \n"
//                + " WHERE blog_id = ?";
//
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setString(1, p.getTitle());
//            st.setString(2, p.getAuthor());
//            st.setString(3, p.getDescription());
//            //// get real time
//            st.setDate(4, p.getCreated_at());
//
//            st.setString(5, p.getBlog_img());
//            st.setInt(6, p.getCreator_id());
//            st.setString(7, p.getBlog_content());
//            st.setInt(8, p.getBlog_id());
//            st.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    // find id blog to see if it exists or not
//    public Blog FindProductById(int blogId) {
//
//        String sql = "SELECT * FROM [dbo].[blog] where blog_id=? ";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setInt(1, blogId);
//
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                Blog p = new Blog(rs.getString("title"), rs.getString("author"),
//                        rs.getString("description"), rs.getDate("created_at"),
//                        rs.getString("blog_img"), rs.getInt("creator_id"), rs.getString("blog_content"));             
//                return p;
//            }
//        } catch (SQLException ex) {
//           Logger.getLogger(BlogDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
//    }
//       public static void main(String[] args) {
//        BlogDao dao = new BlogDao();
//        dao.create(new Blog("JDP123","Minh","Japan",null,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfaJi_pOyJlnYkplmmQD3kJW7GCWyM6dGhWl2GOsic1Q&s", 4," Through a combination of lectures, hands-on labs, and projects, students will gain practical experience in designing and implementing Java applications."));
//    }
}
