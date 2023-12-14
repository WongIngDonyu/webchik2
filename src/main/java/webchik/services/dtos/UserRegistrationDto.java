package webchik.services.dtos;

import jakarta.validation.constraints.*;
import webchik.validation.UniqueUserName;

public class UserRegistrationDto {
    @UniqueUserName
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String imageUrl;

    // Делайте проверку на уникальность
    private String email;

    private String confirmPassword;

    public UserRegistrationDto() {}

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty(message = "Email cannot be null or empty!")
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
