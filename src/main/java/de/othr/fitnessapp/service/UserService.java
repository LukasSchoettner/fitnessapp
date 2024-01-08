package de.othr.fitnessapp.service;

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.othr.fitnessapp.model.User;
import de.othr.fitnessapp.model.Courses;

public interface UserService {

    Page<Courses> getAllCourses(Long id, Pageable pageable);

    List<Courses> findCoursesByName(String name);

    User createUser(User user);

    User getUserById(Long id);

    Page<User> getAllUsers(Pageable pageable);

    String getLastNameById(Long id);

    String getFirstNameById(Long id);

    LocalDate getBirthDate(Long id);

    String getPhone(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
    
}