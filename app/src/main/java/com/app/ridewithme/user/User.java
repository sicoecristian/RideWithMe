package com.app.ridewithme.user;

import java.util.Objects;

public class User {
    private String email;
    private String password;
    private boolean hasRightToDrive;

    public User(){}

    public User(String email, String password, boolean hasRightToDrive) {
        this.email = email;
        this.password = password;
        this.hasRightToDrive = hasRightToDrive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHasRightToDrive() {
        return hasRightToDrive;
    }

    public void setHasRightToDrive(boolean hasRightToDrive) {
        this.hasRightToDrive = hasRightToDrive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return hasRightToDrive == user.hasRightToDrive && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, hasRightToDrive);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hasRightToDrive=" + hasRightToDrive +
                '}';
    }
}
