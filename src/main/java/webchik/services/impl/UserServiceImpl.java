package webchik.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webchik.models.User;
import webchik.repositories.UserRepository;
import webchik.repositories.UserRoleRepository;
import webchik.services.UserRoleService;
import webchik.services.UserService;
import webchik.services.dtos.AddUserDto;
import webchik.services.dtos.ChangeUserDto;
import webchik.services.dtos.ShowUserInfoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching

public class UserServiceImpl implements UserService<UUID> {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
    public void deActivation(UUID uuid) {
        Optional<User> optionalUser = userRepository.findById(uuid);;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(false);
            userRepository.save(user);
        }
    }

    //@Cacheable("users")
    @Override
    public List<ShowUserInfoDto> allUsers() {
        return userRepository.findAll().stream().map((m)->modelMapper.map(m, ShowUserInfoDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<ShowUserInfoDto> findUser(UUID id) {
        return Optional.ofNullable(modelMapper.map(userRepository.findById(id), ShowUserInfoDto.class));
    }

    @Override
    public AddUserDto add(AddUserDto user) {
        User u = modelMapper.map(user, User.class);
        u.setCreated(LocalDateTime.now());
        u.setActive(true);
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.saveAndFlush(u), AddUserDto.class);
    }

    @Override
    public ChangeUserDto update(ChangeUserDto userDto) {
        Optional<User> dbUser = userRepository.findById(userDto.getId());
        if (dbUser.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
            User user1 = modelMapper.map(userDto, User.class);
            user1.setPassword(passwordEncoder.encode(user1.getPassword()));
            user1.setModified(LocalDateTime.now());
            user1.setCreated(dbUser.get().getCreated());
            return modelMapper.map(userRepository.saveAndFlush(user1), ChangeUserDto.class);
        }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void activation(UUID uuid) {
        Optional<User> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(true);
            userRepository.saveAndFlush(user);
        }
    }
}

