package com.task.todolist.services;

import com.task.todolist.models.UserModel;
import com.task.todolist.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository mUserRepository;

    public UserService(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public UserModel findUserById(int id) {
        return null;
    }

    @Override
    public List<UserModel> findAllUsers() {
        return null;
    }

    @Override
    public UserModel register(UserModel userModel) {
        return mUserRepository.save(userModel);
    }

    @Override
    public UserModel login(String email, String password) {
        return mUserRepository.login(email,password);
    }

    @Override
    public int checkUserExist(String email) {
        return mUserRepository.isUserExist(email);
    }
}
