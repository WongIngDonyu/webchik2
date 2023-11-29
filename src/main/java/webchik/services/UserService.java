package webchik.services;



import webchik.models.User;
import webchik.services.dtos.AddUserDto;
import webchik.services.dtos.ShowUserInfoDto;
import webchik.services.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService <I extends UUID>{
    void delete(UserDto user);

    void delete(UUID id);

    List<UserDto> getAll();
    List<ShowUserInfoDto> allUsers();

    Optional<UserDto> findUser(UUID id);

    AddUserDto add(AddUserDto user);
    UserDto update(UserDto user);
    User findByUsername (String username);
    void activation(UUID id);
}
