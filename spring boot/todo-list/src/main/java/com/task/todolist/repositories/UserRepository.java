package com.task.todolist.repositories;

import com.task.todolist.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query("SELECT u FROM UserModel u WHERE u.email = ?1 AND u.password = ?2 ")
    UserModel login(String email, String password);

    @Query("SELECT COUNT(u.id) FROM UserModel u WHERE u.email = ?1")
    int isUserExist(String email);

    UserModel findById(int id);
}
