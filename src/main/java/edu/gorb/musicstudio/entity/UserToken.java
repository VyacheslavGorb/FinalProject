package edu.gorb.musicstudio.entity;

import java.time.LocalDateTime;

public class UserToken extends AbstractEntity {
    private long userId;
    private String token;
    private LocalDateTime creationTimestamp;

    public UserToken(long entityId, long userId, String token, LocalDateTime creationTimestamp) {
        super(entityId);
        this.userId = userId;
        this.token = token;
        this.creationTimestamp = creationTimestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserToken userToken = (UserToken) o;

        if (userId != userToken.userId) return false;
        if (token != null ? !token.equals(userToken.token) : userToken.token != null) return false;
        return creationTimestamp != null ? creationTimestamp.equals(userToken.creationTimestamp) : userToken.creationTimestamp == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (creationTimestamp != null ? creationTimestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserToken{");
        sb.append("userId=").append(userId);
        sb.append(", token='").append(token).append('\'');
        sb.append(", creationTimestamp=").append(creationTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
