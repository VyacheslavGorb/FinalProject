package edu.gorb.musicstudio.entity;

import java.math.BigDecimal;

public class Course extends AbstractEntity {
    private String name;
    private String description;
    private String picturePath;
    private BigDecimal pricePerHour;
    private boolean isActive;

    public Course(long entityId, String name, String description, String picturePath,
                  BigDecimal pricePerHour, boolean isActive) {
        super(entityId);
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.pricePerHour = pricePerHour;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Course course = (Course) o;

        if (isActive != course.isActive) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (picturePath != null ? !picturePath.equals(course.picturePath) : course.picturePath != null) return false;
        return pricePerHour != null ? pricePerHour.equals(course.pricePerHour) : course.pricePerHour == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        result = 31 * result + (pricePerHour != null ? pricePerHour.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Course{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", picturePath='").append(picturePath).append('\'');
        sb.append(", pricePerHour=").append(pricePerHour);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
