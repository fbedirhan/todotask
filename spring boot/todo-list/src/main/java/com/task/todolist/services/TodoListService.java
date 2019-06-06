package com.task.todolist.services;

import com.task.todolist.models.TodoListModel;
import com.task.todolist.repositories.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService implements TodoListServiceInterface {

    private final TodoListRepository mTodoListRepository;

    public TodoListService(TodoListRepository todoListRepository) {
        mTodoListRepository = todoListRepository;
    }

    @Override
    public TodoListModel createTodoList(TodoListModel todoListModel) {
        return mTodoListRepository.save(todoListModel);
    }

    @Override
    public List<TodoListModel> findAllTodoListByUserId(int userId) {
        return mTodoListRepository.findAllTodoListByUserId(userId);
    }

    @Override
    public void deleteTodoList(int todoId) {
        mTodoListRepository.deleteById(todoId);
    }
}
