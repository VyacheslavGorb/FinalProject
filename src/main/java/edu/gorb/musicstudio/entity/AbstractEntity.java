package edu.gorb.musicstudio.entity;

import java.util.Objects;

public abstract class AbstractEntity {
    private long entityId;

    protected AbstractEntity() {
    }

    protected AbstractEntity(long entityId) {
        this.entityId = entityId;
    }

    public long getId() {
        return entityId;
    }

    public void setId(long entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return entityId == that.entityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AbstractEntity{");
        sb.append("entityId=").append(entityId);
        sb.append('}');
        return sb.toString();
    }
}
