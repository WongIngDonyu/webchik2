package webchik.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webchik.models.User;
import webchik.repositories.UserRepository;
import webchik.repositories.UserRoleRepository;
import webchik.services.UserRoleService;
import webchik.services.UserService;
import webchik.services.dtos.AddUserDto;
import webchik.services.dtos.ShowUserInfoDto;
import webchik.services.dtos.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService<UUID> {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
    }

    @Override
    public void delete(UserDto user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public void delete(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map((m)->modelMapper.map(m, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ShowUserInfoDto> allUsers() {
        return userRepository.findAll().stream().map((m)->modelMapper.map(m, ShowUserInfoDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findUser(UUID id) {
        return Optional.ofNullable(modelMapper.map(userRepository.findById(id), UserDto.class));
    }

    @Override
    public AddUserDto add(AddUserDto user) {
        User u = modelMapper.map(user, User.class);
       // u.setUserRole(userRoleService.findRoleByName(user.getRole()));
        u.setCreated(LocalDateTime.now());
        u.setActive(true);
        return modelMapper.map(userRepository.saveAndFlush(u), AddUserDto.class);
    }

    @Override
    public UserDto update(UserDto userDto) {
        Optional<User> dbUser = userRepository.findById(userDto.getId());
        if (dbUser.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
            User user1 = dbUser.get();
           // user1.setUserRole(userRoleService.findRoleByName(userDto.getRole()));
            user1.setModified(LocalDateTime.now());
            user1.setCreated(dbUser.get().getCreated());
            return modelMapper.map(userRepository.saveAndFlush(user1), UserDto.class);
        }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void activation(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(true);
            userRepository.saveAndFlush(user);
        }
    }

}

