package org.elearning.project.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course implements Comparable<Course>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "progress")
    private int progress;

    @Column(name = "image")
    private String image;

    @Column(name = "category")
    private String category;

    @Column(nullable = true)
    private String level;

    @Column(nullable = true)
    private int duration;

    @Column(nullable = true)
    private double rating;

    @Column(name = "date")
    private java.sql.Timestamp date;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusCourse status = StatusCourse.ACTIVE;


    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Lesson> lessonList;

    
    // constructor
    public Course(String title, String description, BigDecimal price, int progress,
                  String image, String category, String level,
                  int duration,Timestamp date, StatusCourse status,Double rating,User teacher) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.progress = progress;
        this.image = image;
        this.category = category;
        this.level = level;
        this.duration = duration;
        this.date = date;
        this.status = status;
        this.rating = rating;
        this.teacher = teacher;
    }

    public Course() {
    }
    // Getters and setters


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public StatusCourse getStatus() {
        return status;
    }

    public void setStatus(StatusCourse status) {
        this.status = status;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public int compareTo(Course other){
        return this.getDate().compareTo(other.getDate());
    }


}
