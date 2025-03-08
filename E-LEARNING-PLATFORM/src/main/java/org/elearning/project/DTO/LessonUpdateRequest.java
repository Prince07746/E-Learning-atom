package org.elearning.project.DTO;

// DTO class to map request data
public class LessonUpdateRequest {
    private String lesson;
    private String course;

    // Getters and setters
    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lessonToUpdate) {
        this.lesson = lessonToUpdate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
