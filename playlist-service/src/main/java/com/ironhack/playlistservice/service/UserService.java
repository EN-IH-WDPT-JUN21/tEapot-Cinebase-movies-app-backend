package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.dao.User;
import com.ironhack.playlistservice.dto.UpdateRequest;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDTO getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return convertToDto(user.get());
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

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserDTO createUser(UserDTO userDTO) throws ParseException {
        System.out.println(userDTO.getEmail());
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            return convertToDto(userRepository.findByEmail(userDTO.getEmail()).get());
        }else{
        User createdUser=convertToEntity(userDTO);
            if(createdUser.getPlaylists()==null) {
                createdUser.setPlaylists(new ArrayList<>());
            }
        createdUser=userRepository.save(createdUser);
        convertToDto(userRepository.findByEmail(createdUser.getEmail()).get());
        return convertToDto(userRepository.findByEmail(userDTO.getEmail()).get());
        }
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
