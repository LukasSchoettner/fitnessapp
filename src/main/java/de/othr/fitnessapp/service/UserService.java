package de.othr.fitnessapp.service;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

import de.othr.fitnessapp.model.User;

public interface UserService {

    User saveUser(User user);

    User getUserById(Long id);

    Page<User> getAllUsers();

    String getLastNameById(Long id);

    String getFirstNameById(Long id);

    LocalDate getBirthDate(Long id);

    String getPhone(Long id);

    User updateUser(Long id, User user);

    void deleteUser(User user);
    
}