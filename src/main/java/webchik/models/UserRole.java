package webchik.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class UserRole extends BasedEnity{
    private Role role;
    public UserRole(){}
    public UserRole(UUID id, Role role) {
        this.id = id;
        this.role = role;
    }
    @Column(name = "role")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public enum Role {
        USER(0), ADMIN(1);
        private final int number;
        Role(int number) {
            this.number=number;
        }
        public int getNumber(){
            return number;
        }
    }
}
