///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controller;
//
//import dal.SubjectDAO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.List;
//import model.Subject;
//
///**
// *
// * @author Admin
// */
//public class searchByNameSubjectServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet searchByNameSubjectServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet searchByNameSubjectServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        SubjectDAO dao = new SubjectDAO();
//        // create subject about service 
//        //transmitted from the page servletList.jsp
//        String indexPage = request.getParameter("index");
//
//        // when star run then assign index = 1 to run without errors
//        if (indexPage == null) {
//            indexPage = "1";
//        }
//        int index = Integer.parseInt(indexPage);
//
////////////////////////////////////////
//// get id from subjectlist.jsp find id source name
//        String name = request.getParameter("searchName");
//
//        // find list cource name and Pagination
//        List<Subject> cource = dao.searchByName(index, name);
//
//        // find count all page
//        int count = dao.getTatolName(name);
//
//        //Find page end
//        int endPage = count / 2;
//        if (count % 2 != 0) {
//            endPage++;
//        }
//
//        // push page end to display End_Pade subjectList.jsp
//        request.setAttribute("endPage", endPage);
//        // 
////        request.setAttribute("id", id);
//        request.setAttribute("index", index);
//
//        // push list_Subject to   subjectList.js
//        request.setAttribute("listSubject", cource);
//        request.getRequestDispatcher("searchListByNameSubject.jsp").forward(request, response);
//
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
