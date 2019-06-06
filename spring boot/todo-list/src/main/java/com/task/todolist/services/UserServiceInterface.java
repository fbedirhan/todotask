package com.task.todolist.services;

import com.task.todolist.models.UserModel;

import java.util.List;

public interface UserServiceInterface {


    UserModel findUserById(int id);

    List<UserModel> findAllUsers();

    UserModel register(UserModel userModel);

    UserModel login(String email, String password);

    int checkUserExist(String email);


}
