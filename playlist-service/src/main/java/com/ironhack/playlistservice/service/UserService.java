package com.ironhack.playlistservice.service;

import com.ironhack.playlistservice.dao.Image;
import com.ironhack.playlistservice.dao.Movie;
import com.ironhack.playlistservice.dao.Playlist;
import com.ironhack.playlistservice.dao.User;
import com.ironhack.playlistservice.dto.*;
import com.ironhack.playlistservice.repository.ImageRepository;
import com.ironhack.playlistservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    PlaylistService playlistService;
    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
//            return createUser(new UserDTO());
            return null;
        }
    }

    public void updateUser(String email, UpdateRequest updateRequest) {
        var user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (updateRequest.getBio() != null) {
                user.get().setBio(updateRequest.getBio());
            }
            if (updateRequest.getNickname() != null) {
                user.get().setUsername(updateRequest.getNickname());
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

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return convertToDto(userRepository.findByEmail(userDTO.getEmail()).get());
        } else {
            User createdUser = convertToEntity(userDTO);
            if (createdUser.getPlaylists() == null) {
                createdUser.setPlaylists(new ArrayList<>());
            }
            createdUser = userRepository.save(createdUser);

            return convertToDto(userRepository.findByEmail(createdUser.getEmail()).get());
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


    public Long storeImage(String email, MultipartFile multipartImage) throws IOException {
        Image dbImage = new Image();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());

        User user = userRepository.findByEmail(email).get();
        if (user.getImage() != null) {
            dbImage.setId(user.getImage().getId());
        }
        user.setImage(imageRepository.save(dbImage));
        return userRepository.save(user).getImage().getId();
    }

    public Resource getImage(Long imageId) {

        byte[] image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContent();
        return new ByteArrayResource(image);
    }

    public UserStatsDTO getUserStats(String email){
        if(userRepository.findByEmail(email).isEmpty()){
            return new UserStatsDTO();
        }else{
            User user=userRepository.findByEmail(email).get();
            UserStatsDTO userStats=new UserStatsDTO();
            List<PlaylistDTO> playlist=playlistService.getByUserId(user.getId());

            userStats.setPlaylistsCount(playlist.size());

            ArrayList<MovieDTO> allTitles=new ArrayList<>();
            ArrayList<Integer> playlistSizes=new ArrayList<>();
            for(PlaylistDTO i : playlist){
                playlistSizes.add(i.getMovies().size());
                allTitles.addAll(i.getMovies());
            }
            userStats.setTitlesCount((int) allTitles.stream().distinct().count());

            OptionalDouble average = playlistSizes
                    .stream()
                    .mapToDouble(a -> a)
                    .average();
            userStats.setAvgTitlesInPlaylist(average.getAsDouble());

            OptionalInt max = playlistSizes
                    .stream()
                    .mapToInt(a -> a)
                    .max();
            userStats.setMaxTitlesInPlaylist(max.getAsInt());

            return userStats;
        }
    }


}
