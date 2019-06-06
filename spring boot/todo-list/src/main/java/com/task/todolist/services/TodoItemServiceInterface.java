package com.task.todolist.services;

import com.task.todolist.models.TodoItemModel;

import java.util.List;

public interface TodoItemServiceInterface {

    List<TodoItemModel> findAllTodoItemsByListId(int todoId, int state);

    List<TodoItemModel> findAllNotDependentTodoItems(int itemId,int listId);

    TodoItemModel createTodoItem(TodoItemModel todoItemModel);

    void deleteAllTodoItems(int todoId);

    void deleteTodoItem(int itemId);

    TodoItemModel updateItemState(int itemId, int state);

}
