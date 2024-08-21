/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DTO.RegisterDTO;
import Utils.EncodePassword;
import Utils.SendEmail;
import dal.RegisterDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class registerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet registerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Register.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String toEmail = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            String msg = "The password must be at least 8 characters long.";
            request.setAttribute("error_msg", msg);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return; // Stop further execution if validation fails
        }

        // Check if the password contains at least one special character
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
                break;
            }
        }
        
        // Check password must have special character:
        if (!hasSpecialChar) {
            String msg = "The password must contain at least one special character.";
            request.setAttribute("error_msg", msg);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return; // Stop further execution if validation fails
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            String msg = "The passwords you entered do not match.";
            request.setAttribute("error_msg", msg);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return; // Stop further execution if validation fails
        }

        // Call util sendEmail utils:
        SendEmail sm = new SendEmail();

        //Get a random code and save it to RegisterDao.
        String code = sm.getRandom();
        
        RegisterDao d = new RegisterDao();

        //Encode password: 
        password = EncodePassword.toSHAI(password);
        RegisterDTO rg = new RegisterDTO(userName, toEmail, password, code);

        //Send code to user's Email:
        boolean check = sm.checkEmailRegister(rg);

        //If user reuse the e-mail signed up before: 
        if (d.checkExistedRegister(rg)) {
            String msg = "A user is already registered with this e-mail address or userName.";
            request.setAttribute("error_msg", msg);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        } // direct user to verified jsp page and input code from email.
        else {
            HttpSession session = request.getSession();
            session.setAttribute("authcode", rg);
            response.sendRedirect("VerifiedRegister.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String getRandom() {
        Random rd = new Random();
        int number = rd.nextInt(99999);
        return String.format("%06d", number);
    }
}
