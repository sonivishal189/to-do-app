package com.vishal.todo.service;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.util.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class ToDoTaskServiceTest {
//    private final String TEST_TASK_ID = "TEST_TASK_ID";
    private final String TEST_TASK_TO_DO = "Fix electric issues";
    private final String TEST_EMP_NAME = "TEST_EMP_NAME";
    private final String TEST_BUILDING_NAME = "TEST_BUILDING_NAME";
    private final String TEST_CREATED_BY = "TEST_CREATED_BY";

    @Test
    void testSaveTask() {
        ToDoService service = new ToDoService();
        ToDoTask task = new ToDoTask(TEST_TASK_TO_DO, TEST_EMP_NAME, TEST_BUILDING_NAME, TaskStatus.NEW, TEST_CREATED_BY);
        service.deleteTask(task);
        ToDoTask newTask = service.saveOrUpdate(task);
        log.info("New Task created is: {}", newTask);
//            Assertions.assertEquals(task.getId(), newTask.getId());
    }

    @Test
    void testGetTaskById() {
        ToDoService service = new ToDoService();
        ToDoTask task = service.getTaskById(1);
        log.info("Task fetched {}", task);
//        Assertions.assertEquals(task.getId(), 1);
    }

    @Test
    void testDeleteTaskById() {
        ToDoService service = new ToDoService();
        ToDoTask task = service.getTaskById(1);
        log.info("Task fetched {}", task);
        service.deleteTask(task);
    }

    @Test
    void testFetchAllTasks() {
        ToDoService service = new ToDoService();
        List<ToDoTask> allTasks = service.getAllTasks();
        log.info("Records fetched {}", allTasks);
    }
}
