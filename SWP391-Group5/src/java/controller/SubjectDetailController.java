/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Utils.EncodePassword;
import Utils.SendEmail;
import dal.LessonDAO;
import dal.MajorDAO;
import dal.QuanDAO;
import dal.RegisterDao;
import dal.SubjectDAO;
import dal.UserDAO;
import dal.UserHasSubjectDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.List;
import model.*;

/**
 *
 * @author admin
 */
public class SubjectDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("pid");
        QuanDAO dao = new QuanDAO();
        MajorDAO majorDAO = new MajorDAO();
        List<Major> listMajors = majorDAO.getAll();
        request.setAttribute("listM", listMajors);
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> recentSubject = subjectDAO.getTop3Subject();

        QSubjectDetail p = dao.getSubjectById(id);
        request.setAttribute("details", p);
        HttpSession session = request.getSession();
        UserHasSubjectDAO userHasSubjectDAO = new UserHasSubjectDAO();
        User user = (User) session.getAttribute("account");
        if (user != null) {
            boolean isEnrolled = userHasSubjectDAO.existUserInSubject(p.getSubject_id(), user.getUser_id());
            request.setAttribute("isEnrolled", isEnrolled);
        }
        // for lesson 
        LessonDAO lessonDAO = new LessonDAO();
        List<Lesson> listLesson = lessonDAO.getAllLessonBySubject(Integer.valueOf(id));
        System.out.println(listLesson.size());
        request.setAttribute("lesson", listLesson);
        request.setAttribute("recentSubject", recentSubject);

        request.getRequestDispatcher("coursedetail.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserHasSubjectDAO dao = new UserHasSubjectDAO();
        User u = (User) session.getAttribute("account");
        UserDAO userDAO = new UserDAO();
        RegisterDao registerDao = new RegisterDao();
        String action = request.getParameter("action");
        int subjectid = Integer.parseInt(request.getParameter("subjectId"));
        if (action.equals("errol-now")) {

            dao.addUserHasSubject(subjectid, u.getUser_id());
            session.setAttribute("notification", "Errol successfully!");
            response.sendRedirect("subjectdetail?pid=" + subjectid);
        }

        if (action.equals("un-errol")) {

            dao.removeUserFromSubject(subjectid, u.getUser_id());
            session.setAttribute("notification", "Un-errol successfully!");
            response.sendRedirect("subjectdetail?pid=" + subjectid);
        }
        if (action.equals("join")) {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            int gender = Integer.parseInt(request.getParameter("gender"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));

            // Generate random password
            String randomPassword = generateRandomPassword();
            String password = EncodePassword.toSHAI(randomPassword);
            String[] usernameArr = email.split("@");
            String username = usernameArr[0];
            // Create a new user
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setUserName(username);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setGender(gender);
            newUser.setPassword(password);

            // Check if the email is already registered
            if (registerDao.checkExistedEmail(newUser.getEmail())) {
                session.setAttribute("notificationErr", "Email already exists!");
                response.sendRedirect("subjectdetail?pid=" + subjectid);
                return;
            }

            // Insert the new user into the database
            boolean isRegister = registerDao.insertAccount(newUser);
            SendEmail sm = new SendEmail();
            // Send email to the new user
            if (isRegister) {
                boolean emailSent = sm.sendMailErrol(newUser.getEmail(), randomPassword);

                if (!emailSent) {
                    session.setAttribute("notificationErr", "Failed to send email!");
                    response.sendRedirect("subjectdetail?pid=" + subjectid);
                    return;
                }

                // Add the new user to the subject
                System.out.println(newUser.getUser_id());
                dao.addUserHasSubject(subjectId, registerDao.getLastId());
                session.setAttribute("notification", "Joined successfully! Please check your email");
            } else {

                session.setAttribute("notificationErr", "Joined Faild! Can not create new account with your email!");
            }

            response.sendRedirect("subjectdetail?pid=" + subjectid);
        }
    }

    private String generateRandomPassword() {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";

        String allChars = upperCaseChars + lowerCaseChars + numbers + specialChars;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }

}