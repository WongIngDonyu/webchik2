package webchik.services.dtos;

import webchik.models.UserRole;

public class ShowUserInfoDto {
    private String username;

    private String password;

    private String firstName;

    private String lastName;
    private boolean isActive;

    private String imageUrl;

    private UserRole.Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserRole.Role getRole() {
        return role;
    }

    public void setRole(UserRole.Role role) {
        this.role = role;
    }
}
