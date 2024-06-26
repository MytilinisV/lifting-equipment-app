package com.example.liftingequipmentmain.service;

import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.exceptions.UserAlreadyExistsException;
import com.example.liftingequipmentmain.model.Role;
import com.example.liftingequipmentmain.model.User;
import com.example.liftingequipmentmain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public void saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        userRepository.save(user);
    }

    public void getUser(User user) throws EntityNotFoundException {
        if (userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }
        userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }
       return user;
    }

    @Override
    public Optional<User> getRole() throws EntityNotFoundException {
        Optional<User> user = userRepository.findByRole(Role.USER);
        if (userRepository.findByRole(Role.USER).isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
