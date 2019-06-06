package com.task.todolist.services;

import com.task.todolist.models.DependencyModel;
import com.task.todolist.models.TodoItemModel;
import com.task.todolist.repositories.DependencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependencyService implements DependencyServiceInterface{

    private final DependencyRepository mDependencyRepository;

    public DependencyService(DependencyRepository dependencyRepository) {
        this.mDependencyRepository = dependencyRepository;
    }


    @Override
    public List<DependencyModel> createDependency(DependencyModel dependencyModel) {
        mDependencyRepository.save(dependencyModel);
        return mDependencyRepository.findAll();
    }

    @Override
    public void deleteAllDependencies(int itemId) {
        mDependencyRepository.deleteAllDependencies(itemId);
    }

    @Override
    public void deleteAllDependenciesByListId(int listId) {
        mDependencyRepository.deleteAllDependenciesByListId(listId);
    }

    @Override
    public List<DependencyModel> findTodoItemDependencies(TodoItemModel todoItem) {
        return mDependencyRepository.findByTodoItemId(todoItem);
    }

    @Override
    public void deleteDependency(int itemId) {
        mDependencyRepository.deleteById(itemId);
    }

    @Override
    public int isTodoItemDependent(int itemId) {
        return mDependencyRepository.isTodoItemDependent(itemId);
    }
}
