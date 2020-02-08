package com.example.demo.Controller;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Api(value="User Management System", description = "Операции над пользователем")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Просмотреть всех пользователей", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "Получить пользователя по Id")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@ApiParam(value = "ID пользователя", required = true)@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Не найден пользователь по данному id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @ApiOperation(value = "Добавить пользователя")
    @PostMapping("/users")
    public User createUser(@ApiParam(value = "Параметры пользователя", required = true)@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @ApiOperation(value = "Обновить информацию выбранного пользователя")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@ApiParam(value = "ID пользователя", required = true)@PathVariable(value = "id") Long userId,
                                           @ApiParam(value = "Обновление информации по пользователю", required = true)@Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден по данному id :: " + userId));
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @ApiOperation(value = "Удалить пользователя")
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@ApiParam(value = "ID удаляемого пользователя", required = true)@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден по данному id :: " + userId));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

