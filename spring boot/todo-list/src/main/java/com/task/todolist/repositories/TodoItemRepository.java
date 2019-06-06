package com.task.todolist.repositories;

import com.task.todolist.models.TodoItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItemModel, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM TodoItemModel tim WHERE tim.todoList.id = ?1")
    void deleteAllTodoItems(int listId);

    @Query("SELECT tim FROM TodoItemModel tim WHERE tim.todoList.id = ?1")
    List<TodoItemModel> findAllTodoItemsByListId(int listId);

    @Query("SELECT tim FROM TodoItemModel tim WHERE tim.id  NOT IN (SELECT dim.dependentId.id FROM  com.task.todolist.models.DependencyModel dim WHERE dim.todoItemId.id =?1) AND tim.todoList.id=?2 AND tim.id <> ?1")
    List<TodoItemModel> findAllNotDependentTodoItems(int itemId, int listId);

    TodoItemModel findById(int id);

}
