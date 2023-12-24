package webchik.services;



import webchik.models.User;
import webchik.services.dtos.AddUserDto;
import webchik.services.dtos.ChangeUserDto;
import webchik.services.dtos.ShowUserInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService <I extends UUID>{
    void delete(UUID id);
    void deActivation(UUID uuid);

    List<ShowUserInfoDto> allUsers();

    Optional<ShowUserInfoDto> findUser(UUID id);

    AddUserDto add(AddUserDto user);
    ChangeUserDto update(ChangeUserDto user);
    User findByUsername (String username);
    void activation(UUID uuid);
}
