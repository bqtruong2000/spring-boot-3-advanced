package core.identityservice.controller;

import core.identityservice.dto.request.ApiResponse;
import core.identityservice.dto.request.UserCreationRequest;
import core.identityservice.dto.request.UserUpdateRequest;
import core.identityservice.entity.User;
import core.identityservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createRequest(request));
        return apiResponse;
    }

    @GetMapping("/all")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    User updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
