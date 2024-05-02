package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.dto.UserDto;
import com.ivanArrabe.AgenciaTurismo.model.User;

import java.util.List;

public interface IUserService {

    void saveUser(User user);

    void editUser(User user, User userEdit);

    void logicDeleteUser(User user);

    User findUser(Long id);

    List<UserDto> getUsers();

    UserDto getUserById(Long id);
}
