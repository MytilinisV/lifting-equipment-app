package com.example.liftingequipmentmain.service;

import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.exceptions.UserAlreadyExistsException;
import com.example.liftingequipmentmain.model.User;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    void saveUser(User user) throws UserAlreadyExistsException;
    void getUser(User user) throws EntityNotFoundException;
    Optional<User> getUserByUsername(String user) throws EntityNotFoundException;
    Optional<User> getRole() throws EntityNotFoundException;
    List<User> findAllUsers();
}
