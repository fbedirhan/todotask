package com.task.todolist.services;

import com.task.todolist.models.TodoItemModel;
import com.task.todolist.repositories.TodoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService implements TodoItemServiceInterface {

    private final TodoItemRepository mTodoItemRepository;


    public TodoItemService(TodoItemRepository todoItemRepository) {
        mTodoItemRepository = todoItemRepository;
    }

    @Override
    public List<TodoItemModel> findAllTodoItemsByListId(int todoId, int state) {
        return mTodoItemRepository.findAllTodoItemsByListId(todoId);
    }

    @Override
    public List<TodoItemModel> findAllNotDependentTodoItems(int itemId, int listId) {
        return mTodoItemRepository.findAllNotDependentTodoItems(itemId, listId);
    }


    @Override
    public TodoItemModel createTodoItem(TodoItemModel todoItemModel) {
        return mTodoItemRepository.save(todoItemModel);
    }

    @Override
    public void deleteAllTodoItems(int todoListId) {
        mTodoItemRepository.deleteAllTodoItems(todoListId);
    }

    @Override
    public void deleteTodoItem(int itemId) {
        mTodoItemRepository.deleteById(itemId);
    }

    @Override
    public TodoItemModel updateItemState(int itemId, int state) {
        TodoItemModel todoItemModel = mTodoItemRepository.findById(itemId);
        todoItemModel.setState(state);
        return mTodoItemRepository.save(todoItemModel);
    }
}
