/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ASUS TUF
 */
public class BlogDetail {
    private String title;
    private Date created_at;
    private String blog_img;
    private String blog_content;

    public BlogDetail() {
    }

    public BlogDetail(String title, Date created_at, String blog_img, String blog_content) {
        this.title = title;
        this.created_at = created_at;
        this.blog_img = blog_img;
        this.blog_content = blog_content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getBlog_img() {
        return blog_img;
    }

    public void setBlog_img(String blog_img) {
        this.blog_img = blog_img;
    }

    public String getBlog_content() {
        return blog_content;
    }

    public void setBlog_content(String blog_content) {
        this.blog_content = blog_content;
    }

    @Override
    public String toString() {
        return "BlogDetail{" + "title=" + title + ", created_at=" + created_at + ", blog_img=" + blog_img + ", blog_content=" + blog_content + '}';
    }
    
}
