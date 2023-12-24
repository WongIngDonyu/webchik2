package webchik.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webchik.models.UserRole;
import webchik.repositories.UserRoleRepository;
import webchik.services.UserRoleService;
import webchik.services.dtos.UserRoleDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService<UUID> {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void delete(UUID id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRoleDto> getAll() {
        return userRoleRepository.findAll().stream().map((m)->modelMapper.map(m, UserRoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserRoleDto> findUserRole(UUID id) {
        return Optional.ofNullable(modelMapper.map(userRoleRepository.findById(id), UserRoleDto.class));
    }

    @Override
    public UserRoleDto add(UserRoleDto userRole) {
        UserRole u = modelMapper.map(userRole, UserRole.class);
        return modelMapper.map(userRoleRepository.saveAndFlush(u), UserRoleDto.class);
    }

    @Override
    public UserRoleDto update(UserRoleDto userRole) {
        Optional<UserRole> dbRole = userRoleRepository.findById(userRole.getId());
        if(dbRole.isEmpty()){
            throw new NoSuchElementException("Role not found");
        }
            UserRole userRole1 = modelMapper.map(userRole, UserRole.class);
            return modelMapper.map(userRoleRepository.saveAndFlush(userRole1), UserRoleDto.class);
    }

    @Override
    public UserRole findRoleByName(UserRole.Role name) {
        return userRoleRepository.findByRole(name).orElse(null);
    }


}
