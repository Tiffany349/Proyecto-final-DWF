package com.infinitisky.controller;

import com.infinitisky.dto.request.UserUpdateRequest;
import com.infinitisky.dto.response.UserResponse;
import com.infinitisky.entity.User;
import com.infinitisky.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return toResponse(findUser(id));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                               @Valid @RequestBody UserUpdateRequest req) {
        User user = findUser(id);
        user.setFullname(req.getFullname());
        user.setEmail(req.getEmail());
        return toResponse(userRepository.save(user));
    }

    @PutMapping("/{id}/activate")
    public UserResponse activate(@PathVariable Long id) {
        User user = findUser(id);
        user.setActive(true);
        return toResponse(userRepository.save(user));
    }

    @PutMapping("/{id}/deactivate")
    public UserResponse deactivate(@PathVariable Long id) {
        User user = findUser(id);
        user.setActive(false);
        return toResponse(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .fullname(u.getFullname())
                .email(u.getEmail())
                .role(u.getRole().name())
                .active(u.isActive())
                .build();
    }
}