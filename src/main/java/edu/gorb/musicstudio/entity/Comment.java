package edu.gorb.musicstudio.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Comment extends AbstractEntity {
    private long studentId;
    private long teacherId;
    private String content;
    private Timestamp dateTime;

    public Comment(long commentId, long studentId, long teacherId, String content, Timestamp dateTime) {
        super(commentId);
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.content = content;
        this.dateTime = dateTime;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return studentId == comment.studentId && teacherId == comment.teacherId && Objects.equals(content, comment.content) && Objects.equals(dateTime, comment.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentId, teacherId, content, dateTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id='").append(getId()).append('\'');
        sb.append("studentId=").append(studentId);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", content='").append(content).append('\'');
        sb.append(", dateTime=").append(dateTime);
        sb.append('}');
        return sb.toString();
    }
}
