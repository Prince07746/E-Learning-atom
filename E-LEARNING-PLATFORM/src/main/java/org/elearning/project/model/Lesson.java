package org.elearning.project.model;

import jakarta.persistence.*;

@Entity
@Table(name="lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="title", length = 100,nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name="content_type",nullable = false)
    private Content contentType;

    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name="pricing")
    private  PRICE pricing;

    @Column(name="completed")
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Content getContentType() {
        return contentType;
    }

    public void setContentType(Content contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public PRICE getPricing() {
        return pricing;
    }

    public void setPricing(PRICE pricing) {
        this.pricing = pricing;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentType=" + contentType +
                ", content='" + content + '\'' +
                ", course=" + course +
                ", pricing=" + pricing +
                ", completed=" + completed +
                '}';
    }
}
