package com.task.todolist.controllers;

import com.task.todolist.models.JsonResponseModel;
import com.task.todolist.models.UserModel;
import com.task.todolist.services.UserServiceInterface;
import com.task.todolist.utils.Constant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {


    public static final String BASE_URL = Constant.BASE_URL + "user";
    private final UserServiceInterface mUserService;

    public UserController(UserServiceInterface userServiceInterface) {
        mUserService = userServiceInterface;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonResponseModel loginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        JsonResponseModel response;
        UserModel userModel = mUserService.login(email, password);
        if (userModel != null) {
            List<Object> list = new ArrayList<>();
            list.add(userModel);
            response = new JsonResponseModel(true, "Login successful", list);
        } else {
            response = new JsonResponseModel(false, "Login failed");
        }
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public JsonResponseModel registerUser(@RequestBody UserModel userModel) {
        JsonResponseModel response;
        if (mUserService.checkUserExist(userModel.getEmail()) == 0) {
            UserModel user = mUserService.register(userModel);
            if (user != null) {
                List<Object> list = new ArrayList<>();
                list.add(user);
                response = new JsonResponseModel(true, "Register successful", list);
            } else {
                response = new JsonResponseModel(false, "Register failed");
            }
        } else {
            response = new JsonResponseModel(false, "Email exist");
        }
        return response;
    }


}
