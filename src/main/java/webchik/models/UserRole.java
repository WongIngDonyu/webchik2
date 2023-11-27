package webchik.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class UserRole extends BasedEnity{
    private Role role;
    private List<User> users;
    public UserRole(){}

    public UserRole(UUID id, Role role, List<User> users) {
        this.id = id;
        this.role = role;
        this.users = users;
    }
    @Column(name = "role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public enum Role {
        User(0), Admin(1);
        private int number;
        Role(int number) {
            this.number=number;
        }
        public int getNumber(){
            return number;
        }
    }
}
