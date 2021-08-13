package edu.gorb.musicstudio.entity;

public class Teacher {
    private long teacherId;
    private String name;
    private String surname;
    private String patronymic;
    private UserStatus status;
    private String selfDescription;
    private int experienceYears;
    private String picturePath;
    private boolean isDescriptionProvided;

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public boolean isDescriptionProvided() {
        return isDescriptionProvided;
    }

    public void setDescriptionProvided(boolean descriptionProvided) {
        isDescriptionProvided = descriptionProvided;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher that = (Teacher) o;

        if (teacherId != that.teacherId) return false;
        if (experienceYears != that.experienceYears) return false;
        if (isDescriptionProvided != that.isDescriptionProvided) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (status != that.status) return false;
        if (selfDescription != null ? !selfDescription.equals(that.selfDescription) : that.selfDescription != null)
            return false;
        return picturePath != null ? picturePath.equals(that.picturePath) : that.picturePath == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (teacherId ^ (teacherId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (selfDescription != null ? selfDescription.hashCode() : 0);
        result = 31 * result + experienceYears;
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (isDescriptionProvided ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeacherDto{");
        sb.append("teacherId=").append(teacherId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", patronymic='").append(patronymic).append('\'');
        sb.append(", status=").append(status);
        sb.append(", selfDescription='").append(selfDescription).append('\'');
        sb.append(", experienceYears=").append(experienceYears);
        sb.append(", picturePath='").append(picturePath).append('\'');
        sb.append(", isDescriptionProvided=").append(isDescriptionProvided);
        sb.append('}');
        return sb.toString();
    }
}
