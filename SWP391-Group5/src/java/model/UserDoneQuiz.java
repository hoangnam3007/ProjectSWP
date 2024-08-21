/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class UserDoneQuiz {
    private Quiz quiz;
    private User user;
    private int score;
    private int is_pass;

    public UserDoneQuiz() {
    }

    public UserDoneQuiz(Quiz quiz, User user, int score, int is_pass) {
        this.quiz = quiz;
        this.user = user;
        this.score = score;
        this.is_pass = is_pass;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIs_pass() {
        return is_pass;
    }

    public void setIs_pass(int is_pass) {
        this.is_pass = is_pass;
    }

    @Override
    public String toString() {
        return "UserDoneQuiz{" + "quiz=" + quiz + ", user=" + user + ", score=" + score + ", is_pass=" + is_pass + '}';
    }
}
