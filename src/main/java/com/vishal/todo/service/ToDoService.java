package com.vishal.todo.service;

import com.vishal.todo.dao.ToDoTaskDao;
import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.util.TaskStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class ToDoService {

    private final ToDoTaskDao toDoTaskDao = new ToDoTaskDao();

    public ToDoTask saveOrUpdate(ToDoTask toDoTask) {
        log.info("Got request to create task: {}", toDoTask);

        //validate data
        if (null == toDoTask.getTask() || toDoTask.getTask().isBlank()) {
            log.error("Task cannot be null or empty");
        }
        if (null == toDoTask.getBuildingName() || toDoTask.getBuildingName().isBlank()) {
            log.error("building name cannot be null");
        }

        // set required fields in task
        if (null == toDoTask.getEmpName() || toDoTask.getEmpName().isBlank()) {
            toDoTask.setStatus(TaskStatus.NEW);
        } else {
            toDoTask.setStatus(TaskStatus.ASSIGNED);
        }
        if (0 == toDoTask.getId()) {
            toDoTask.setCreatedOn(new Date());
        } else {
            toDoTask.setUpdatedOn(new Date());
        }

        return toDoTaskDao.saveOrUpdate(toDoTask);
    }

    public ToDoTask getTaskById(int taskId) {
        log.info("Got request to fetch task for id: {}", taskId);
        return toDoTaskDao.getTaskById(taskId);
    }

    public List<ToDoTask> getAllTasks() {
        log.info("Got request to fetch all tasks");
        return toDoTaskDao.getAllTasks();
    }

    public void deleteTask(ToDoTask task) {
        log.info("Got request to delete task {}", task);
        toDoTaskDao.deleteTask(task);
    }
}
