package com.task.todolist.services;

import com.task.todolist.models.TodoListModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoListServiceInterface {

    TodoListModel createTodoList(TodoListModel todoListModel);

    List<TodoListModel> findAllTodoListByUserId(int userId);

    @Transactional
    @Modifying
    void deleteTodoList(int todoId);


}
