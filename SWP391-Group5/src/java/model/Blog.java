/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author User
 */
public class Blog {
    private int blog_id;
    private String title;
    private String author;
    private String description;
    private Date created_at;
    private String blog_img;
    private int creator_id;
    private String blog_content;
    

    public Blog() {
    }

    public Blog(int blog_id, String title, String author, String description, Date created_at, String blog_img, int creator_id, String blog_content) {
        this.blog_id = blog_id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.created_at = created_at;
        this.blog_img = blog_img;
        this.creator_id = creator_id;
        this.blog_content = blog_content;
    }

    public Blog(String title, String author, String description, Date created_at, String blog_img, int creator_id, String blog_content) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.created_at = created_at;
        this.blog_img = blog_img;
        this.creator_id = creator_id;
        this.blog_content = blog_content;
    }

   

    public int getBlog_id() {
        return blog_id;
    }

    public String getBlog_content() {
        return blog_content;
    }

    public void setBlog_content(String blog_content) {
        this.blog_content = blog_content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    @Override
    public String toString() {
        return "Blog{" + "title=" + title + ", author=" + author + ", description=" + description + ", created_at=" + created_at + ", blog_img=" + blog_img + ", creator_id=" + creator_id + ", blog_content=" + blog_content + '}';
    }

}
