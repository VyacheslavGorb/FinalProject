package edu.gorb.musicstudio.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LessonSchedule extends AbstractEntity {
    private long studentId;
    private long teacherId;
    private long courseId;
    private long subscriptionId;
    private LocalDateTime startDateTime;
    private LocalTime duration;
    private LessonStatus status;

    private LessonSchedule(Builder builder) {
        super(builder.entityId);
        setStudentId(builder.studentId);
        setTeacherId(builder.teacherId);
        setCourseId(builder.courseId);
        setSubscriptionId(builder.subscriptionId);
        setStartDateTime(builder.startDateTime);
        setDuration(builder.duration);
        setStatus(builder.status);
    }

    public enum LessonStatus {
        NORMAL, CANCELLED
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

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
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
        if (subscriptionId != that.subscriptionId) return false;
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
        result = 31 * result + (int) (subscriptionId ^ (subscriptionId >>> 32));
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
        sb.append(", subscriptionId=").append(subscriptionId);
        sb.append(", startDateTime=").append(startDateTime);
        sb.append(", duration=").append(duration);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }


    public static final class Builder {
        private long entityId;
        private long studentId;
        private long teacherId;
        private long courseId;
        private long subscriptionId;
        private LocalDateTime startDateTime;
        private LocalTime duration;
        private LessonStatus status;

        public Builder setEntityId(long val) {
            entityId = val;
            return this;
        }

        public Builder setStudentId(long val) {
            studentId = val;
            return this;
        }

        public Builder setTeacherId(long val) {
            teacherId = val;
            return this;
        }

        public Builder setCourseId(long val) {
            courseId = val;
            return this;
        }

        public Builder setSubscriptionId(long val) {
            subscriptionId = val;
            return this;
        }

        public Builder setStartDateTime(LocalDateTime val) {
            startDateTime = val;
            return this;
        }

        public Builder setDuration(LocalTime val) {
            duration = val;
            return this;
        }

        public Builder setStatus(LessonStatus val) {
            status = val;
            return this;
        }

        public LessonSchedule build() {
            return new LessonSchedule(this);
        }
    }
}
