package com.ironhack.userservice.service;

import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.UpdateRequest;
import com.ironhack.userservice.dto.UserDTO;
import com.ironhack.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOS = new ArrayList<>();
        var users = userRepository.findAll();
        for (User user : users) {
            UserDTO userDTO = getUser(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public UserDTO getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return getUser(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no User with id " + id);
        }
    }

    public void updateUser(Long id, UpdateRequest updateRequest){
        var user = userRepository.findById(id);
        if(user.isPresent()){
            if(updateRequest.getPlaylistId()!= null){
                user.get().getPlaylists().add(updateRequest.getPlaylistId());
            } if(updateRequest.getImageUrl()!= null){
                user.get().setImageUrl(updateRequest.getImageUrl());
            } if(updateRequest.getBio()!= null){
                user.get().setBio(updateRequest.getBio());
            }
            userRepository.save(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no User with id " + id);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void createUser(UserDTO userDTO) {
        userRepository.save(dtoToDao(userDTO));
    }

    public UserDTO getUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setImageUrl(user.getImageUrl());
        return userDTO;
    }

    public User dtoToDao(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setImageUrl(userDTO.getImageUrl());
        return user;
    }
}
