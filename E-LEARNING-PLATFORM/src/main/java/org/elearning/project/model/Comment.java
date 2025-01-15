package org.elearning.project.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "discussion_id")
    private Discussion discussion;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
