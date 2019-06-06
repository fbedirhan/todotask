package com.task.todolist.repositories;

import com.task.todolist.models.TodoListModel;
import com.task.todolist.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoListModel, Integer> {

    @Query("SELECT td FROM TodoListModel td WHERE td.user.id = ?1")
    List<TodoListModel> findAllTodoListByUserId(int user_id);

}
