package com.task.todolist.controllers;


import com.task.todolist.models.JsonResponseModel;
import com.task.todolist.models.TodoItemModel;
import com.task.todolist.models.TodoListModel;
import com.task.todolist.services.DependencyService;
import com.task.todolist.services.DependencyServiceInterface;
import com.task.todolist.services.TodoItemServiceInterface;
import com.task.todolist.services.TodoListServiceInterface;
import com.task.todolist.utils.Constant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(TodoListController.BASE_URL)
public class TodoListController {

    public static final String BASE_URL = Constant.BASE_URL + "todoList";
    private final TodoListServiceInterface mTodoListService;
    private final TodoItemServiceInterface mTodoItemService;
    private final DependencyServiceInterface mDependencyService;


    public TodoListController(TodoListServiceInterface todoListServiceInterface, TodoItemServiceInterface todoItemServiceInterface, DependencyService dependencyService) {
        mTodoListService = todoListServiceInterface;
        mTodoItemService = todoItemServiceInterface;
        mDependencyService = dependencyService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "user/{userId}")
    public JsonResponseModel getAllTodoListOfUser(@PathVariable int userId) {
        List<Object> list = new ArrayList<>(mTodoListService.findAllTodoListByUserId(userId));
        JsonResponseModel response = new JsonResponseModel(true, list);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public JsonResponseModel createTodoList(@RequestBody TodoListModel todoListModel) {
        JsonResponseModel response;
        TodoListModel todoList = mTodoListService.createTodoList(todoListModel);
        if (todoList != null) {
            List<Object> list = new ArrayList<>(mTodoListService.findAllTodoListByUserId(todoListModel.getUser().getId()));
            response = new JsonResponseModel(true, "Todo list is created successfully", list);
        } else {
            response = new JsonResponseModel(false, "Failed");
        }
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "delete/{listId}/{userId}")
    public JsonResponseModel deleteTodoList(@PathVariable("listId") int id,@PathVariable("userId") int userId) {

        JsonResponseModel response;
        try {
            //mDependencyService.deleteAllDependenciesByListId(id);
            for (TodoItemModel item : mTodoItemService.findAllTodoItemsByListId(id,-1)) {
                mDependencyService.deleteAllDependencies(item.getId());
            }
            mTodoItemService.deleteAllTodoItems(id);
            mTodoListService.deleteTodoList(id);
            List<Object> list = new ArrayList<>(mTodoListService.findAllTodoListByUserId(userId));
            response = new JsonResponseModel(true, "Success",list);
        }catch (Exception e){
            response = new JsonResponseModel(false, e.getMessage());
        }
        return response;
    }

}
