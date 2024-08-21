package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.BlogDetail;

public class BlogDetailDao extends DBContext {

    // get detail from database by id
    public BlogDetail getDetailById(int id) {
        BlogDetail detail = null;
        String sql = "SELECT title, blog_content, blog_img, created_at FROM blog WHERE blog_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);  
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                detail = new BlogDetail(rs.getString("title"), rs.getDate("created_at"), rs.getString("blog_img"), rs.getString("blog_content"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detail;
    }
// list 3 product for Recent post
    public List<BlogDetail> listRecentpost() {
        List<BlogDetail> listRS = new ArrayList<>();
        String sql = "SELECT top 3 [title]\n"
                + "      ,[created_at]\n"
                + "      ,[blog_img]\n"
                + "  FROM [dbo].[blog]\n"
                + "  order by created_at DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                BlogDetail blogrecent = new BlogDetail(rs.getString("title"), rs.getDate("created_at"), rs.getString("blog_img"), rs.getString("blog_content"));
                listRS.add(blogrecent);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRS;
    }
    //search for phần search 
    public BlogDetail searchByTitle(String t) {
        BlogDetail detail = null;
        String sql = "SELECT title, blog_content, blog_img, created_at FROM blog WHERE title = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, t);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                detail = new BlogDetail(rs.getString("title"), rs.getDate("created_at"), rs.getString("blog_img"), rs.getString("blog_content"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detail;
    }

    //class main test data xem đã được load lên hay chưa 
    public static void main(String[] args) {
        BlogDetailDao blogDetailDao = new BlogDetailDao();
        BlogDetail blogDetail = blogDetailDao.getDetailById(4); 
        if (blogDetail != null) {
            System.out.println(blogDetail);
        } else {
            System.out.println("No blog found with the given ID.");
        }

    }
}
