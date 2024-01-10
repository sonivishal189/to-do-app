package com.vishal.todo.service;

import com.vishal.todo.dao.ToDoTaskDao;
import com.vishal.todo.model.ToDoTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToDoService {

    private final ToDoTaskDao toDoTaskDao = new ToDoTaskDao();

    public ToDoTask addNewToDoTask(ToDoTask toDoTask) {
        log.info("Got request to create task: {}", toDoTask);

        //validate data

        return toDoTaskDao.save(toDoTask);
    }

    public ToDoTask getTaskById(String taskId) {
        log.info("Got request to fetch task for id: {}", taskId);
        return toDoTaskDao.getTaskById(taskId);
    }

    public int deleteAllTasks() {
        log.info("Got request to delete all records");
        return toDoTaskDao.deleteAll();
    }

    public ToDoTask deleteTaskById(String taskId) {
        log.info("Got request to delete task with id {}", taskId);
        return toDoTaskDao.deleteById(taskId);
    }
}
