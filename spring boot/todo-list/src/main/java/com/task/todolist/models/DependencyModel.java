package com.task.todolist.models;


import javax.persistence.*;
import java.util.Date;

@Entity
public class DependencyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    private int id;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "todo_item_id")
    private TodoItemModel todoItemId;

    @ManyToOne
    @JoinColumn(name = "dependent_id")
    private TodoItemModel dependentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TodoItemModel getTodoItemId() {
        return todoItemId;
    }

    public void setTodoItemId(TodoItemModel todoItemId) {
        this.todoItemId = todoItemId;
    }

    public TodoItemModel getDependentId() {
        return dependentId;
    }

    public void setDependentId(TodoItemModel dependentId) {
        this.dependentId = dependentId;
    }
}
