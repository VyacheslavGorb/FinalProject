package edu.gorb.musicstudio.entity;

import java.time.LocalTime;

public class TeacherSchedule extends AbstractEntity{
    private int dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public TeacherSchedule(long entityId, int dayOfWeek, LocalTime startTime, LocalTime endTime) {
        super(entityId);
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TeacherSchedule that = (TeacherSchedule) o;

        if (dayOfWeek != that.dayOfWeek) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        return endTime != null ? endTime.equals(that.endTime) : that.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dayOfWeek;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeacherSchedule{");
        sb.append("dayOfWeek=").append(dayOfWeek);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }
}
