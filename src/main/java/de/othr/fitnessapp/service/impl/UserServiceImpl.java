package de.othr.fitnessapp.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.model.User;
import de.othr.fitnessapp.repository.UserRepository;
import de.othr.fitnessapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
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
    public Page<User> getAllUsers() {

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
    public void deleteUser(User user) {
        // Implement the logic to delete a user using the repository
        userRepository.delete(user);
    }
}
