package com.ivanArrabe.AgenciaTurismo.service;

import com.ivanArrabe.AgenciaTurismo.model.User;

import java.util.List;

public interface IUserService {
    public void saveUser(User user);

    public void editUser(User user, User userEdit);

    public void logicDeleteUser(User user);

    public User findUser(Long id);

    List<User> getUsers();

    public User getUserById(Long id);
}
