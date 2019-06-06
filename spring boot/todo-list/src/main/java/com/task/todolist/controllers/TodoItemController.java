package com.task.todolist.controllers;

import com.task.todolist.models.DependencyModel;
import com.task.todolist.models.JsonResponseModel;
import com.task.todolist.models.TodoItemModel;
import com.task.todolist.services.DependencyService;
import com.task.todolist.services.DependencyServiceInterface;
import com.task.todolist.services.TodoItemServiceInterface;
import com.task.todolist.utils.Constant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(TodoItemController.BASE_URL)
public class TodoItemController {

    public static final String BASE_URL = Constant.BASE_URL + "todoItem";
    private final TodoItemServiceInterface mTodoItemService;
    private final DependencyServiceInterface mDependencyService;


    public TodoItemController(TodoItemServiceInterface todoItemServiceInterface, DependencyService dependencyService) {
        mTodoItemService = todoItemServiceInterface;
        mDependencyService = dependencyService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public JsonResponseModel createTodoItem(@RequestBody TodoItemModel todoItemModel) {
        JsonResponseModel response;
        TodoItemModel todoItem = mTodoItemService.createTodoItem(todoItemModel);
        if (todoItem != null) {
            List<Object> list = new ArrayList<>(mTodoItemService.findAllTodoItemsByListId(todoItem.getTodoList().getId(),-1));
            response = new JsonResponseModel(true, "Todo item is created successfully", list);
        } else {
            response = new JsonResponseModel(false, "Failed");
        }
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "mark")
    public JsonResponseModel markTodoItem(@RequestParam("listId") int listId, @RequestParam("itemId") int itemId,@RequestParam("state") int state) {
        JsonResponseModel response;

        if (mDependencyService.isTodoItemDependent(itemId) == 0 || state == 1) {
            int updatedState = state == 1 ? 0 : 1;
            TodoItemModel todoItem = mTodoItemService.updateItemState(itemId, updatedState);
            if (todoItem != null) {
                List<Object> list = new ArrayList<>(mTodoItemService.findAllTodoItemsByListId(listId, -1));
                response = new JsonResponseModel(true, list);
            } else {
                response = new JsonResponseModel(false, "Update Failed");
            }
        } else {
            response = new JsonResponseModel(false, "Please check dependencies");

        }

        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "list/{listId}")
    public JsonResponseModel getAllTodoListOfUser(@PathVariable int listId) {
        List<Object> list = new ArrayList<>(mTodoItemService.findAllTodoItemsByListId(listId, -1));
        JsonResponseModel response = new JsonResponseModel(true, list);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "notDependent/{itemId}/{listId}")
    public JsonResponseModel getAllNotDependentTodoListOfUser(@PathVariable int itemId, @PathVariable int listId) {
        List<Object> list = new ArrayList<>(mTodoItemService.findAllNotDependentTodoItems(itemId, listId));
        JsonResponseModel response = new JsonResponseModel(true, list);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "delete/{itemId}/{listId}")
    public JsonResponseModel deleteTodoItem(@PathVariable("itemId") int id, @PathVariable("listId") int listId) {
        JsonResponseModel response;
        try {
            mDependencyService.deleteAllDependencies(id);
            mTodoItemService.deleteTodoItem(id);
            List<Object> list = new ArrayList<>(mTodoItemService.findAllTodoItemsByListId(listId, -1));
            response = new JsonResponseModel(true, "Success", list);
        } catch (Exception e) {
            response = new JsonResponseModel(false, e.getMessage());
        }
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "dependency/{itemId}")
    public JsonResponseModel getAllDependenciesOfTodoItem(@PathVariable int itemId) {
        TodoItemModel model = new TodoItemModel();
        model.setId(itemId);
        List<Object> list = new ArrayList<>(mDependencyService.findTodoItemDependencies(model));

        JsonResponseModel response = new JsonResponseModel(true, list);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "dependency/create", method = RequestMethod.POST)
    public JsonResponseModel createDependency(@RequestBody DependencyModel dependencyModel) {
        JsonResponseModel response;
        List<DependencyModel> dependencyModelList = mDependencyService.createDependency(dependencyModel);
        if (dependencyModelList != null) {
            List<Object> list = new ArrayList<>(dependencyModelList);
            response = new JsonResponseModel(true, "Todo item is created successfully", list);
        } else {
            response = new JsonResponseModel(false, "Failed");
        }
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "delete/dependency/{dependencyId}/{itemId}")
    public JsonResponseModel deleteDependency(@PathVariable("dependencyId") int dependencyId, @PathVariable("itemId") int itemId) {
        JsonResponseModel response;
        try {
            mDependencyService.deleteDependency(dependencyId);
            TodoItemModel todoItemModel = new TodoItemModel();
            todoItemModel.setId(itemId);
            List<Object> list = new ArrayList<>(mDependencyService.findTodoItemDependencies(todoItemModel));
            response = new JsonResponseModel(true, "Success", list);
        } catch (Exception e) {
            response = new JsonResponseModel(false, "Failed");
        }
        return response;
    }

}
