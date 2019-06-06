package com.task.todolist.services;

import com.task.todolist.models.DependencyModel;
import com.task.todolist.models.TodoItemModel;
import com.task.todolist.models.TodoListModel;

import java.util.List;

public interface DependencyServiceInterface {

    List<DependencyModel> createDependency(DependencyModel dependencyModel);

    void deleteAllDependencies(int itemId);

    void deleteAllDependenciesByListId(int listId);

    List<DependencyModel> findTodoItemDependencies(TodoItemModel todoItem);

    void deleteDependency(int itemId);

    int isTodoItemDependent(int itemId);


}
