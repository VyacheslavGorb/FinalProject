package edu.gorb.musicstudio.entity;

import java.time.LocalDateTime;

public class Comment extends AbstractEntity { //todo
    private long studentId;
    private long courseId;
    private String content;
    private LocalDateTime dateTime;

    public Comment(long entityId, long studentId, long courseId, String content, LocalDateTime dateTime) {
        super(entityId);
        this.studentId = studentId;
        this.courseId = courseId;
        this.content = content;
        this.dateTime = dateTime;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (studentId != comment.studentId) return false;
        if (courseId != comment.courseId) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        return dateTime != null ? dateTime.equals(comment.dateTime) : comment.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (studentId ^ (studentId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("studentId=").append(studentId);
        sb.append(", courseId=").append(courseId);
        sb.append(", content='").append(content).append('\'');
        sb.append(", dateTime=").append(dateTime);
        sb.append('}');
        return sb.toString();
    }
}
