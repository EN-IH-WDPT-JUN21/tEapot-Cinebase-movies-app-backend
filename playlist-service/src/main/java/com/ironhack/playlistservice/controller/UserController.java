package com.ironhack.playlistservice.controller;

import com.ironhack.playlistservice.dto.UpdateRequest;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin(origins= {"http://localhost:4200", "http://localhost:8000"})
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return principal.getAccessTokenHash();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getByEmail(@PathVariable("email") String email) throws ParseException {
        return userService.getByEmail(email);
    }

    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadImage(@PathVariable(name="imageId") Long imageId) {
        return userService.getImage(imageId);
    }


    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable("email") String email, @RequestBody UpdateRequest updateRequest){
        userService.updateUser(email, updateRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) throws ParseException {
        return userService.createUser(userDTO);
    }

    @PostMapping("/{email}/image")
    public Long uploadImage(@PathVariable(name="email") String email, @RequestParam MultipartFile multipartImage) throws Exception {
        return userService.storeImage(email, multipartImage);
    }


    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable("email") String email) {
        userService.deleteUser(email);
    }
}
