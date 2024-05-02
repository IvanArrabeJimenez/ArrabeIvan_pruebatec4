package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.UserDto;
import com.ivanArrabe.AgenciaTurismo.model.User;
import com.ivanArrabe.AgenciaTurismo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void saveUser(User user) {
        user.setDeleted(false);
        userRepo.save(user);
    }

    @Override
    public void editUser(User user, User userEdit) {
        user.setDni(userEdit.getDni());
        user.setName(userEdit.getName());
        user.setSurname(userEdit.getSurname());
        user.setEmail(userEdit.getEmail());
        userRepo.save(user);
    }

    @Override
    public void logicDeleteUser(User user) {
        user.setDeleted(true);
        userRepo.save(user);
    }

    @Override
    public User findUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepo.findAllByDeletedIsFalse().stream()
                .map(user -> new UserDto(user.getId(), user.getDni(), user.getName(), user.getSurname()))
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepo.findAllByDeletedIsFalse().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user -> new UserDto(user.getId(), user.getDni(), user.getName(), user.getSurname()))
                .orElse(null);
    }

}
