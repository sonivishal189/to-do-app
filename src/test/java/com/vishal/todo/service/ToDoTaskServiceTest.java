package com.vishal.todo.service;

import com.vishal.todo.model.ToDoTask;
import com.vishal.todo.util.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class ToDoTaskServiceTest {
    private final String TEST_TASK_ID = "TEST_TASK_ID";
    private final String TEST_TASK_TO_DO = "Fix electric issues";
    private final String TEST_EMP_NAME = "TEST_EMP_NAME";
    private final String TEST_BUILDING_NAME = "TEST_BUILDING_NAME";

    @Test
    void testSaveTask() {
        ToDoTask task = new ToDoTask(TEST_TASK_ID, TEST_TASK_TO_DO, TEST_EMP_NAME, TEST_BUILDING_NAME, TaskStatus.NEW);
        ToDoService service = new ToDoService();
        service.deleteTaskById(TEST_TASK_ID);
        ToDoTask newTask = service.addNewToDoTask(task);
        log.info("New Task created is: {}", newTask);
        Assertions.assertEquals(task.getId(), newTask.getId());
    }

    @Test
    void testDeleteAllTasks() {
        ToDoService service = new ToDoService();
        int rowsDeleted = service.deleteAllTasks();
        log.info("No. of rows deleted {}", rowsDeleted);
    }
}
