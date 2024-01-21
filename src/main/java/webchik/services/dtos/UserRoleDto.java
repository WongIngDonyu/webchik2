package webchik.services.dtos;


import webchik.models.UserRole;

import java.util.UUID;

public class UserRoleDto {
    private UUID id;
    private UserRole.Role role;
    public UserRoleDto(){}

    public UserRoleDto(UUID id, UserRole.Role role) {
        this.id = id;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserRole.Role getRole() {
        return role;
    }

    public void setRole(UserRole.Role role) {
        this.role = role;
    }
}
