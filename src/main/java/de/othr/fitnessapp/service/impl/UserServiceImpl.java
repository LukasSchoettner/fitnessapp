package de.othr.fitnessapp.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.Courses;
import de.othr.fitnessapp.model.User;
import de.othr.fitnessapp.repository.UserRepository;
import de.othr.fitnessapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<Courses> getAllCourses(Long id, Pageable pageable) {
        // Implement the logic to retrieve courses for a user by id using the repository
        // Return a Page of Courses
        // Example: return userRepository.findById(id).map(User::getCourses).orElse(Page.empty());
        // Note: Adjust the logic based on your actual data model and relationships
        return Page.empty();
    }

    @Override
    public List<Courses> findCoursesByName(String name) {
        // Implement the logic to find courses by name using the repository
        // Return a List of Courses
        // Example: return userRepository.findByFirstName(name).map(User::getCourses).orElse(Collections.emptyList());
        // Note: Adjust the logic based on your actual data model and relationships
        return List.of();
    }

    @Override
    public User createUser(User user) {
        // Implement the logic to save a user using the repository
        // Return the saved user
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override 
    public Page<User> getAllUsers(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public String getLastNameById(Long id) {
        // Implement the logic to retrieve the last name of a user by id using the repository
        // Return the last name or an empty string if not found
        return userRepository.findById(id).map(User::getLastName).orElse("");
    }

    @Override
    public String getFirstNameById(Long id) {
        // Implement the logic to retrieve the first name of a user by id using the repository
        // Return the first name or an empty string if not found
        return userRepository.findById(id).map(User::getFirstName).orElse("");
    }

    @Override
    public LocalDate getBirthDate(Long id) {
        // Implement the logic to retrieve the birth date of a user by id using the repository
        // Return the birth date or null if not found
        return userRepository.findById(id).map(User::getBirthDate).orElse(null);
    }

    @Override
    public String getPhone(Long id) {
        // Implement the logic to retrieve the phone number of a user by id using the repository
        // Return the phone number or an empty string if not found
        return userRepository.findById(id).map(User::getPhone).orElse("");
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update the existing user with the new data
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setBirthDate(updatedUser.getBirthDate());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());

            return userRepository.save(existingUser);
        }

        return null; // User with the given id not found
    }

    @Override
    public void deleteUser(Long id) {
        // Implement the logic to delete a user using the repository
        userRepository.delete(id);
    }
}
