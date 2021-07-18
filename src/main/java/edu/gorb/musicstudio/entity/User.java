package edu.gorb.musicstudio.entity;

import java.util.Objects;

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private UserRole role;
    private UserStatus status;

    private User(Builder builder) {
        super(builder.userId);
        this.login = builder.login;
        this.password = builder.password;
        this.name = builder.name;
        this.surname = builder.surname;
        this.patronymic = builder.patronymic;
        this.email = builder.email;
        this.role = builder.role;
        this.status = builder.status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password)
                && Objects.equals(name, user.name) && Objects.equals(surname, user.surname)
                && Objects.equals(patronymic, user.patronymic) && Objects.equals(email, user.email)
                && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), login, password, name, surname, patronymic, email, role, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(getId()).append('\'');
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", patronymic='").append(patronymic).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private static final String DEFAULT_PHONE_NUMBER_VALUE = "";

        private long userId;
        private String login;
        private String password;
        private String name;
        private String surname;
        private String patronymic;
        private String email;
        private UserRole role;
        private UserStatus status;

        public Builder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }


}
