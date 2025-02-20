package org.elearning.project.DTO;

import org.elearning.project.model.Certificate;
import org.elearning.project.model.Course;
import org.elearning.project.model.User;

import java.util.List;

public class DashBoardData {

    private List<Course> courseList;
    private List<Certificate> certificateList;
    private User user;

    public DashBoardData(List<Course> courseList, List<Certificate> certificateList, User user){
        this.courseList = courseList;
        this.certificateList = certificateList;
        this.user = user;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
