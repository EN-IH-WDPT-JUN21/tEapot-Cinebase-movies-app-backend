package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.dao.User;
import com.ironhack.playlistservice.dto.UpdateRequest;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

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

    public UserDTO getByEmail(String email) throws ParseException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return convertToDto(userRepository.findByEmail(email).get());
        } else {
            return createUser(new UserDTO(email));
        }
    }

    public void updateUser(String email, UpdateRequest updateRequest){
        var user = userRepository.findByEmail(email);
        if(user.isPresent()){
            if(updateRequest.getPlaylistId()!= null){
                user.get().getPlaylists().add(updateRequest.getPlaylistId());
            }
            if(updateRequest.getImageUrl()!= null){
                user.get().setImageUrl(updateRequest.getImageUrl());
            }
            if(updateRequest.getBio()!= null){
                user.get().setBio(updateRequest.getBio());
            }
            userRepository.save(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no User with email " + email);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO createUser(UserDTO userDTO) throws ParseException {
        User createdUser=userRepository.save(convertToEntity(userDTO));
        userDTO=convertToDto(createdUser);
        return userDTO;
    }


    //**** UTIL METHODS *****
    public UserDTO getUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setImageUrl(user.getImageUrl());
        return userDTO;
    }

    public User dtoToDao(UserDTO userDTO) throws ParseException {
        return convertToEntity(userDTO);
    }

    public UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) throws ParseException {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
