package edu.gorb.musicstudio.dto;

import edu.gorb.musicstudio.entity.LessonSchedule;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LessonScheduleDto {
    private long scheduleId;
    private String studentName;
    private String studentSurname;
    private String teacherName;
    private String teacherSurname;
    private String courseName;
    private LocalDateTime startDateTime;
    private LocalTime duration;
    private LessonSchedule.LessonStatus status;

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public LessonSchedule.LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonSchedule.LessonStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonScheduleDto that = (LessonScheduleDto) o;

        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (studentSurname != null ? !studentSurname.equals(that.studentSurname) : that.studentSurname != null)
            return false;
        if (teacherName != null ? !teacherName.equals(that.teacherName) : that.teacherName != null) return false;
        if (teacherSurname != null ? !teacherSurname.equals(that.teacherSurname) : that.teacherSurname != null)
            return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) return false;
        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null)
            return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = studentName != null ? studentName.hashCode() : 0;
        result = 31 * result + (studentSurname != null ? studentSurname.hashCode() : 0);
        result = 31 * result + (teacherName != null ? teacherName.hashCode() : 0);
        result = 31 * result + (teacherSurname != null ? teacherSurname.hashCode() : 0);
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LessonScheduleDto{");
        sb.append("studentName='").append(studentName).append('\'');
        sb.append(", studentSurname='").append(studentSurname).append('\'');
        sb.append(", teacherName='").append(teacherName).append('\'');
        sb.append(", teacherSurname='").append(teacherSurname).append('\'');
        sb.append(", courseName='").append(courseName).append('\'');
        sb.append(", startDateTime=").append(startDateTime);
        sb.append(", duration=").append(duration);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
