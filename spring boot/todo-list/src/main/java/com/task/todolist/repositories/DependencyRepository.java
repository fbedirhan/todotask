package com.task.todolist.repositories;

import com.task.todolist.models.DependencyModel;
import com.task.todolist.models.TodoItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DependencyRepository extends JpaRepository<DependencyModel, Integer> {


    List<DependencyModel> findByTodoItemId(TodoItemModel todoItemId);

    @Query("SELECT COUNT(dim.id) FROM  DependencyModel dim WHERE dim.todoItemId.id =?1 AND dim.dependentId.state=0")
    int isTodoItemDependent(int itemId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DependencyModel dim WHERE dim.todoItemId.id = ?1 OR dim.dependentId.id=?1")
    void deleteAllDependencies(int itemId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DependencyModel dim WHERE dim.todoItemId.todoList.id = ?1 OR dim.dependentId.todoList.id=?1")
    void deleteAllDependenciesByListId(int listId);
}
