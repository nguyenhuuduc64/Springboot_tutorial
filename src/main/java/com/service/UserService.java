package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.request.UserCreationRequest;
import com.dto.request.UserUpdateRequest;
import com.dto.response.UserResponse;
import com.entity.User;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor //bien nao co defind la final thi se duoc inject vao class 
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true) //cac field khong co type thi se mac dinh la private va dua vao contructor nhu final
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public User createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("User existed");

    User user = userMapper.toUser(request);
        return userRepository.save(user);
    }

    /*khi dung Annotation builder ben request thi co the tao nhanh 1 request */
    // UserCreationRequest newUserRequest = UserCreationRequest.builder()
    // .username("name")
    // .fullName("name")
    // .build();

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    //tra ve User Entity cua DB
    private User getUserEntityById(String id){
    return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse getUserById(String id){
        User user = getUserEntityById(id);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest userUpdate){
        User user = getUserEntityById(id);
        userMapper.updateUser(user, userUpdate );
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
