package edu.gorb.musicstudio.entity;

import java.time.LocalDate;

public class Subscription extends AbstractEntity {
    private long studentId;
    private long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
    private int lessonCount;

    public enum SubscriptionStatus {
        APPROVED, ACTIVATED, WAITING_FOR_APPROVE, CANCELLED
    }

    public Subscription(long entityId, long studentId, long courseId, LocalDate startDate, LocalDate endDate, SubscriptionStatus status, int lessonCount) {
        super(entityId);
        this.studentId = studentId;
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.lessonCount = lessonCount;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Subscription that = (Subscription) o;

        if (studentId != that.studentId) return false;
        if (courseId != that.courseId) return false;
        if (lessonCount != that.lessonCount) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (studentId ^ (studentId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + lessonCount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subscription{");
        sb.append("studentId=").append(studentId);
        sb.append(", courseId=").append(courseId);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", status=").append(status);
        sb.append(", lessonCount=").append(lessonCount);
        sb.append('}');
        return sb.toString();
    }
}
