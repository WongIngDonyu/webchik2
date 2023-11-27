package webchik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webchik.models.UserRole;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository  extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findByRole(UserRole.Role name);
}
