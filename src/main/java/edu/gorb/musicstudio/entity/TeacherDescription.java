package edu.gorb.musicstudio.entity;

public class TeacherDescription extends AbstractEntity {
    private String description;
    private int experience;
    private String picturePath;

    public TeacherDescription(long entityId, String description, int experience, String picturePath) {
        super(entityId);
        this.description = description;
        this.experience = experience;
        this.picturePath = picturePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TeacherDescription that = (TeacherDescription) o;

        if (experience != that.experience) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return picturePath != null ? picturePath.equals(that.picturePath) : that.picturePath == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + experience;
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeacherDescription{");
        sb.append("description='").append(description).append('\'');
        sb.append(", experience=").append(experience);
        sb.append(", picturePath='").append(picturePath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
