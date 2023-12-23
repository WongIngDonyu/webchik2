package webchik.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import webchik.models.UserRole;
import webchik.validation.UniqueUserName;

import java.util.List;

public class AddUserDto {
    @UniqueUserName
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private List<UserRole> userRoles;
    @NotEmpty(message = "Username must not be empty!")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @NotEmpty(message = "Password must not be empty!")
    @Size(min = 5,message = "Password must be over 5 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotEmpty(message = "FirstName must not be empty!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @NotEmpty(message = "LastName must not be empty!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @NotNull(message = "User role must not be empty!")
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
