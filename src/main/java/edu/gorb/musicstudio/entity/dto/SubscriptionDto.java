package edu.gorb.musicstudio.entity.dto;

import edu.gorb.musicstudio.entity.Subscription;

import java.time.LocalDate;

public class SubscriptionDto {
    private long subscriptionId;
    private long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Subscription.SubscriptionStatus status;
    private int lessonCount;
    private String courseName;
    private String studentName;
    private String studentSurname;

    private SubscriptionDto(Builder builder) {
        setSubscriptionId(builder.subscriptionId);
        setCourseId(builder.courseId);
        setStartDate(builder.startDate);
        setEndDate(builder.endDate);
        setStatus(builder.status);
        setLessonCount(builder.lessonCount);
        setCourseName(builder.courseName);
        setStudentName(builder.studentName);
        setStudentSurname(builder.studentSurname);
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    public Subscription.SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(Subscription.SubscriptionStatus status) {
        this.status = status;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionDto that = (SubscriptionDto) o;

        if (subscriptionId != that.subscriptionId) return false;
        if (courseId != that.courseId) return false;
        if (lessonCount != that.lessonCount) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (status != that.status) return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        return studentSurname != null ? studentSurname.equals(that.studentSurname) : that.studentSurname == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (subscriptionId ^ (subscriptionId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + lessonCount;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (studentSurname != null ? studentSurname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubscriptionDto{");
        sb.append("subscriptionId=").append(subscriptionId);
        sb.append(", courseId=").append(courseId);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", status=").append(status);
        sb.append(", lessonCount=").append(lessonCount);
        sb.append(", courseName='").append(courseName).append('\'');
        sb.append(", studentName='").append(studentName).append('\'');
        sb.append(", studentSurname='").append(studentSurname).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static final class Builder {
        private long subscriptionId;
        private long courseId;
        private LocalDate startDate;
        private LocalDate endDate;
        private Subscription.SubscriptionStatus status;
        private int lessonCount;
        private String courseName;
        private String studentName;
        private String studentSurname;

        public Builder() {
        }

        public Builder setSubscriptionId(long val) {
            subscriptionId = val;
            return this;
        }

        public Builder setCourseId(long val) {
            courseId = val;
            return this;
        }

        public Builder setStartDate(LocalDate val) {
            startDate = val;
            return this;
        }

        public Builder setEndDate(LocalDate val) {
            endDate = val;
            return this;
        }

        public Builder setStatus(Subscription.SubscriptionStatus val) {
            status = val;
            return this;
        }

        public Builder setLessonCount(int val) {
            lessonCount = val;
            return this;
        }

        public Builder setCourseName(String val) {
            courseName = val;
            return this;
        }

        public Builder setStudentName(String val) {
            studentName = val;
            return this;
        }

        public Builder setStudentSurname(String val) {
            studentSurname = val;
            return this;
        }

        public SubscriptionDto build() {
            return new SubscriptionDto(this);
        }
    }
}
