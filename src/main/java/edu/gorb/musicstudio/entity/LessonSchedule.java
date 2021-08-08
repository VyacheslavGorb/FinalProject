package edu.gorb.musicstudio.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LessonSchedule extends AbstractEntity {
    private long studentId;
    private long teacherId;
    private long courseId;
    private LocalDateTime startDateTime;
    private LocalTime duration;
    private LessonStatus status;

    public enum LessonStatus {
        NORMAL, CANCELLED
    }

    public LessonSchedule(long entityId, long studentId, long teacherId, long courseId, LocalDateTime startDateTime,
                          LocalTime duration, LessonStatus status) {
        super(entityId);
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.status = status;
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

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LessonSchedule that = (LessonSchedule) o;

        if (studentId != that.studentId) return false;
        if (teacherId != that.teacherId) return false;
        if (courseId != that.courseId) return false;
        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null)
            return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (studentId ^ (studentId >>> 32));
        result = 31 * result + (int) (teacherId ^ (teacherId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LessonSchedule{");
        sb.append("studentId=").append(studentId);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", courseId=").append(courseId);
        sb.append(", startDateTime=").append(startDateTime);
        sb.append(", duration=").append(duration);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
